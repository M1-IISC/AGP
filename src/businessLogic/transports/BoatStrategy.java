package businessLogic.transports;

public class BoatStrategy extends TransportStrategy {
	private double cost;
	
	public BoatStrategy() {
		super();
		cost = 0;
	}

	public BoatStrategy(double speed, double baseConfort, double cost) {
		super(speed, baseConfort);
		this.cost = cost;
	}

	@Override
	public double calculateConfort(double distance) {
		return getBaseConfort();
	}

	@Override
	public double calculateTime(double distance) {
		return distance / getSpeed();
	}

	@Override
	public double calculatePrice(double distance) {
		return cost;
	}

	@Override
	public TransportType getType() {
		return TransportType.Boat;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}
}
