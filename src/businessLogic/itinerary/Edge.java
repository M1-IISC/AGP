package businessLogic.itinerary;

import businessLogic.stay.Route;
import businessLogic.transport.TransportStrategy;

public class Edge {
	private TransportStrategy strategy;
	private double distance;
	private Node destination;
	
	private int searchId;
	
	private Edge previous;
	private double score;
	private double remainingTime;
	private double remainingActivities;
	private double remainingBudget;
	private double tiredness;
	
	public Edge(TransportStrategy strategy, double distance, Node destination) {
		super();
		this.strategy = strategy;
		this.distance = distance;
		this.destination = destination;
		
		// Default value
		init();
	}
	
	public void init() {
		this.searchId = 0;
		this.previous = null;
		this.score = Double.MAX_VALUE;
		this.remainingTime = 1;
		this.remainingActivities = 1;
		this.remainingBudget = 1;
		this.tiredness = 0;
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
	

	public double getRemainingTime() {
		return remainingTime;
	}
	

	public void setRemainingTime(double remainingTime) {
		this.remainingTime = remainingTime;
	}
	

	public double getRemainingActivities() {
		return remainingActivities;
	}
	

	public void setRemainingActivities(double remainingActivities) {
		this.remainingActivities = remainingActivities;
	}
	

	public double getRemainingBudget() {
		return remainingBudget;
	}
	

	public void setRemainingBudget(double remainingBudget) {
		this.remainingBudget = remainingBudget;
	}
	

	public double getTiredness() {
		return tiredness;
	}
	

	public void setTiredness(double tiredness) {
		this.tiredness = tiredness;
	}
	

	public int getSearchId() {
		return searchId;
	}
	

	public void setSearchId(int searchId) {
		this.searchId = searchId;
	}
}
