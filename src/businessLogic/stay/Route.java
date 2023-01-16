package businessLogic.stay;

import businessLogic.journeyPoint.JourneyPoint;
import businessLogic.transports.TransportStrategy;

public class Route {
	private final JourneyPoint destination;
	private final TransportStrategy strategy;
		
	public Route(JourneyPoint destination, TransportStrategy strategy) {
		super();
		this.destination = destination;
		this.strategy = strategy;
	}
	
	public JourneyPoint getDestination() {
		return destination;
	}
	
	public TransportStrategy getStrategy() {
		return strategy;
	}	
}
