package businessLogic.persistance;

import java.util.List;

public interface DataAccesObject {
	public List<DBPlaceObject> fetchAllPlaces();
	
	public List<DBPlaceObject> fetchSitesByKeywords(String keywords);
	
	public List<DBPlacesTransportObject> fetchSitesRelationsByKeywords(String keywords);
}
