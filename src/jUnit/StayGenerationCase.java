package jUnit;

import org.junit.Before;
import org.junit.Test;

<<<<<<< Updated upstream
import businessLogic.persistance.DBDataAccesObject;
import businessLogic.persistance.DataAccesObject;
=======
import businessLogic.dataAccess.DataAccesObject;
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
>>>>>>> Stashed changes
import spring.springContainer;

public class StayGenerationCase {

	DataAccesObject dataAccessor = springContainer.getBeanOfClass(DBDataAccesObject.class);

	@Before
	public void prepare()
	{
<<<<<<< Updated upstream
=======
		graphBuilder = new DBItineraryGraphBuilder(dataAccessor, touristicSiteFactory, hotelFactory);
		stayBuilder = new StayBuilder();
		
		itineraryGraph = graphBuilder.build("");
	}
	
	@Test
	public void checkNumberOfExcursionsForAdventurousProfile()
	{
		int stayDuration = 6;
		
		Stay stay = stayBuilder.build(itineraryGraph, stayDuration, 1000, 5000, StayProfile.Adventurous, 0.6 , "");
		
		int count = 0;
		for(StayActivity activity : stay.getActivities()) {
			if(activity.getType().equals(StayActivityType.Excursion)) {
				count++;
			}
		}
		
		int expectedCount = stayDuration * 2; // In this profile, it's 2 excursions by day
>>>>>>> Stashed changes
		
	}
	

	@Test
	public void base()
	{
<<<<<<< Updated upstream
=======
		int stayDuration = 6;
		
		Stay stay = stayBuilder.build(itineraryGraph, stayDuration, 1000, 5000, StayProfile.Discovery, 1, "");
		
		int count = 0;
		for(StayActivity activity : stay.getActivities()) {
			if(activity.getType().equals(StayActivityType.Excursion)) {
				count++;
			}
		}
		
		int expectedCount = stayDuration; // In this profile, it's 1 excursions by day
		
		Assert.isTrue(count == expectedCount);
	}
	
	@Test
	public void checkNumberOfExcursionsForRelaxProfile() {
		int stayDuration = 6;
		
		Stay stay = stayBuilder.build(itineraryGraph, stayDuration, 1000, 5000, StayProfile.Relax, 1, "");
		
		int count = 0;
		for(StayActivity activity : stay.getActivities()) {
			if(activity.getType().equals(StayActivityType.Excursion)) {
				count++;
			}
		}
		
		int expectedCount = (int) (stayDuration / 2); // In this profile, it's 1 excursions by 2 days
>>>>>>> Stashed changes
		
	}
	
	@Test
	public void checkBudgetCriteria() {
		int stayDuration = 6;
		
		double minBudget = 100;
		double maxBudget = 2000;
		
		Stay stay = stayBuilder.build(itineraryGraph, stayDuration, minBudget, maxBudget, StayProfile.Adventurous, 1, "");
	
		
		Assert.isTrue(stay.calculateCost() > 0);
		Assert.isTrue(stay.calculateCost() < maxBudget);
	}
	
	
	
	
}
