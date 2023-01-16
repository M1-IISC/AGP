package businessLogic.transports;

public class BoatStrategy extends TransportStrategy {
	private double cost;

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

}
