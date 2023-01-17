package businessLogic.persistance;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import persistence.BDeResultSet;
import persistence.IBDePersistence;

public class DBDataAccesObject implements DataAccesObject {
	private IBDePersistence persistanceLayerInterface;
	
	@Override
	public List<DBPlaceObject> fetchAllPlaces() {
		LinkedList<DBPlaceObject> placeObjectList = new LinkedList<DBPlaceObject>();
		
		//TODO request text
		String querry = String.format("");
		
		BDeResultSet resultSet = persistanceLayerInterface.executeQuery(querry);
		//TODO iterator->next when it work
		while (resultSet.getCurrentItem() != null)
		{
			Map<String, Object> line = resultSet.getCurrentItem();
			//here note we do not use the score to filter, but we should
			placeObjectList.add(new DBPlaceObject(
					(String)line.get("name"),
					(String)line.get("descroption"),
					((Double)line.get("confort")).doubleValue(),
					((Double)line.get("duration")).doubleValue(),
					((Double)line.get("price")).doubleValue(),
					((Double)line.get("night_price")).doubleValue(),
					((Double)line.get("lunch_price")).doubleValue()
					));
			resultSet.next();
		}
		
		
		return placeObjectList;
	}

	@Override
	public List<DBPlaceObject> fetchSitesByKeywords(String keywords) {
		LinkedList<DBPlaceObject> placeObjectList = new LinkedList<DBPlaceObject>();
		
		//TODO request text
		String querry = String.format("");
		
		BDeResultSet resultSet = persistanceLayerInterface.executeQuery(querry);
		//TODO iterator->next when it work
		while (resultSet.getCurrentItem() != null)
		{
			Map<String, Object> line = resultSet.getCurrentItem();
			//here note we do not use the score to filter, but we should
			placeObjectList.add(new DBPlaceObject(
					(String)line.get("name"),
					(String)line.get("descroption"),
					((Double)line.get("confort")).doubleValue(),
					((Double)line.get("duration")).doubleValue(),
					((Double)line.get("price")).doubleValue(),
					((Double)line.get("night_price")).doubleValue(),
					((Double)line.get("lunch_price")).doubleValue()
					));
			resultSet.next();
		}
		
		return placeObjectList;
	}

	@Override
	public List<DBPlacesTransportObject> fetchSitesRelationsByKeywords(String keywords) {
		// TODO Auto-generated method stub
		return null;
	}
}
