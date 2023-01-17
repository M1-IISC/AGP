package businessLogic.stay;

import businessLogic.StayProfile;
import businessLogic.itinaryGraph.ItineraryGraph;

public interface StayBuilder {
	
	/**
	 * This method is used to build a stay according to the criteria
	 * @param stayDuration duration of the stay
	 * @param minimumPrice minimum price of the stay.
	 * @param maximumPrice maximum price of the stay.
	 * @param profile profile of the stay
	 * @param quality quality/confort of the stay
	 * @param keywords words to describe what to look for in the description of activities
	 * @return The recommended stay
	 */
	public abstract Stay build(ItineraryGraph itinaryGraph, int stayDuration, double minimumPrice, double maximumPrice, StayProfile profile, double quality, String keywords);
}
