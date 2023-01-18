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
import businessLogic.itinerary.ItineraryGraph;
import businessLogic.itinerary.ItineraryGraphBuilder;

public class BusinessLogicController implements IBusinessLogicController {
		
	private DataAccesObject dataAccessor;
	    
	private JourneyPointFactory hotelFactory;
	private JourneyPointFactory touristicSiteFactory;
	    
	private ItineraryGraphBuilder graphBuilder;
	
	public BusinessLogicController() {
		super();
	}
	
	

	public DataAccesObject getDataAccessor() {
		return dataAccessor;
	}



	public void setDataAccessor(DataAccesObject dataAccessor) {
		this.dataAccessor = dataAccessor;
	}



	public JourneyPointFactory getHotelFactory() {
		return hotelFactory;
	}



	public void setHotelFactory(JourneyPointFactory hotelFactory) {
		this.hotelFactory = hotelFactory;
	}



	public JourneyPointFactory getTouristicSiteFactory() {
		return touristicSiteFactory;
	}



	public void setTouristicSiteFactory(JourneyPointFactory touristicSiteFactory) {
		this.touristicSiteFactory = touristicSiteFactory;
	}



	public ItineraryGraphBuilder getGraphBuilder() {
		return graphBuilder;
	}



	public void setGraphBuilder(ItineraryGraphBuilder graphBuilder) {
		this.graphBuilder = graphBuilder;
	}



	@Override
	public List<Hotel> getAllHotels() {
		List<Hotel> hotels = new ArrayList<>();
		List<PlaceObject> places = dataAccessor.fetchAllHotels();
		
		for(PlaceObject place : places) {
			Hotel hotel = (Hotel) hotelFactory.factory(place.getName(), place.getDescription(), place.getConfort(), place.getAttractionTime(), place.getCost(), place.getLunchCost(), place.getNightCost(), place.getCategory());
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
				TouristicSite touristicSite = (TouristicSite) touristicSiteFactory.factory(place.getName(), place.getDescription(), place.getConfort(), place.getAttractionTime(), place.getCost(), place.getLunchCost(), place.getNightCost(), place.getCategory());
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
				TouristicSite touristicSite = (TouristicSite) touristicSiteFactory.factory(place.getName(), place.getDescription(), place.getConfort(), place.getAttractionTime(), place.getCost(), place.getLunchCost(), place.getNightCost(), place.getCategory());
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
			TouristicSite touristicSite = (TouristicSite) touristicSiteFactory.factory(place.getName(), place.getDescription(), place.getConfort(), place.getAttractionTime(), place.getCost(), place.getLunchCost(), place.getNightCost(), place.getCategory());
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
