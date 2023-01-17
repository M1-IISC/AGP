package businessLogic.stay.builders;

import businessLogic.journeyPoint.Hotel;
import businessLogic.journeyPoint.PeriodOfDay;
import businessLogic.stay.StayActivity;
import businessLogic.stay.StayActivityType;

public interface IStayActivityBuilder {
	
	
	/**
	 * This method is used to build an activity (half day) for a stay.
	 * @param startingPoint The starting hotel
	 * @param arrivalPoint The arrival hotel
	 * @param periodOfDay The period of day
	 * @param type The type of activity
	 * @return The activity
	 */
	public abstract StayActivity build(Hotel startingPoint, Hotel arrivalPoint, PeriodOfDay periodOfDay, StayActivityType type);
	
}
