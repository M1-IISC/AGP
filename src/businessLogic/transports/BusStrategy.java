package businessLogic.transports;

public class BusStrategy extends TransportStrategy {
	private double confortOverDistance;
	private double cost;

	public BusStrategy(double speed, double baseConfort, double confortOverDistance, double cost) {
		super(speed, baseConfort);
		this.confortOverDistance = confortOverDistance;
		this.cost = cost;
	}

	@Override
	public double calculateConfort(double distance) {
		return getBaseConfort() + confortOverDistance * distance;
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
