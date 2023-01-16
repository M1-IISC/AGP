package businessLogic.journeyPoint;

public class Hotel extends JourneyPoint {
	private double nightCost;
	private double lunchCost;

	@Override
	public double calculateCost(PeriodOfDay period) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	public double getNightCost() {
		return nightCost;
	}

	public double getLunchCost() {
		return lunchCost;
	}
}
