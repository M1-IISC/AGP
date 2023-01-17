package businessLogic;

import java.util.ArrayList;
import java.util.List;

import businessLogic.journeyPoint.CategoryOfSite;
import businessLogic.journeyPoint.Hotel;
import businessLogic.journeyPoint.JourneyPointFactory;
import businessLogic.journeyPoint.TouristicSite;
import businessLogic.stay.Stay;
import businessLogic.stay.StayProfile;
import businessLogic.stay.builders.IStayBuilder;
import businessLogic.stay.builders.StayBuilder;
import businessLogic.dataAccess.DataAccesObject;
import businessLogic.dataAccess.PlaceObject;
import businessLogic.itinerary.DBItineraryGraphBuilder;
import businessLogic.itinerary.ItineraryGraph;
import businessLogic.itinerary.ItineraryGraphBuilder;
import spring.springContainer;

public class BusinessLogicController implements IBusinessLogicController {
		
	private DataAccesObject dataAccessor = springContainer.getBeanOfClass(DataAccesObject.class);
	    
	private JourneyPointFactory hotelFactory = springContainer.getBeanOfClass(JourneyPointFactory.class, "HotelFactory");
	private JourneyPointFactory touristicSiteFactory = springContainer.getBeanOfClass(JourneyPointFactory.class, "TouristicSiteFactory");
	    
	private ItineraryGraphBuilder graphBuilder = new DBItineraryGraphBuilder(dataAccessor, touristicSiteFactory, hotelFactory);
	

	@Override
	public List<Hotel> getAllHotels() {
		List<Hotel> hotels = new ArrayList<>();
		List<PlaceObject> places = dataAccessor.fetchAllHotels();
		
		for(PlaceObject place : places) {
			Hotel hotel = (Hotel) hotelFactory.factory(place.getName(), place.getDescription(), place.getConfort(), place.getAttractionTime(), place.getCost(), place.getLunchCost(), place.getNightcost(), place.getCategory());
			if (hotel != null) {
				hotels.add(hotel);
			}
		}
		
		return hotels;
	}

	@Override
	public List<TouristicSite> getAllHistoricalSites() {
		List<TouristicSite> touristicSites = new ArrayList<>();
		List<PlaceObject> places = dataAccessor.fetchAllSites();
		
		for(PlaceObject place : places) {
			if (place.getCategory() == CategoryOfSite.HISTORIC) {
				TouristicSite touristicSite = (TouristicSite) touristicSiteFactory.factory(place.getName(), place.getDescription(), place.getConfort(), place.getAttractionTime(), place.getCost(), place.getLunchCost(), place.getNightcost(), place.getCategory());
				if (touristicSite != null) {
					touristicSites.add(touristicSite);
				}
			}
		}
		
		return touristicSites;
	}

	@Override
	public List<TouristicSite> getAllActivitySites() {
		List<TouristicSite> touristicSites = new ArrayList<>();
		List<PlaceObject> places = dataAccessor.fetchAllSites();
		
		for(PlaceObject place : places) {
			if (place.getCategory() == CategoryOfSite.LEISURE) {
				TouristicSite touristicSite = (TouristicSite) touristicSiteFactory.factory(place.getName(), place.getDescription(), place.getConfort(), place.getAttractionTime(), place.getCost(), place.getLunchCost(), place.getNightcost(), place.getCategory());
				if (touristicSite != null) {
					touristicSites.add(touristicSite);
				}
			}
		}
		
		return touristicSites;
	}

	@Override
	public List<TouristicSite> searchForTouristicSites(String keywords) {
		List<TouristicSite> touristicSites = new ArrayList<>();
		List<PlaceObject> places = dataAccessor.fetchSitesByKeywords(keywords);
		
		for(PlaceObject place : places) {
			TouristicSite touristicSite = (TouristicSite) touristicSiteFactory.factory(place.getName(), place.getDescription(), place.getConfort(), place.getAttractionTime(), place.getCost(), place.getLunchCost(), place.getNightcost(), place.getCategory());
			if (touristicSite != null) {
				touristicSites.add(touristicSite);
			}
		}
		
		return touristicSites;
	}

	@Override
	public List<Stay> searchPlansForAStay(int stayDuration, double minimumPrice, double maximumPrice,
			StayProfile profile, double quality, String keywords) {
		List<Stay> stays = new ArrayList<Stay>();
		
		ItineraryGraph itineraryGraph = graphBuilder.build(keywords);
		
		IStayBuilder builder = new StayBuilder();
		Stay stay = builder.build(itineraryGraph, stayDuration, minimumPrice, maximumPrice, profile, quality, keywords);
		stays.add(stay);
		
		return stays;
	}

}
