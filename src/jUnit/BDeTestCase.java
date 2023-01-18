package jUnit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import persistence.BDePersistence;
import persistence.BDeResultSet;
import persistence.IBDePersistence;

public class BDeTestCase {
	
	private IBDePersistence bdePersistence;
	
	/**
	 * This method is executed before each test of this test case to configure the BDe API
	 */
	@Before
	public void prepare() {
		bdePersistence = new BDePersistence();
		String seychellesSitesPath = System.getProperty("user.dir") + System.getProperty("file.separator") + "seychelles_sites";
		bdePersistence.configure("site", "name", seychellesSitesPath);
		bdePersistence.createTextIndex();
	}
	
	/**
	 * This method is used to test the good execution of a SELECT SQL query
	 */
	@Test
	public void testExecuteSqlSelectQuery() {
		BDeResultSet bdeResultSet = bdePersistence.executeQuery("SELECT h.name as hotelName FROM hotel h WHERE h.name = 'Hotel1'");
		
		assertTrue(bdeResultSet.next());
		assertNotNull(bdeResultSet.getCurrentItem());
		assertTrue(((String) bdeResultSet.getCurrentItem().get("hotelName")).equals("Hotel1"));
		assertFalse(bdeResultSet.next());
		assertNull(bdeResultSet.getCurrentItem());
	}
	
	/**
	 * This method is used to test if all queries that are not SELECT are all being rejected by the system
	 */
	@Test
	public void testExecuteSqlNotSelectQuery() {
		assertNull(bdePersistence.executeQuery("INSERT INTO Place (name, comfort) VALUES ('TestPlace', 0.6)"));
		assertNull(bdePersistence.executeQuery("DELETE FROM Place WHERE name = 'Hotel1'"));
		assertNull(bdePersistence.executeQuery("UPDATE Place SET name = 'Hotel2' WHERE name = 'Hotel1'"));
	}
	
	/**
	 * This method is used to test the good execution of a combined query
	 */
	@Test
	public void testExecuteCombinedQuery() {
		BDeResultSet bdeResultSet = bdePersistence.executeQuery("SELECT s.name FROM Site s WHERE s.category = 'LEISURE' WITH plage");
		
		assertTrue(bdeResultSet.next());
		Map<String, Object> currentItem = bdeResultSet.getCurrentItem();
		assertNotNull(currentItem);
		assertTrue(currentItem.containsKey("name") && !((String) currentItem.get("name")).isEmpty());
		assertTrue(currentItem.containsKey("score") && (float) currentItem.get("score") != 0);
		assertTrue(currentItem.containsKey("description") && !((String) currentItem.get("description")).isEmpty());
		
		assertTrue(bdeResultSet.next());
		assertNotNull(bdeResultSet.getCurrentItem());
		
		assertTrue(bdeResultSet.next());
		assertNotNull(bdeResultSet.getCurrentItem());
		
		assertTrue(bdeResultSet.next());
		assertNotNull(bdeResultSet.getCurrentItem());
		
		assertTrue(bdeResultSet.next());
		assertNotNull(bdeResultSet.getCurrentItem());
		
		assertTrue(bdeResultSet.next());
		assertNotNull(bdeResultSet.getCurrentItem());
		
		assertFalse(bdeResultSet.next());
		assertNull(bdeResultSet.getCurrentItem());
	}

}
