package businessLogic.stay;

import businessLogic.journeyPoint.Hotel;

public class StayBuilderConcrete implements StayBuilder {

	@Override
	public Stay build() {
		
		int duration = 7;
		int minBudget = 1000;
		int maxBudget = 5000;
		int confort = 3;
		int excursionRate = 1;
		String query;
		
		// New instance of the stay
		Stay stay = new Stay();
		
		// Definition of the starting hotel
		Hotel hotel = selectBestBeginPoint(confort, minBudget, maxBudget);
		stay.setBeginPoint(hotel);
		
		// Calculate number of excursions according to the excursion rate
		int count = numberOfExcursions(duration, excursionRate);
		
		
		
		return null;
	}
	
	
	private Hotel selectBestBeginPoint(int confort, int minBudget, int maxBudget) {
		
		return null;
	}
	
	private int numberOfExcursions(int duration, int excursionRate) {
		return duration;	
	}
	
	
}
