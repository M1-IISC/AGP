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
import businessLogic.stay.Stay;
import businessLogic.stay.StayActivity;
import businessLogic.stay.StayActivityType;
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
	
	Stay stayAdventurous; 
	Stay stayDiscovery;
	Stay stayRelax;

	@Before
	public void prepare()
	{
		graphBuilder = new DBItineraryGraphBuilder(dataAccessor, touristicSiteFactory, hotelFactory);
		stayBuilder = new StayBuilder();
	}
	
	@Test
	public void checkNumberOfExcursionsAccordingToProfile()
	{	
		int stayDuration = 6;
		
		itineraryGraph = graphBuilder.build("");
		Stay stayAdventurous = stayBuilder.build(itineraryGraph, stayDuration, 8000, 10000, StayProfile.Adventurous, 0.6, "");
		
		itineraryGraph = graphBuilder.build("");
		Stay stayDiscovery = stayBuilder.build(itineraryGraph, stayDuration, 8000, 10000, StayProfile.Discovery, 0.6, "");
		
		itineraryGraph = graphBuilder.build("");
		Stay stayRelax = stayBuilder.build(itineraryGraph, stayDuration, 8000, 10000, StayProfile.Relax, 0.6, "");
		
		int countAdventurous = 0;
		for(StayActivity activity : stayAdventurous.getActivities()) {
			if(activity.getType().equals(StayActivityType.Excursion)) {
				countAdventurous++;
			}
		}
		
		int countDiscovery = 0;
		for(StayActivity activity : stayDiscovery.getActivities()) {
			if(activity.getType().equals(StayActivityType.Excursion)) {
				countDiscovery++;
			}
		}
		
		int countRelax = 0;
		for(StayActivity activity : stayRelax.getActivities()) {
			if(activity.getType().equals(StayActivityType.Excursion)) {
				countRelax++;
			}
		}
				
		Assert.isTrue(countRelax <= countDiscovery);
		Assert.isTrue(countDiscovery <= countAdventurous);
	}
	
	
	@Test
	public void checkComfortCriteria() {
		int stayDuration = 6;
		
		itineraryGraph = graphBuilder.build("");
		Stay stayWithHighComfort = stayBuilder.build(itineraryGraph, stayDuration, 8000, 10000, StayProfile.Discovery, 1.0, "");
		
		itineraryGraph = graphBuilder.build("");
		Stay stayWithLowComfort = stayBuilder.build(itineraryGraph, stayDuration, 8000, 10000, StayProfile.Discovery, 0.6, "");
		
		Assert.isTrue(stayWithLowComfort.calculateConfort() < stayWithHighComfort.calculateConfort());
	}
	
}
