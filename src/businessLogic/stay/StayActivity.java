package businessLogic.stay;

import java.util.List;

import businessLogic.journeyPoint.PeriodOfDay;

public abstract class StayActivity {
	private PeriodOfDay periodOfActivity;
	
	protected StayActivity(PeriodOfDay periodOfActivity) {
		super();
		this.periodOfActivity = periodOfActivity;
	}

	public abstract double calculateConfort();
	
	public abstract double calculateCost();
	
	public abstract List<Route> getRoutes();
	
	public abstract StayActivityType getType();

	public PeriodOfDay getPeriodOfActivity() {
		return periodOfActivity;
	}
	
	public boolean isExcursion() {
		return getType() == StayActivityType.Excursion;
	}
	
	public boolean isChillTime() {
		return getType() == StayActivityType.ChillTime;
	}
	
}
