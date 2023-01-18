package businessLogic.dataAccess;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import businessLogic.journeyPoint.CategoryOfSite;
import businessLogic.transport.TransportType;
import persistence.BDeResultSet;
import persistence.IBDePersistence;

public class DBDataAccesObject implements DataAccesObject {
	private IBDePersistence persistanceLayerInterface;
	
	public IBDePersistence getPersistanceLayerInterface() {
		return persistanceLayerInterface;
	}

	public void setPersistanceLayerInterface(IBDePersistence persistanceLayerInterface) {
		this.persistanceLayerInterface = persistanceLayerInterface;
		String seychellesSitesPath = System.getenv().get("SEYCHELLES_SITES_PATH");
		persistanceLayerInterface.configure("site", "name", seychellesSitesPath);
		persistanceLayerInterface.createTextIndex();
	}

	@Override
	public List<PlaceObject> fetchAllHotels() {
		LinkedList<PlaceObject> placeObjectList = new LinkedList<PlaceObject>();
		
		String querry = String.format("SELECT p.name, p.comfort, h.night_price, h.lunch_price\r\n"
				+ "FROM place p INNER JOIN hotel h ON p.name = h.name\r\n");
		
		BDeResultSet resultSet = persistanceLayerInterface.executeQuery(querry);
		while (resultSet.next())
		{
			Map<String, Object> line = resultSet.getCurrentItem();
			//here note we do not use the score to filter, but we should
			placeObjectList.add(new PlaceObject(
					(String)line.get("name"),
					null ,
					getValue((Float)line.get("comfort")),
					0,
					0,
					getValue((Float)line.get("lunch_price")),
					getValue((Float)line.get("night_price")),
					getSiteValue(line.get("category")),
					1
					));
		}

		return placeObjectList;
	}

	@Override
	public List<PlaceObject> fetchAllSites() {
		// TODO Auto-generated method stub
		LinkedList<PlaceObject> placeObjectList = new LinkedList<PlaceObject>();
		
		String querry = String.format("SELECT p.name, p.comfort, s.price, s.duration, s.category "
				+ "FROM place p INNER JOIN site s ON p.name = s.name WITH *:*");
		
		BDeResultSet resultSet = persistanceLayerInterface.executeQuery(querry);
		while (resultSet.next())
		{
			Map<String, Object> line = resultSet.getCurrentItem();
			placeObjectList.add(new PlaceObject(
					(String)line.get("name"),
					(String)line.get("description"),
					getValue((Float)line.get("comfort")),
					getValue((Float)line.get("duration")),
					getValue((Float)line.get("price")),
					0,
					0,
					getSiteValue(line.get("category")),
					 getValue((Float)line.get("score"))
					));
		}
		return placeObjectList;
	}
	
	@Override
	public List<PlaceObject> fetchAllPlaces() {
		LinkedList<PlaceObject> placeObjectList = new LinkedList<PlaceObject>();
		
		String querry = String.format("SELECT p.name, p.comfort, h.night_price, h.lunch_price, s.price, s.duration, s.category "
				+ "FROM place p Left outer JOIN hotel h ON p.name = h.name left outer JOIN site s ON p.name = s.name WITH *:*");
		
		BDeResultSet resultSet = persistanceLayerInterface.executeQuery(querry);
		while (resultSet.next())
		{
			Map<String, Object> line = resultSet.getCurrentItem();

			placeObjectList.add(new PlaceObject(
					(String)line.get("name"),
					line.containsKey("description") ? (String)line.get("description") : null ,
					getValue((Float)line.get("comfort")),
					getValue((Float)line.get("duration")),
					getValue((Float)line.get("price")),
					getValue((Float)line.get("night_price")),
					getValue((Float)line.get("lunch_price")),
					getSiteValue(line.get("category")),
					line.containsKey("score") ? getValue((Float)line.get("score")) : 1
					));
		}
		
		
		return placeObjectList;
	}

