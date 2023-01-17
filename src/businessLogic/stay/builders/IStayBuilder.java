package businessLogic.stay.builders;

import businessLogic.itinerary.ItineraryGraph;
import businessLogic.stay.Stay;
import businessLogic.stay.StayProfile;

public interface IStayBuilder {
	
	/**
	 * This method is used to build a stay according to the criteria
	 * @param stayDuration The duration of the stay
	 * @param minimumPrice The minimum price of the stay
	 * @param maximumPrice The maximum price of the stay
	 * @param profile The profile of the stay
	 * @param quality The quality or comfort of the stay
	 * @param keywords The words to describe what to look for in the description of activities
	 * @return The recommended stay
	 */
	public abstract Stay build(ItineraryGraph itinaryGraph, int stayDuration, double minimumPrice, double maximumPrice, StayProfile profile, double quality, String keywords);
}
