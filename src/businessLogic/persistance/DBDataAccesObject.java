package businessLogic.persistance;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import businessLogic.transports.TransportType;
import persistence.BDeResultSet;
import persistence.IBDePersistence;

public class DBDataAccesObject implements DataAccesObject {
	private IBDePersistence persistanceLayerInterface;
	
	@Override
	public List<PlaceObject> fetchAllPlaces() {
		LinkedList<PlaceObject> placeObjectList = new LinkedList<PlaceObject>();
		
		//TODO request text
		String querry = String.format("");
		
		BDeResultSet resultSet = persistanceLayerInterface.executeQuery(querry);
		//TODO iterator->next when it work
		while (resultSet.getCurrentItem() != null)
		{
			Map<String, Object> line = resultSet.getCurrentItem();
			//here note we do not use the score to filter, but we should
			placeObjectList.add(new PlaceObject(
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
	public List<PlaceObject> fetchSitesByKeywords(String keywords) {
		LinkedList<PlaceObject> placeObjectList = new LinkedList<PlaceObject>();
		
		//TODO request text
		String querry = String.format("");
		
		BDeResultSet resultSet = persistanceLayerInterface.executeQuery(querry);
		//TODO iterator->next when it work
		while (resultSet.getCurrentItem() != null)
		{
			Map<String, Object> line = resultSet.getCurrentItem();
			//here note we do not use the score to filter, but we should
			placeObjectList.add(new PlaceObject(
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
	public List<PlacesTransportObject> fetchSitesRelationsByKeywords(String keywords) {
		LinkedList<PlacesTransportObject> placesTransportObjectList = new LinkedList<PlacesTransportObject>();
		
		//TODO request text
		String querry = String.format("");
		
		BDeResultSet resultSet = persistanceLayerInterface.executeQuery(querry);
		//TODO iterator->next when it work
		while (resultSet.getCurrentItem() != null)
		{
			Map<String, Object> line = resultSet.getCurrentItem();
			//here note we do not use the score to filter, but we should
			placesTransportObjectList.add(new PlacesTransportObject(
					//place source
					new PlaceObject(
							(String)line.get("name"),
							(String)line.get("descroption"),
							((Double)line.get("confort")).doubleValue(),
							((Double)line.get("duration")).doubleValue(),
							((Double)line.get("price")).doubleValue(),
							((Double)line.get("night_price")).doubleValue(),
							((Double)line.get("lunch_price")).doubleValue()
							),
					//place destination
					new PlaceObject(
							(String)line.get("name"),
							(String)line.get("descroption"),
							((Double)line.get("confort")).doubleValue(),
							((Double)line.get("duration")).doubleValue(),
							((Double)line.get("price")).doubleValue(),
							((Double)line.get("night_price")).doubleValue(),
							((Double)line.get("lunch_price")).doubleValue()
							),
						(TransportType)line.get("type"),
						((Double)line.get("distance")).doubleValue()
						)
					);
			resultSet.next();
		}
		
		return placesTransportObjectList;
	}
}
