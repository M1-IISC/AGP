package businessLogic.dataAccess;

import java.util.List;

public interface DataAccesObject {
	public List<PlaceObject> fetchAllHotels();
	
	public List<PlaceObject> fetchAllSites();
	
	public List<PlaceObject> fetchAllPlaces();
	
	public List<PlaceObject> fetchSitesByKeywords(String keywords);
	
	public List<PlacesTransportObject> fetchSitesRelationsByKeywords(String keywords);
}
