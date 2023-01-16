package businessLogic.journeyPoint;

public class TouristicSite extends JourneyPoint {
	private double attractionTime;
	private double cost;
	private String description;
	
	public TouristicSite(double confort, String name, double attractionTime, double cost, String description) {
		super(confort, name);
		this.attractionTime = attractionTime;
		this.cost = cost;
		this.description = description;
	}

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
