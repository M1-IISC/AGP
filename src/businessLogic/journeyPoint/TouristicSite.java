package businessLogic.journeyPoint;

public class TouristicSite extends JourneyPoint {
	private double attractionTime;
	private double cost;
	private String description;
	private CategoryOfSite category;
	
	public TouristicSite(double confort, String name, double attractionTime, double cost, String description, CategoryOfSite category) {
		super(confort, name);
		this.attractionTime = attractionTime;
		this.cost = cost;
		this.description = description;
		this.category = category;
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

	public CategoryOfSite getCategory() {
		return category;
	}
}
