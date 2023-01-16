package businessLogic.stay;

import businessLogic.journeyPoint.PeriodOfDay;

public abstract class StayActivity {
	private PeriodOfDay periodOfActivity;
	
	public abstract double calculateConfort();
	
	public abstract double calculateCost();

	public PeriodOfDay getPeriodOfActivity() {
		return periodOfActivity;
	}
}
