package businessLogic.stay.builders;

import java.util.Random;

import businessLogic.itinerary.ItineraryGraph;
import businessLogic.journeyPoint.Hotel;
import businessLogic.journeyPoint.PeriodOfDay;
import businessLogic.stay.Stay;
import businessLogic.stay.StayActivity;
import businessLogic.stay.StayActivityType;
import businessLogic.stay.StayProfile;

public class StayBuilder implements IStayBuilder {

	@Override
	public Stay build(ItineraryGraph itinaryGraph, int stayDuration, double minimumPrice, double maximumPrice, StayProfile profile, double quality, String keywords) {	
		// Tools
		Random rand = new Random();
		
		// New instance of the stay
		Stay stay = new Stay();
		
		// Definition of the starting hotel
		Hotel hotel = selectBestBeginPoint(quality, minimumPrice, maximumPrice);
		stay.setBeginPoint(hotel);
		
		// Plan the excursions according to the excursion profile
		IStayActivityBuilder activityBuilder = new StayActivityBuilder(itinaryGraph);
		int day;
		boolean dayIsOff = false;
		for (day = 1; day <= stayDuration; day++) {
			StayActivity morningActivity;
			StayActivity afternoonActivity;
			if (dayIsOff) {
				morningActivity = activityBuilder.build(hotel, null, PeriodOfDay.Morning, StayActivityType.ChillTime);
				afternoonActivity = activityBuilder.build(hotel, null, PeriodOfDay.Afternoon, StayActivityType.ChillTime);
				dayIsOff = false; // Next day is not off
			} else {
				if (profile == StayProfile.Discovery || profile == StayProfile.Relax) {
					int randomPeriodOfExcursion = rand.nextInt(2); 
					if (randomPeriodOfExcursion == 0) {
						// The excursion will be in the morning
						morningActivity = activityBuilder.build(hotel, null, PeriodOfDay.Morning, StayActivityType.Excursion);
						// And chill time in the afternoon
						afternoonActivity = activityBuilder.build(hotel, null, PeriodOfDay.Afternoon, StayActivityType.ChillTime);
					} else {
						// The excursion will be in the afternoon
						afternoonActivity = activityBuilder.build(hotel, null, PeriodOfDay.Afternoon, StayActivityType.Excursion);
						// And chill time in the morning
						morningActivity = activityBuilder.build(hotel, null,PeriodOfDay.Morning, StayActivityType.ChillTime);
					}
					
					if (profile == StayProfile.Relax) {
						dayIsOff = true; // Next day will be off for Relax profile
					}
				} else {
					morningActivity = activityBuilder.build(hotel, null, PeriodOfDay.Morning, StayActivityType.Excursion);
					afternoonActivity = activityBuilder.build(hotel, null,PeriodOfDay.Afternoon, StayActivityType.Excursion);
				}
			}
			// Add the activity to the planning for the current day
			stay.addActivity(morningActivity);
			stay.addActivity(afternoonActivity);
		}
		
		return null;
	}
	
	
	private Hotel selectBestBeginPoint(double confort, double minBudget, double maxBudget) {
		// TODO implements this method;
		return null;
	}
	
	
}
