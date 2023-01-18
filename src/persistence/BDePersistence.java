package persistence;

import java.io.*;
import java.nio.file.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.primefaces.shaded.commons.io.FilenameUtils;

public class BDePersistence implements IBDePersistence {
	
	private Analyzer analyseur = new StandardAnalyzer();
	
	private String tableName, keyName, repositoryPath, indexPath;

	@Override
	public void configure(String tableName, String keyName, String repositoryPath) {
		this.tableName = tableName;
		this.keyName = keyName;
		this.repositoryPath = repositoryPath;
		this.indexPath = Paths.get(repositoryPath).getParent().toString() + System.getProperty("file.separator") + "index";
	}

	@Override
	public void createTextIndex() {
	    Directory index;
		try {
			// Get text index directory with the index path
			index = FSDirectory.open(FileSystems.getDefault().getPath(indexPath));
			
			// If index already exists, abort creating text index (avoiding index duplicates)
			if (DirectoryReader.indexExists(index)) {
				return;
			}
			
			// Creating the index writer
			IndexWriterConfig config = new IndexWriterConfig(analyseur);
			IndexWriter w = new IndexWriter(index, config);
			
			// For each files into the repository path, adding these files to the text index
			for (File f : new File(repositoryPath).listFiles()) {
			   	Document doc = new Document();
			   	doc.add(new TextField("name", FilenameUtils.removeExtension(f.getName()), Field.Store.YES));
			   	doc.add(new TextField("content", new FileReader(f)));
			   	w.addDocument(doc);
		    }
		   	
			// Closing the index writer
		   	w.close();
		} catch (IOException e) {
			// TODO
			System.err.println(e.getMessage());
		}
	}

	@Override
	public void addText(String key, String text) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BDeResultSet executeQuery(String query) {
		// Defining regexs
		String combinedQueryRegex = "^(.*?(\\bwith\\b)[^$]*)$";
		String tableNameRegex = "^(.*?(\\b" + tableName + "\\b)[^$]*)$";
		String selectRegex = "^(.*?(\\bselect\\b)[^$]*)$";
		String withRegex = "with";
		
		// If query is not a SELECT query, aborting
		if (!Pattern.compile(selectRegex, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE).matcher(query).matches()) {
			System.err.println("This query is not a SELECT query");
			return null;
		}
		
		// Verifying which type this query is, combined or SQL
		if (Pattern.compile(combinedQueryRegex, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE).matcher(query).matches()) {
			// Combined query
			
			// Verifying that combined query contains the table associated to the text index
			if (!Pattern.compile(tableNameRegex, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE).matcher(query).matches()) {
				System.err.println("The table " + tableName + " has no text index");
				return null;
			}
			
			// Separating combined query into on SQL query and one Text query
			String[] queries = Pattern.compile(withRegex, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE).split(query);
			String sqlQuery = queries[0];
			String textQuery = queries[1];
			
			// Verifying that splitting has been correctly done
			// TODO exception if queries length is not equals to 2 & if 0 and 1 is null
			if (queries.length != 2 || sqlQuery.length() == 0 || textQuery.length() == 0) {
				System.err.println("Error when interpretting combined query");
				return null;
			}
			
			// Executing the two queries
			JdbcSqlResultSet sqlResultSet = executeSqlQuery(sqlQuery);
			LuceneTextResultSet textResultSet = executeTextQuery(textQuery);
			
			// Combine queries
			return combineQuery(sqlResultSet, textResultSet);
		} else {
			// SQL query
			
			// Executing SQL query
			return executeSqlQuery(query);
		}
	}
	
	/**
	 * This method is used to combine the two SQL & Text queries into one.
	 * @param sqlResultSet The SQL result set iterator
	 * @param textResultSet The Text result set iterator
	 * @return The combined result set iterator containing the two queries results
	 */
	private BDeFirstPlanResultSet combineQuery(JdbcSqlResultSet sqlResultSet, LuceneTextResultSet textResultSet) {
		BDeFirstPlanResultSet firstPlanResultSet = new BDeFirstPlanResultSet(sqlResultSet, textResultSet, keyName);
		return firstPlanResultSet;
	}
	
	/**
	 * This method is used to execute the SQL query.
	 * @param sqlQuery The SQL query
	 * @return The SQL result set iterator containing the SQL query results
	 */
	private JdbcSqlResultSet executeSqlQuery(String sqlQuery) {
		// Executing query with JDBC and putting the JDBC result set into the final result set
		try {
			Statement statement = JdbcConnection.getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery(sqlQuery);
			
			// Creating the result set
			JdbcSqlResultSet sqlResultSet = new JdbcSqlResultSet(resultSet);
			return sqlResultSet;
		} catch (SQLException e) {
			// TODO
			System.err.println(e.getMessage());
			return null;
		}
	}
	
	/**
	 * This method is used to execute the Text query.
	 * @param sqlQuery The Text query
	 * @return The Text result set iterator containing the Text query results
	 */
	private LuceneTextResultSet executeTextQuery(String textQuery) {
		// Creating list of results
		List<Map<String, Object>> results = new ArrayList<>();
		
	    Directory index;
		try {
			// Get text index directory with the index path
			index = FSDirectory.open(FileSystems.getDefault().getPath(indexPath));
			
			// Creating the index reader
			DirectoryReader ireader = DirectoryReader.open(index);
		    IndexSearcher searcher = new IndexSearcher(ireader);
		    
		    // Parsing text query into Lucene text query
		    QueryParser qp = new QueryParser("content", analyseur); 
		    Query req = qp.parse(textQuery);
		    
		    // Gathering results (max 100)
		    TopDocs luceneResults = searcher.search(req, 100);
		    
		    // We add reach result into the results list 
		    for (int i = 0; i < luceneResults.scoreDocs.length; i++) {
		    	int docId = luceneResults.scoreDocs[i].doc;
		    	float docScore = luceneResults.scoreDocs[i].score;
		    	Document d = searcher.doc(docId);
		    	
		    	String description = new String(Files.readAllBytes(Paths.get(repositoryPath + "/" + d.get("name") + ".txt")));
		    	
		    	Map<String, Object> result = new HashMap<>();
		    	result.put(keyName, d.get("name"));
		    	result.put("description", description);
		    	result.put("score", docScore);
		    	
		    	results.add(result);
		    }
		    
		    // Closing the index reader
		    ireader.close();
		    
		    // Creating the result set
		    LuceneTextResultSet textResultSet = new LuceneTextResultSet(results);
			return textResultSet;
		} catch (IOException e) {
			// TODO
			System.err.println(e.getMessage());
			return null;
		} catch (ParseException e) {
			// TODO
			System.err.println(e.getMessage());
			return null;
		}
	}

}
