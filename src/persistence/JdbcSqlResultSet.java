package persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

class JdbcSqlResultSet implements BDeResultSet {
	
	// Jdbc SQL result set 
	private ResultSet jdbcResultSet;
	
	// Result set current item
	private Map<String, Object> currentItem;
	
	// Init result set
	public JdbcSqlResultSet(ResultSet jdbcResultSet) {
		this.jdbcResultSet = jdbcResultSet;
		currentItem = null;
		init();
	}

	@Override
	public void init() {
		// Init result set by setting jdbc result set cursor just before the first row
		try {
			jdbcResultSet.beforeFirst();
		} catch (SQLException e) {
			// TODO
			System.err.println(e.getMessage());
		}
	}

	@Override
	public boolean next() {
		// Moving the cursor of the result set by moving the jdbc result set cursor
		try {
			jdbcResultSet.next();
			
			// Putting all columns data of the row into the result set current item
			currentItem = new HashMap<>();
			int columnCount = jdbcResultSet.getMetaData().getColumnCount();
			for (int i = 1; i <= columnCount; i++) {
				String columnName = jdbcResultSet.getMetaData().getColumnLabel(i);
				Object columnData = jdbcResultSet.getObject(i);
				currentItem.put(columnName, columnData);
			}
			
			return true;
		} catch (SQLException e) {
			// TODO
			return false;
		}
	}

	@Override
	public Map<String, Object> getCurrentItem() {
		Map<String, Object> currentItem = new HashMap<>();
		
		try {
			int columnCount = jdbcResultSet.getMetaData().getColumnCount();
			for (int i = 1; i <= columnCount; i++) {
				String columnName = jdbcResultSet.getMetaData().getColumnLabel(i);
				Object columnData = jdbcResultSet.getObject(i);
				currentItem.put(columnName, columnData);
			}
		} catch (SQLException e) {
			return null;
		}
		
		return currentItem;
	}

}