	@Override
	public List<PlaceObject> fetchSitesByKeywords(String keywords) {
		LinkedList<PlaceObject> placeObjectList = new LinkedList<PlaceObject>();
		
		String querry = String.format("SELECT p.name, p.comfort, h.night_price, h.lunch_price, s.price, s.duration, s.category "
				+ "FROM place p Left outer JOIN hotel h ON p.name = h.name left outer JOIN site s ON p.name = s.name WITH %s", keywords.isEmpty() ? "*:*" : keywords);
		BDeResultSet resultSet = persistanceLayerInterface.executeQuery(querry);
		while (resultSet.next())
		{
			Map<String, Object> line = resultSet.getCurrentItem();
			placeObjectList.add(new PlaceObject(
					(String)line.get("name"),
					line.containsKey("description") ? (String)line.get("description") : null ,
					getValue((Float)line.get("comfort")),
					getValue((Float)line.get("duration")),
					getValue((Float)line.get("price")),
					getValue((Float)line.get("lunch_price")),
					getValue((Float)line.get("night_price")),
					getSiteValue(line.get("category")),
					line.containsKey("score") ? getValue((Float)line.get("score")) : 1
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
				+ "(SELECT p.name as startName, p.comfort as startComfort, h.night_price as startNightPrice, h.lunch_price as startLunchPrice, s.price as startCost, s.duration  as startDuration, s.category as startCat\r\n"
				+ "FROM place p Left outer JOIN hotel h ON p.name = h.name left outer JOIN site s ON p.name = s.name) AS T1 ON transport.start_place = startName\r\n"
				+ "left outer JOIN\r\n"
				+ "(SELECT p.name as endName, p.comfort as endComfort, h.night_price as endNightPrice, h.lunch_price as endLunchPrice, s.price as endCost, s.duration  as endDuration, s.category as endCat\r\n"
				+ "FROM place p Left outer JOIN hotel h ON p.name = h.name left outer JOIN site s ON p.name = s.name) AS T2 ON transport.end_place = endName");
		
		BDeResultSet resultSet = persistanceLayerInterface.executeQuery(querry);
		while (resultSet.next())
		{
			Map<String, Object> line = resultSet.getCurrentItem();
			placesTransportObjectList.add(new PlacesTransportObject(
					//place source
					new PlaceObject(
							(String)line.get("startName"),
							line.get("startCost") == null? null : "" ,
							getValue((Float)line.get("startComfort")),
							getValue((Float)line.get("startDuration")),
							getValue((Float)line.get("startCost")),
							getValue((Float)line.get("startLunchPrice")),
							getValue((Float)line.get("startNightPrice")),
							getSiteValue(line.get("startCat")),
							1
							),
					//place destination
					new PlaceObject(
							(String)line.get("endName"),
							line.get("endCost") == null? null : "" ,
							getValue((Float)line.get("endComfort")),
							getValue((Float)line.get("endDuration")),
							getValue((Float)line.get("endCost")),
							getValue((Float)line.get("endLunchPrice")),
							getValue((Float)line.get("endNightPrice")),
							getSiteValue(line.get("endCat")),
							1
							),
						getValue(line.get("type")),
						getValue((Float)line.get("distance"))
						)
					);
		}
		
		if (keywords==null||keywords.isEmpty()) return placesTransportObjectList;
		
		querry = String.format("Select name from site with %s", keywords);
		resultSet = persistanceLayerInterface.executeQuery(querry);
		while (resultSet.next())
		{
			Map<String, Object> line = resultSet.getCurrentItem();
			placesTransportObjectList.forEach(placeTransportRelation->{
				if (placeTransportRelation.getSiteA().getName().equals((String)line.get("name"))) {

					placeTransportRelation.getSiteA().setDescription((String)line.get("description"));
					placeTransportRelation.getSiteA().setAccuracy(getValue((Float)line.get("score")));
				}
				if (placeTransportRelation.getSiteB().getName().equals((String)line.get("name"))) {

					placeTransportRelation.getSiteB().setDescription((String)line.get("description"));
					placeTransportRelation.getSiteB().setAccuracy(getValue((Float)line.get("score")));
				}
			});
		}
		
		return placesTransportObjectList;
	}
	
	private double getValue(Float f)
	{
		return f != null ? f.doubleValue() : 0;
	}
	
	private CategoryOfSite  getSiteValue(Object o)
	{
		return o !=null ? CategoryOfSite.valueOf((String)o) : CategoryOfSite.HISTORIC;
	}
	
	private TransportType  getValue(Object o)
	{
		return TransportType.valueOf((String)o);
	}
}
