package businessLogic.stay;

import java.util.List;

import businessLogic.journeyPoint.PeriodOfDay;

public class Excursion extends StayActivity {
	private List<Route> routes;

	public Excursion(PeriodOfDay periodOfActivity, List<Route> routes) {
		super(periodOfActivity);
		this.routes = routes;
	}

	@Override
	public double calculateConfort() {
		double accumulator = 0.;
		for(Route route : routes){
			accumulator += route.getDestination().getConfort() + route.getStrategy().calculateConfort(route.getDistance());
		}
		return accumulator;
	}

	@Override
	public double calculateCost() {
		double accumulator = 0.;
		for(Route route : routes){
			accumulator += route.getDestination().calculateCost(getPeriodOfActivity()) + route.getStrategy().calculateConfort(route.getDistance());
		}
		return accumulator;
	}

	public List<Route> getRoutes() {
		return routes;
	}

	@Override
	public StayActivityType getType() {
		return StayActivityType.Excursion;
	}

}
