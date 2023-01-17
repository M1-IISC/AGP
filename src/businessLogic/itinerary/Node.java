package businessLogic.itinerary;

import java.util.List;

import businessLogic.journeyPoint.JourneyPoint;

public class Node {
	private JourneyPoint point;
	private List<Edge> edges; 
	private double accuracy;
	
	public Node(JourneyPoint point, List<Edge> edges, double accurency) {
		super();
		this.point = point;
		this.edges = edges;
		this.accuracy = accurency;
	}

	public List<Edge> getEdges() {
		return edges;
	}

	public JourneyPoint getPoint() {
		return point;
	}

	public double getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(double accurency) {
		this.accuracy = accurency;
	}
}
