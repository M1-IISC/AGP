package jUnit;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.util.Assert;

import businessLogic.dataAccess.DataAccesObject;
import businessLogic.dataAccess.PlaceObject;
import businessLogic.itinerary.DBItineraryGraphBuilder;
import businessLogic.itinerary.ItineraryGraph;
import businessLogic.itinerary.ItineraryGraphBuilder;
import businessLogic.journeyPoint.JourneyPointFactory;
import businessLogic.stay.StayProfile;
import businessLogic.stay.builders.IStayBuilder;
import businessLogic.stay.builders.StayBuilder;
import spring.springContainer;

public class StayGenerationCase {

	DataAccesObject dataAccessor = springContainer.getBeanOfClass(DataAccesObject.class);
	
	JourneyPointFactory hotelFactory = springContainer.getBeanOfClass(JourneyPointFactory.class, "HotelFactory");
	JourneyPointFactory touristicSiteFactory = springContainer.getBeanOfClass(JourneyPointFactory.class, "TouristicSiteFactory");
	
	ItineraryGraphBuilder graphBuilder;
	ItineraryGraph itineraryGraph;
	
	IStayBuilder stayBuilder;

	@Before
	public void prepare()
	{
		graphBuilder = new DBItineraryGraphBuilder(dataAccessor, touristicSiteFactory, hotelFactory);
		stayBuilder = new StayBuilder();
		
		itineraryGraph = graphBuilder.build("");
	}
	
	@Test
	public void checkNumberOfExcursionsForAdventurousProfile()
	{
		stayBuilder.build(itineraryGraph, 7, 1000, 5000, StayProfile.Adventurous, 1, "");
	}
	
}
