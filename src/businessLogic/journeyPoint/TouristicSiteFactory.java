package businessLogic.journeyPoint;

public class TouristicSiteFactory implements JourneyPointFactory {

	@Override
	public JourneyPoint factory(String name, String description, double confort, double attractionTime, double cost, double lunchCost, double nightcost, CategoryOfSite category) {
		if (description == null) return null;
		return new TouristicSite(confort, name, attractionTime, cost, description, category);
	}
}
