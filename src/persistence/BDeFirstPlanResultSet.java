package persistence;

import java.util.HashMap;
import java.util.Map;

class BDeFirstPlanResultSet implements BDeResultSet {
	
	private JdbcSqlResultSet sqlResultSet;
	private LuceneTextResultSet textResultSet;
	private String keyName;
	private Map<String, Object> currentResult;
	
	public BDeFirstPlanResultSet(JdbcSqlResultSet sqlResultSet, LuceneTextResultSet textResultSet, String keyName) {
		this.sqlResultSet = sqlResultSet;
		this.textResultSet = textResultSet;
		this.keyName = keyName;
		this.currentResult = null;
		init();
	}

	@Override
	public void init() {
		sqlResultSet.init();
		textResultSet.init();
	}

	@Override
	public boolean next() {
		if (!sqlResultSet.next()) {
			currentResult = null;
			return false;
		}
		
		Map<String, Object> sqlResult = sqlResultSet.getCurrentItem();
		String sqlKey = (String) sqlResult.get(keyName);
		
		Map<String, Object> matchText = null;
		while (textResultSet.next() && matchText == null) {
			Map<String, Object> textResult = textResultSet.getCurrentItem();
			String textKey = (String) textResult.get(keyName);
			
			if (sqlKey.equalsIgnoreCase(textKey)) {
				matchText = textResult;
			}
		}
		
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
		textResultSet.init();
		return true;
	}

	@Override
	public Map<String, Object> getCurrentItem() {
		return currentResult;
	}

}
