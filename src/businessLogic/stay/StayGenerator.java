package businessLogic.stay;

import java.util.Random;
import businessLogic.journeyPoint.Hotel;
import businessLogic.journeyPoint.PeriodOfDay;

public class StayGenerator implements StayBuilder {

	@Override
	public Stay build() {
		
		int duration = 7;
		int minBudget = 1000;
		int maxBudget = 5000;
		int confort = 3;
		int excursionRate = 1;
		String query;
		
		
		// Tools
		Random rand = new Random();
		
		// New instance of the stay
		Stay stay = new Stay();
		
		// Definition of the starting hotel
		Hotel hotel = selectBestBeginPoint(confort, minBudget, maxBudget);
		stay.setBeginPoint(hotel);
		
		// Plan the excursions according to the excursion rate
		StayActivityBuilder activityBuilder = new StayActivityGenerator();
		int day;
		boolean dayIsOff = false;
		for (day = 1; day <= duration; day++) {
			StayActivity morningActivity;
			StayActivity afternoonActivity;
			if (dayIsOff) {
				morningActivity = activityBuilder.build(hotel, null, PeriodOfDay.Morning, StayActivityType.ChillTime);
				afternoonActivity = activityBuilder.build(hotel, null, PeriodOfDay.Afternoon, StayActivityType.ChillTime);
				dayIsOff = false; // Next day is not off
			} else {
				if (excursionRate == 1) {
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
	
	
	private Hotel selectBestBeginPoint(int confort, int minBudget, int maxBudget) {
		
		return null;
	}
	
	
}
