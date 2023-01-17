package businessLogic.stay;

import businessLogic.journeyPoint.JourneyPoint;
import businessLogic.transports.TransportStrategy;

public class Route {
	private final JourneyPoint destination;
	private final TransportStrategy strategy;
	private final double distance;
		
	public Route(JourneyPoint destination, TransportStrategy strategy, double distance) {
		super();
		this.destination = destination;
		this.strategy = strategy;
		this.distance = distance;
	}
	
	public JourneyPoint getDestination() {
		return destination;
	}
	
	public TransportStrategy getStrategy() {
		return strategy;
	}

	public double getDistance() {
		return distance;
	}
}
