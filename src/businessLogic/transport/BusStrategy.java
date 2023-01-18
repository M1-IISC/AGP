package businessLogic.transport;

public class BusStrategy extends TransportStrategy {
	private double confortOverDistance;
	private double cost;
	
	public BusStrategy() {
		super();
		confortOverDistance = 0;
		cost = 0;
	}

	public BusStrategy(double speed, double baseConfort, double confortOverDistance, double cost) {
		super(speed, baseConfort);
		this.confortOverDistance = confortOverDistance;
		this.cost = cost;
	}

	public double getConfortOverDistance() {
		return confortOverDistance;
	}

	public void setConfortOverDistance(double confortOverDistance) {
		this.confortOverDistance = confortOverDistance;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	@Override
	public double calculateConfort(double distance) {
		double conf = getBaseConfort() + confortOverDistance * distance;
		return conf > 0 ? conf : 0;
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
		return TransportType.BUS;
	}
}
