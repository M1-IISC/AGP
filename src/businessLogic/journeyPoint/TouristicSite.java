package businessLogic.journeyPoint;

public class TouristicSite extends JourneyPoint {
	private double attractionTime;
	private double cost;
	private String description;

	@Override
	public double calculateCost(PeriodOfDay period) {
		return cost;
	}

	@Override
	public String getDescription() {
		return description;
	}

	public double getAttractionTime() {
		return attractionTime;
	}
}
