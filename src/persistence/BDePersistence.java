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

public class BDePersistence implements IBDePersistence {
	
	private Analyzer analyseur = new StandardAnalyzer();
	
	private String tableName, keyName, repositoryPath;

	@Override
	public void configure(String tableName, String keyName, String repositoryPath) {
		this.tableName = tableName;
		this.keyName = keyName;
		this.repositoryPath = repositoryPath;
	}

	@Override
	public void createTextIndex() {
		Path indexpath = FileSystems.getDefault().getPath(repositoryPath);
	    Directory index;
		try {
			index = FSDirectory.open(indexpath);
			IndexWriterConfig config = new IndexWriterConfig(analyseur);
			IndexWriter w = new IndexWriter(index, config);
			
			for (File f : new File(repositoryPath).listFiles()) {
			   	Document doc = new Document();
			   	doc.add(new Field("name", f.getName(), TextField.TYPE_STORED));
			   	doc.add(new Field("content", new FileReader(f), TextField.TYPE_NOT_STORED));
			   	w.addDocument(doc);
		    }
		   		
		   	w.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void addText(String key, String text) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BDeResultSet executeQuery(String query) {
		String combinedQueryRegex = "/^(.*?(\bwith\b)[^$]*)$/i";
		String tableNameRegex = "/^(.*?(\b" + tableName + "\b)[^$]*)$/i";
		String selectRegex = "/^(.*?(\bselect\b)[^$]*)$/i";
		if (query.matches(combinedQueryRegex)) {
			if (!query.matches(tableNameRegex) || !query.matches(selectRegex)) {
				// TODO here
				return null;
			}
			
			String[] queries = query.split(combinedQueryRegex);
			String sqlQuery = queries[0];
			String textQuery = queries[1];
			
			// TODO exception if queries length is not equals to 2 & if 0 and 1 is null
			
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
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	private LuceneTextResultSet executeTextQuery(String textQuery) {
		List<Map<String, Object>> results = new ArrayList<>();
		
		Path indexpath = FileSystems.getDefault().getPath(repositoryPath);
	    Directory index;
		try {
			index = FSDirectory.open(indexpath);
			DirectoryReader ireader = DirectoryReader.open(index);
		    IndexSearcher searcher = new IndexSearcher(ireader);
		    	
		    QueryParser qp = new QueryParser("content", analyseur); 
		    Query req = qp.parse(textQuery);

		    TopDocs luceneResults = searcher.search(req, 100);
		    
		    for (int i = 0; i < luceneResults.scoreDocs.length; i++) {
		    	int docId = luceneResults.scoreDocs[i].doc;
		    	Document d = searcher.doc(docId);
		    	
		    	Map<String, Object> result = new HashMap<>();
		    	result.put(keyName, d.get("name"));
		    	result.put("description", d.get("content"));
		    	result.put("score", luceneResults.scoreDocs[i].score);
		    	
		    	results.add(result);
		    }
		    
		    ireader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		LuceneTextResultSet textResultSet = new LuceneTextResultSet(results);
		return textResultSet;
	}

}
