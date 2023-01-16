package businessLogic.journeyPoint;

public abstract class JourneyPoint {
	private double confort;
	private String name;
	
	public abstract double calculateCost(PeriodOfDay period);
	
	public abstract String getDescription();

	public double getConfort() {
		return confort;
	}

	public String getName() {
		return name;
	}	
}
