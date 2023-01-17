package businessLogic.journeyPoint;

public class HotelFactory implements JourneyPointFactory {

	@Override
	public JourneyPoint factory(String name, String description, double confort, double attractionTime, double cost, double lunchCost, double nightcost, CategoryOfSite category) {
		if (description != null) return null;
		return new Hotel(confort, name, nightcost, lunchCost);
	}


}
