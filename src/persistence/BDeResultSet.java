package persistence;

import java.util.Map;

/**
 * 
 * Interface of the BDe ResultSet
 *
 */
public interface BDeResultSet {
	
	/**
	 * This method is used to reset the cursor of the result set.
	 */
	public void init();
	
	/**
	 * This method is used to move the cursor to the next result of the result set.
	 */
	public void next();
	
	/**
	 * This method is used to get the current item of the result set where the cursor is located.
	 * @return The map of the current item of the result set
	 */
	public Map<String, Object> getCurrentItem();

}
