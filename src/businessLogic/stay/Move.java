package businessLogic.stay;

import businessLogic.journeyPoint.PeriodOfDay;

public class Move extends StayActivity {
	private Route route;

	public Move(PeriodOfDay periodOfActivity, Route route) {
		super(periodOfActivity);
		this.route = route;
	}

	@Override
	public double calculateConfort() {
		return route.getDestination().getConfort() + route.getStrategy().calculateConfort(route.getDistance());
	}

	@Override
	public double calculateCost() {
		return route.getDestination().calculateCost(getPeriodOfActivity()) + route.getStrategy().calculatePrice(route.getDistance());
	}

	public Route getRoute() {
		return route;
	}

	@Override
	public StayActivityType getType() {
		return StayActivityType.Move;
	}

}
