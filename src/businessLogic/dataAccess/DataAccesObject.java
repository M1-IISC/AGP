package businessLogic.dataAccess;

import java.util.List;

public interface DataAccesObject {
	public List<PlaceObject> fetchAllHotels();
	
	public List<PlaceObject> fetchAllSites();
	
	public List<PlaceObject> fetchAllPlaces();
	
	//TODO rename sites to places
	public List<PlaceObject> fetchSitesByKeywords(String keywords);

	//TODO rename sites to places
	public List<PlacesTransportObject> fetchSitesRelationsByKeywords(String keywords);
}
