package persistence;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * 
 * This singleton represents the SQL connection for Jdbc
 *
 */
class JdbcConnection {
	
	private static String host = "localhost";
	private static String base = "seychelles";
	private static String user = "root";
	private static String password = "";
	private static String url = "jdbc:mysql://" + host + "/" + base;

	private static Connection connection;

	public static Connection getConnection() {
		if (connection == null) {
			try {
				DriverManager.registerDriver(new com.mysql.jdbc.Driver());
				connection = DriverManager.getConnection(url, user, password);
			} catch (Exception e) {
				System.err.println("Connection to database failed : " + e.getMessage());
				System.exit(1);
			}
		}
		return connection;
	}
	
}
