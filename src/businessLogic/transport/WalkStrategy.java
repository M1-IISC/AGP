package businessLogic.transport;

public class WalkStrategy extends TransportStrategy {
	private double confortOverDistance;

	public WalkStrategy() {
		super();
		confortOverDistance = 0;
	}

	public WalkStrategy(double speed, double baseConfort, double confortOverDistance) {
		super(speed, baseConfort);
		this.confortOverDistance = confortOverDistance;
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
		return 0;
	}

	@Override
	public TransportType getType() {
		return TransportType.WALK;
	}

	public double getConfortOverDistance() {
		return confortOverDistance;
	}

	public void setConfortOverDistance(double confortOverDistance) {
		this.confortOverDistance = confortOverDistance;
	}
	
	
}
