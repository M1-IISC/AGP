package persistence;


/**
 * 
 * Interface of the BDe API
 *
 */
public interface IBDePersistence {

	/**
	 * This method is used to configure the BDe text repository and his associate table name and key.
	 * @param tableName The name of the table
	 * @param keyName The name of the table key
	 * @param repositoryPath The repository path where each text will be stored
	 */
	public void configure(String tableName, String keyName, String repositoryPath);
	
	/**
	 * This method is used to create a text index on the configured repository.
	 */
	public void createTextIndex();
	
	/**
	 * This method is used to associate a text to an item (identified by a key) in the configured table.
	 * @param key The identifier of the item in the configured table
	 * @param text The text to associate
	 */
	public void addText(String key, String text);
	
	/**
	 * This method is used to execute a mixed query in the BDe.
	 * @param query The mixed query in SQL
	 * @return Iterator of the mixed query results
	 */
	public BDeResultSet executeQuery(String query);
	
}
