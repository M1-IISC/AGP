package businessLogic.journeyPoint;

public class Hotel extends JourneyPoint {
	private double nightCost;
	private double lunchCost;
	
	public Hotel(double confort, String name, double nightCost, double lunchCost) {
		super(confort, name);
		this.nightCost = nightCost;
		this.lunchCost = lunchCost;
	}

	@Override
	public double calculateCost(PeriodOfDay period) {
		return period == PeriodOfDay.Morning ? lunchCost : nightCost;
	}

	@Override
	public String getDescription() {
		return null;
	}

	public double getNightCost() {
		return nightCost;
	}

	public double getLunchCost() {
		return lunchCost;
	}
}
