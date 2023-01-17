package persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

class JdbcSqlResultSet implements BDeResultSet {
	
	private ResultSet jdbcResultSet;
	
	public JdbcSqlResultSet(ResultSet jdbcResultSet) {
		this.jdbcResultSet = jdbcResultSet;
		init();
	}

	@Override
	public void init() {
		try {
			jdbcResultSet.beforeFirst();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean next() {
		try {
			return jdbcResultSet.next();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Map<String, Object> getCurrentItem() {
		Map<String, Object> currentItem = new HashMap<>();
		
		// Putting all columns data of the row into a Map
		try {
			int columnCount = jdbcResultSet.getMetaData().getColumnCount();
			for (int i = 0; i < columnCount; i++) {
				String columnName = jdbcResultSet.getMetaData().getColumnName(i);
				Object columnData = jdbcResultSet.getObject(i);
				currentItem.put(columnName, columnData);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return currentItem;
	}

}
