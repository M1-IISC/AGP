package businessLogic.journeyPoint;

public interface JourneyPointFactory {
	public abstract JourneyPoint factory(String name, String description, double confort, double attractionTime, double cost, double lunchCost, double nightcost, CategoryOfSite category);
}
