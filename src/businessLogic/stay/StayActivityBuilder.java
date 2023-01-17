package businessLogic.stay;

import businessLogic.journeyPoint.Hotel;
import businessLogic.journeyPoint.PeriodOfDay;

public interface StayActivityBuilder {
	public abstract StayActivity build(Hotel startingPoint, Hotel arrivalPoint, PeriodOfDay periodOfDay, StayActivityType type);
}
