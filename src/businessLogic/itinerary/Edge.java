package businessLogic.itinerary;

import businessLogic.stay.Route;
import businessLogic.transports.TransportStrategy;

public class Edge {
	private TransportStrategy strategy;
	private double distance;
	private Node destination;
	
	private double score;
	private Edge previous;
	
	public Edge(TransportStrategy strategy, double distance, Node destination) {
		super();
		this.strategy = strategy;
		this.distance = distance;
		this.destination = destination;
	}
	
	public Route createRoute() {
		return new Route(this.destination.getPoint(), this.strategy, this.distance);
	}
	
	public TransportStrategy getStrategy() {
		return strategy;
	}
	
	
	public double getDistance() {
		return distance;
	}
	
	
	public Node getDestination() {
		return destination;
	}


	public double getScore() {
		return score;
	}


	public void setScore(double score) {
		this.score = score;
	}

	
	public Edge getPrevious() {
		return previous;
	}


	public void setPrevious(Edge previous) {
		this.previous = previous;
	}
}
