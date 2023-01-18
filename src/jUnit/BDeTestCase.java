package jUnit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import persistence.BDePersistence;
import persistence.BDeResultSet;
import persistence.IBDePersistence;

public class BDeTestCase {
	
	private IBDePersistence bdePersistence;
	String seychellesSitesPath = System.getProperty("user.dir") + System.getProperty("file.separator") + "seychelles_sites";
	
	/**
	 * This method is executed before each test of this test case to configure the BDe API
	 */
	@Before
	public void prepare() {
		bdePersistence = new BDePersistence();
		bdePersistence.configure("site", "name", seychellesSitesPath);
		bdePersistence.createTextIndex();
	}
	
	/**
	 * This method is used to test the good creation of adding a text into the repository and the index
	 * @throws IOException 
	 */
	@Test
	public void testAddText() throws IOException {
		String key = "Site1";
		String description = "Ceci est un test ! Il adore les plages";
		
		bdePersistence.addText(key, description);
		
		String filePath = seychellesSitesPath + System.getProperty("file.separator") + key + ".txt";
		File f = new File(filePath);
		assertTrue(f.exists());
		assertTrue((new String(Files.readAllBytes(Paths.get(filePath)))).equals(description));
	}
	
	/**
	 * This method is used to test the good execution of a SELECT SQL query
	 */
	@Test
	public void testExecuteSqlSelectQuery() {
		String hotelName = "Hotel California Seychelles";
		
		BDeResultSet bdeResultSet = bdePersistence.executeQuery("SELECT h.name as hotelName FROM hotel h WHERE h.name = '" + hotelName + "'");
		
		assertTrue(bdeResultSet.next());
		assertNotNull(bdeResultSet.getCurrentItem());
		assertTrue(((String) bdeResultSet.getCurrentItem().get("hotelName")).equals(hotelName));
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
		BDeResultSet bdeResultSet = bdePersistence.executeQuery("SELECT s.name, s.category FROM Site s WHERE s.category = 'LEISURE' WITH plage");
		
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
	
	@Test
	public void testExecuteCombinedQueryWithNoSpecificKeywords() {
		BDeResultSet bdeResultSet = bdePersistence.executeQuery("SELECT s.name FROM Site s WHERE s.category = 'LEISURE' WITH *:*");
		
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
		
		assertTrue(bdeResultSet.next());
		assertNotNull(bdeResultSet.getCurrentItem());
	}

}
