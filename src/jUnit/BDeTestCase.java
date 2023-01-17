package jUnit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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
		bdePersistence.configure("site", "name", "H:\\Desktop\\repository");
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
		assertNotNull(bdeResultSet.getCurrentItem());
		System.out.println(bdeResultSet.getCurrentItem());
	}

}
