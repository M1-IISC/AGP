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
			index = FSDirectory.open(FileSystems.getDefault().getPath(indexPath));
			
			if (DirectoryReader.indexExists(index)) {
				return;
			}
			
			IndexWriterConfig config = new IndexWriterConfig(analyseur);
			IndexWriter w = new IndexWriter(index, config);
			
			for (File f : new File(repositoryPath).listFiles()) {
			   	Document doc = new Document();
			   	doc.add(new TextField("name", FilenameUtils.removeExtension(f.getName()), Field.Store.YES));
			   	doc.add(new TextField("content", new FileReader(f)));
			   	w.addDocument(doc);
		    }
		   		
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
		String combinedQueryRegex = "^(.*?(\\bwith\\b)[^$]*)$";
		String tableNameRegex = "^(.*?(\\b" + tableName + "\\b)[^$]*)$";
		String selectRegex = "^(.*?(\\bselect\\b)[^$]*)$";
		
		if (!Pattern.compile(selectRegex, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE).matcher(query).matches()) {
			return null;
		}
		
		if (Pattern.compile(combinedQueryRegex, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE).matcher(query).matches()) {
			if (!Pattern.compile(tableNameRegex, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE).matcher(query).matches()) {
				System.err.println("The table " + tableName + " has no text index");
				return null;
			}
			
			String[] queries = Pattern.compile("[,&]|with", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE).split(query);
			String sqlQuery = queries[0];
			String textQuery = queries[1];
			
			// TODO exception if queries length is not equals to 2 & if 0 and 1 is null
			if (queries.length != 2 || sqlQuery.length() == 0 || textQuery.length() == 0) {
				System.err.println("Error when interpretting combined query");
				return null;
			}
			
			JdbcSqlResultSet sqlResultSet = executeSqlQuery(sqlQuery);
			LuceneTextResultSet textResultSet = executeTextQuery(textQuery);
			
			return combineQuery(sqlResultSet, textResultSet);
		} else {
			return executeSqlQuery(query);
		}
	}
	
	private BDeFirstPlanResultSet combineQuery(JdbcSqlResultSet sqlResultSet, LuceneTextResultSet textResultSet) {
		BDeFirstPlanResultSet firstPlanResultSet = new BDeFirstPlanResultSet(sqlResultSet, textResultSet, keyName);
		return firstPlanResultSet;
	}
	
	private JdbcSqlResultSet executeSqlQuery(String sqlQuery) {
		try {
			Statement statement = JdbcConnection.getConnection().createStatement();
			ResultSet resultSet = statement.executeQuery(sqlQuery);
			
			JdbcSqlResultSet sqlResultSet = new JdbcSqlResultSet(resultSet);
			return sqlResultSet;
		} catch (SQLException e) {
			// TODO
			System.err.println(e.getMessage());
			return null;
		}
	}
	
	private LuceneTextResultSet executeTextQuery(String textQuery) {
		List<Map<String, Object>> results = new ArrayList<>();
		
	    Directory index;
		try {
			index = FSDirectory.open(FileSystems.getDefault().getPath(indexPath));
			DirectoryReader ireader = DirectoryReader.open(index);
		    IndexSearcher searcher = new IndexSearcher(ireader);
		    	
		    QueryParser qp = new QueryParser("content", analyseur); 
		    Query req = qp.parse(textQuery);

		    TopDocs luceneResults = searcher.search(req, 100);
		    
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
		    
		    ireader.close();
		    
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
