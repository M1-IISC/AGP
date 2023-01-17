package businessLogic.persistance;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import businessLogic.journeyPoint.CategoryOfSite;
import businessLogic.transports.TransportType;
import persistence.BDeResultSet;
import persistence.IBDePersistence;

public class DBDataAccesObject implements DataAccesObject {
	private IBDePersistence persistanceLayerInterface;
	
	@Override
	public List<PlaceObject> fetchAllHotels() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PlaceObject> fetchAllSites() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<PlaceObject> fetchAllPlaces() {
		LinkedList<PlaceObject> placeObjectList = new LinkedList<PlaceObject>();
		
		//TODO request text
		String querry = String.format("SELECT p.name, p.comfort, h.night_price, h.lunch_price, s.price, s.duration, s.category\r\n"
				+ "FROM place p Left outer JOIN hotel h ON p.name = h.name left outer JOIN site s ON p.name = s.name;\n");
		
		BDeResultSet resultSet = persistanceLayerInterface.executeQuery(querry);
		resultSet.init();
		while (resultSet.next())
		{
			Map<String, Object> line = resultSet.getCurrentItem();
			//here note we do not use the score to filter, but we should
			placeObjectList.add(new PlaceObject(
					(String)line.get("name"),
					null, //(String)line.get("descroption") //TODO
					((Double)line.get("confort")).doubleValue(),
					((Double)line.get("duration")).doubleValue(),
					((Double)line.get("price")).doubleValue(),
					((Double)line.get("night_price")).doubleValue(),
					((Double)line.get("lunch_price")).doubleValue(),
					(CategoryOfSite)line.get("category"),
					1 //TODO score
					));
		}
		
		
		return placeObjectList;
	}

	@Override
	public List<PlaceObject> fetchSitesByKeywords(String keywords) {
		LinkedList<PlaceObject> placeObjectList = new LinkedList<PlaceObject>();
		
		//TODO add keywords
		String querry = String.format("SELECT p.name, p.comfort, h.night_price, h.lunch_price, s.price, s.duration, s.category\r\n"
				+ "FROM place p Left outer JOIN hotel h ON p.name = h.name left outer JOIN site s ON p.name = s.name;\n");
		
		BDeResultSet resultSet = persistanceLayerInterface.executeQuery(querry);
		//TODO iterator->next when it work
		resultSet.init();
		while (resultSet.next())
		{
			Map<String, Object> line = resultSet.getCurrentItem();
			//here note we do not use the score to filter, but we should
			placeObjectList.add(new PlaceObject(
					(String)line.get("name"),
					null, //(String)line.get("descroption") //TODO
					((Double)line.get("confort")).doubleValue(),
					((Double)line.get("duration")).doubleValue(),
					((Double)line.get("price")).doubleValue(),
					((Double)line.get("night_price")).doubleValue(),
					((Double)line.get("lunch_price")).doubleValue(),
					(CategoryOfSite)line.get("category"),
					1 //TODO score
					));
		}
		
		return placeObjectList;
	}

	@Override
	public List<PlacesTransportObject> fetchSitesRelationsByKeywords(String keywords) {
		LinkedList<PlacesTransportObject> placesTransportObjectList = new LinkedList<PlacesTransportObject>();
		
		//TODO add keywords
		String querry = String.format("Select * \r\n"
				+ "FROM\r\n"
				+ "transport LEFT OUTER JOIN\r\n"
				+ "(SELECT p.name as startName, p.comfort as startConfort, h.night_price as startNightPrice, h.lunch_price as startLunchPrice, s.price as startCost, s.duration  as startDuration, s.category as startCat\r\n"
				+ "FROM place p Left outer JOIN hotel h ON p.name = h.name left outer JOIN site s ON p.name = s.name) AS T1 ON transport.start_place = startName\r\n"
				+ "left outer JOIN\r\n"
				+ "(SELECT p.name as endName, p.comfort as endConfort, h.night_price as endNightPrice, h.lunch_price as endLunchPrice, s.price as endCost, s.duration  as endDuration, s.category as endCat\r\n"
				+ "FROM place p Left outer JOIN hotel h ON p.name = h.name left outer JOIN site s ON p.name = s.name) AS T2 ON transport.end_place = endName");
		
		BDeResultSet resultSet = persistanceLayerInterface.executeQuery(querry);
		resultSet.init();
		while (resultSet.next())
		{
			Map<String, Object> line = resultSet.getCurrentItem();
			//here note we do not use the score to filter, but we should
			placesTransportObjectList.add(new PlacesTransportObject(
					//place source
					new PlaceObject(
							(String)line.get("startName"),
							null, //(String)line.get("descroption") //TODO
							((Double)line.get("startConfort")).doubleValue(),
							((Double)line.get("startDuration")).doubleValue(),
							((Double)line.get("startCost")).doubleValue(),
							((Double)line.get("startNightPrice")).doubleValue(),
							((Double)line.get("startLunchPrice")).doubleValue(),
							(CategoryOfSite)line.get("startCat"),
							1 //TODO score
							),
					//place destination
					new PlaceObject(
							(String)line.get("endName"),
							null, //(String)line.get("descroption") //TODO
							((Double)line.get("endConfort")).doubleValue(),
							((Double)line.get("endDuration")).doubleValue(),
							((Double)line.get("endCost")).doubleValue(),
							((Double)line.get("endNightPrice")).doubleValue(),
							((Double)line.get("endLunchPrice")).doubleValue(),
							(CategoryOfSite)line.get("endCat"),
							1 //TODO score
							),
						(TransportType)line.get("type"),
						((Double)line.get("distance")).doubleValue()
						)
					);
		}
		
		return placesTransportObjectList;
	}
}
