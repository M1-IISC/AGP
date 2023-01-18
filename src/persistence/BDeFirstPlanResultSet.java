package persistence;

import java.util.HashMap;
import java.util.Map;

class BDeFirstPlanResultSet implements BDeResultSet {
	
	// Queries result sets
	private JdbcSqlResultSet sqlResultSet;
	private LuceneTextResultSet textResultSet;
	
	// Key name to match the different queries with
	private String keyName;
	
	// Current result (where the cursor is)
	private Map<String, Object> currentResult;
	
	// Init result set
	public BDeFirstPlanResultSet(JdbcSqlResultSet sqlResultSet, LuceneTextResultSet textResultSet, String keyName) {
		this.sqlResultSet = sqlResultSet;
		this.textResultSet = textResultSet;
		this.keyName = keyName;
		init();
	}

	@Override
	public void init() {
		// Init queries result set
		sqlResultSet.init();
		textResultSet.init();
		currentResult = null;
	}

	@Override
	public boolean next() {
		// If SQL result set is entirely visited, we return false because there is no other entry
		if (!sqlResultSet.next()) {
			currentResult = null;
			return false;
		}
		
		// Getting current item of the SQL result set and getting value of the text index key of the table associated
		Map<String, Object> sqlResult = sqlResultSet.getCurrentItem();
		String sqlKey = (String) sqlResult.get(keyName);
		
		// Getting a matching text document
		Map<String, Object> matchText = null;
		while (textResultSet.next() && matchText == null) {
			Map<String, Object> textResult = textResultSet.getCurrentItem();
			String textKey = (String) textResult.get(keyName);
			
			if (sqlKey.equalsIgnoreCase(textKey)) {
				matchText = textResult;
			}
		}
		
		// Combining items into the current result
		Map<String, Object> result = new HashMap<>();
		for (String key : sqlResult.keySet()) {
			Object o = sqlResult.get(key);
			result.put(key, o);
		}
		if (matchText != null) {
			for (String key : matchText.keySet()) {
				Object o = matchText.get(key);
				result.put(key, o);
			}
		}
		currentResult = result;
		
		// Reinitialize text result set for possible next iteration
		textResultSet.init();
		return true;
	}

	@Override
	public Map<String, Object> getCurrentItem() {
		return currentResult;
	}

}
