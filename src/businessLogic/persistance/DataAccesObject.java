package businessLogic.persistance;

import java.util.List;

public interface DataAccesObject {
	public List<PlaceObject> fetchAllPlaces();
	
	public List<PlaceObject> fetchSitesByKeywords(String keywords);
	
	public List<PlacesTransportObject> fetchSitesRelationsByKeywords(String keywords);
}
