package businessLogic.itinaryGraph;

import businessLogic.transports.TransportStrategy;

public class Edge {
	private TransportStrategy strategy;
	private double distance;
	private Node destination;
	
	public TransportStrategy getStrategy() {
		return strategy;
	}
	
	public double getDistance() {
		return distance;
	}
	
	public Node getDestination() {
		return destination;
	}
}
