package businessLogic.itinaryGraph;

import java.util.List;

import businessLogic.journeyPoint.JourneyPoint;

public class Node {
	private JourneyPoint point;
	private List<Edge> edges; 

	public List<Edge> getEdges() {
		return edges;
	}

	public JourneyPoint getPoint() {
		return point;
	}
}
