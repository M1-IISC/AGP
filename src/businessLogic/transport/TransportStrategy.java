package businessLogic.transport;

public abstract class TransportStrategy {
	private double speed;
	private double baseConfort;
	
	protected TransportStrategy(double speed, double baseConfort) {
		super();
		this.speed = speed;
		this.baseConfort = baseConfort;
	}

	public abstract double calculateConfort(double distance);
	
	public abstract double calculateTime(double distance);
	
	public abstract double calculatePrice(double distance);
	
	public abstract TransportType getType();

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public void setBaseConfort(double baseConfort) {
		this.baseConfort = baseConfort;
	}

	public double getSpeed() {
		return speed;
	}

	public double getBaseConfort() {
		return baseConfort;
	}
}
