package businessLogic.itinerary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import businessLogic.dataAccess.DataAccesObject;
import businessLogic.dataAccess.PlaceObject;
import businessLogic.dataAccess.PlacesTransportObject;
import businessLogic.journeyPoint.JourneyPoint;
import businessLogic.journeyPoint.JourneyPointFactory;

public class DBItineraryGraphBuilder implements ItineraryGraphBuilder {
	
	private DataAccesObject DataAccesor;
	private JourneyPointFactory hotelFactory;
	private JourneyPointFactory touristicSiteFactory;
	
	public DBItineraryGraphBuilder(DataAccesObject dataAccesor, JourneyPointFactory touristicSiteFactory, JourneyPointFactory hotelFactory) {
		super();
		DataAccesor = dataAccesor;
		this.hotelFactory = hotelFactory;
		this.touristicSiteFactory = touristicSiteFactory;
	}
	
	public DBItineraryGraphBuilder() {
		super();
	}

	public DataAccesObject getDataAccesor() {
		return DataAccesor;
	}

	public JourneyPointFactory getHotelFactory() {
		return hotelFactory;
	}

	public JourneyPointFactory getTouristicSiteFactory() {
		return touristicSiteFactory;
	}

	@Override
	public ItineraryGraph build(String keywords) {
		List<PlacesTransportObject> places = DataAccesor.fetchSitesRelationsByKeywords(keywords);
		if (places == null || places.size()==0) 
			return null;
		Map<String, Node> nodes = new HashMap<String, Node>();
		for (PlacesTransportObject placeRelation : places) {
			PlaceObject placeA = placeRelation.getSiteA();
			addUniqueNodeFromPlace(nodes, placeA);
			
			PlaceObject placeB = placeRelation.getSiteB();
			addUniqueNodeFromPlace(nodes, placeB);
			
			// TODO getTransportStrategyOfType without any duplicates
			Edge edge = new Edge(null, placeRelation.getDistance(), nodes.get(placeB.getName()));
			
			nodes.get(placeA.getName()).getEdges().add(edge);
		}
		return new ItineraryGraph(nodes.get(places.get(0).getSiteA().getName()));
	}
	
	private void addUniqueNodeFromPlace(Map<String, Node> nodes, PlaceObject place)
	{
		if(!nodes.containsKey(place.getName()))
		{
			JourneyPoint asHotel = hotelFactory.factory(place.getName(), place.getDescription(), place.getConfort(), place.getAttractionTime(), place.getCost(), place.getLunchCost(), place.getNightcost(), place.getCategory());
			JourneyPoint asSite = touristicSiteFactory.factory(place.getName(), place.getDescription(), place.getConfort(), place.getAttractionTime(), place.getCost(), place.getLunchCost(), place.getNightcost(), place.getCategory());
			
			nodes.put(place.getName(), asHotel == null ? new Node(asSite, new ArrayList<Edge>(), place.getAccuracy()) : new Node(asHotel, new ArrayList<Edge>(),place.getAccuracy()));
		}
	}
}
