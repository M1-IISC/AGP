package stay.transport;

public abstract class TransportStrategy {
	private double speed;
	private double baseConfort;
	
	public abstract double calculateConfort(double distance);
	
	public abstract double calculateTime(double distance);
	
	public abstract double calculatePrice(double distance);

	public double getSpeed() {
		return speed;
	}

	public double getBaseConfort() {
		return baseConfort;
	}
}
