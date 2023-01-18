package businessLogic.stay.builders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.lang.Math;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import businessLogic.itinerary.Edge;
import businessLogic.itinerary.ItineraryGraph;
import businessLogic.itinerary.Node;
import businessLogic.journeyPoint.Hotel;
import businessLogic.journeyPoint.JourneyPoint;
import businessLogic.journeyPoint.PeriodOfDay;
import businessLogic.stay.ChillTime;
import businessLogic.stay.Excursion;
import businessLogic.stay.Move;
import businessLogic.stay.Route;
import businessLogic.stay.StayActivity;
import businessLogic.stay.StayActivityType;
import businessLogic.transport.TransportStrategy;

public class StayActivityBuilder implements IStayActivityBuilder {
	
	private static final int MAX_ACTIVITIES = 5;  
	private static final double MAX_TIME = 5;  
	
<<<<<<< Updated upstream
	ItineraryGraph itineraryGraph;
	double budget;
	PeriodOfDay periodOfDay;
=======
	private int searchId = 0;
	
	private ItineraryGraph itineraryGraph;
	private double budget;
	private PeriodOfDay periodOfDay;
	private Hotel startingPoint;
>>>>>>> Stashed changes
	
	public StayActivityBuilder(ItineraryGraph itineraryGraph) {
		this.itineraryGraph = itineraryGraph;
	}

	@Override
	public StayActivity build(Hotel startingPoint, Hotel arrivalPoint, PeriodOfDay periodOfDay, StayActivityType type) {
		
		this.periodOfDay = periodOfDay; 
<<<<<<< Updated upstream
=======
		this.startingPoint = startingPoint;
>>>>>>> Stashed changes
		
		StayActivity activity = null;
		
		switch(type) {
		case ChillTime:
			activity = new ChillTime(periodOfDay, startingPoint);
			break;
		case Excursion:
			searchId++;
			List<Route> itinerary = findBestItinerary(startingPoint);
			activity = new Excursion(periodOfDay, itinerary);
			break;
		case Move:
			//TODO add route to the other hotel
			activity = new Move(periodOfDay, null);
			break;
		}
		
		// Remove the cost of activity to the client's budget
		budget -= activity.calculateCost();
		
		return activity;
	}
	
	private List<Route> findBestItinerary(Hotel startingPoint) {		
		// Initialization
<<<<<<< Updated upstream
		double remainingActivities = 1;
		double remainingTime = 1;
		double remainingBudget = 1;
		double tiredness = 0;
		
		double initialF = remainingActivities + remainingTime + remainingBudget + tiredness;
		
=======
>>>>>>> Stashed changes
		Set<Edge> open = new HashSet<>();
		Set<Edge> closed = new HashSet<>();
		
		List<Route> routes = new ArrayList<Route>();
				
		// First step with the starting node
		Node startingNode = findPointInGraph(startingPoint);
		List<Edge> edges = startingNode.getEdges();
		for (Edge edge : edges) {
<<<<<<< Updated upstream
			double F = initialF - calculateF(startingPoint, edge);
			edge.setScore(F);
			edge.setPrevious(null);
=======
			computeNeighborScore(null, edge);
			if (edge.getScore() > 0) {
				// The edge is valid
				open.add(edge);
			}
>>>>>>> Stashed changes
		}
		
		Edge currentEdge = null;
		while(!open.isEmpty()) {
			currentEdge = getBestRoute(open);
			open.remove(currentEdge);
			closed.add(currentEdge);
			
			if (currentEdge.getDestination().getPoint().getName() == startingPoint.getName() ) {
				// STOP THE ALGO
				break;
			}
			
			// Check all attractions around the current node
			edges = currentEdge.getDestination().getEdges();
			for (Edge edge : edges) {
<<<<<<< Updated upstream
				double F = calculateF(startingPoint, edge);
				F = currentEdge.getScore() - F;
=======
>>>>>>> Stashed changes
				if (!closed.contains(edge)) {
					computeNeighborScore(currentEdge, edge);
					if (!open.contains(edge) && edge.getScore() > 0) {
						// The edge is valid and it's a new one
						open.add(edge);
					} else if (open.contains(edge) && edge.getScore() < 0) {
						open.remove(edge);
					}
				}				
			};
		}
		
		if (currentEdge == null) { return routes; }
		
		// Build the best itinerary		
		while(currentEdge != null) {
			routes.add(currentEdge.createRoute());
			if (currentEdge.getDestination().getPoint().getAttractionTime() != 0) {
				currentEdge.getDestination().setAvailable(false);	
			}	
			currentEdge = currentEdge.getPrevious(); 
		}
		
		Collections.reverse(routes); // Order the list - back to the hotel in last
		return routes;
	}	
	
<<<<<<< Updated upstream
	private double calculateF(Hotel startingPoint, Edge edge) {
=======
	
	private void computeNeighborScore(Edge currentEdge, Edge neighborEdge) {
		TransportStrategy transport = neighborEdge.getStrategy();
		JourneyPoint attraction = neighborEdge.getDestination().getPoint();
		
		if (neighborEdge.getSearchId() < searchId) {
			// This is a new search, so the edge must be re-initialized
			neighborEdge.init();
		}
>>>>>>> Stashed changes
		
		boolean neverVisited = neighborEdge.getPrevious() == null;
		
		// ------------------------------------------------------------
		// Check availability
		if (attraction.getAttractionTime() == 0 && !attraction.getName().equals(startingPoint.getName())) {
			neighborEdge.setScore(-1);
			return;
		}
		
		if (!neighborEdge.getDestination().isAvailable()) {
			neighborEdge.setScore(-1);
			return;
		}
		// ------------------------------------------------------------
		
		// ------------------------------------------------------------
		// 1) REMAINING BUDGET
		double transportCost = transport.calculatePrice(neighborEdge.getDistance());
		double attractionCost = attraction.calculateCost(periodOfDay);
		
		double edgeCost = (transportCost + attractionCost) / budget;
		
		double remainingBudget = -1;
		if (currentEdge != null) {
			remainingBudget = currentEdge.getRemainingBudget() - edgeCost;	
		} else {
			remainingBudget = neighborEdge.getRemainingBudget() - edgeCost;
		}
		
		if (remainingBudget < 0) {
			neighborEdge.setScore(-1); // This edge is not valid with this current itinerary
			return;
		} 
		
		// 2) REMAINING TIME 
		double transportTime = transport.calculateTime(neighborEdge.getDistance());
		double attractionTime = attraction.getAttractionTime();
		
		double	edgeTime = (transportTime + attractionTime) / MAX_TIME;
			
		double remainingTime = -1;
		if (currentEdge != null) {
			remainingTime = currentEdge.getRemainingTime() - edgeTime;	
		} else {
			remainingTime = neighborEdge.getRemainingTime() - edgeTime;
		}
		
		if (remainingTime < 0) {
			neighborEdge.setScore(-1); // This edge is not valid with this current itinerary
			return;
		}
		
<<<<<<< Updated upstream
		// Impact the remaining activities to do
		double numberOfActivity = 1 / MAX_ACTIVITIES;
=======
		// 3) REMAINING ACTIVITIES
		double numberOfActivity = 1.0 / (double) MAX_ACTIVITIES;
				
		double remainingActivities = -1;
		if (currentEdge != null) {
			remainingActivities = currentEdge.getRemainingActivities() - numberOfActivity;	
		} else {
			remainingActivities = neighborEdge.getRemainingActivities() - numberOfActivity;
		}
>>>>>>> Stashed changes
		
		if (remainingActivities < 0) {
			neighborEdge.setScore(-1); // This edge is not valid with this current itinerary
			return;
		}
		
		// 4) COMFORT SENSATION
		double transportTiredness = 1 - transport.calculateConfort(neighborEdge.getDistance());
		double attractionTiredness = 1 - attraction.getConfort();
		
		double edgeTiredness = Math.max(attractionTiredness, transportTiredness);
		
<<<<<<< Updated upstream
		// Add the minimal cost of the return route to the hotel
		double Hmin = Double.MAX_VALUE;
		for (Edge returnRoute : edge.getDestination().getEdges()) {
			if (returnRoute.getDestination().getPoint().getName() == startingPoint.getName()) {
				double returnTime = returnRoute.getStrategy().calculateTime(returnRoute.getDistance());
				double returnPrice = returnRoute.getStrategy().calculatePrice(returnRoute.getDistance());		
				double H = (returnTime / MAX_TIME) + (returnPrice / budget);
				if (H < Hmin) {
					Hmin = H;
				}
			}
=======
		double tiredness = -1;
		if (currentEdge != null) {
			tiredness = currentEdge.getTiredness() + edgeTiredness;	
		} else {
			tiredness = neighborEdge.getTiredness() + edgeTiredness;
>>>>>>> Stashed changes
		}
		// ------------------------------------------------------------
		
<<<<<<< Updated upstream
		return routeCost + routeTime + numberOfActivity + routeTiredness + Hmin;
	}

	private List<Edge> findAvailableEdges(Hotel startingPoint, Node node) {
		List<Edge> routes = new ArrayList<Edge>();
		
		for (Edge e : node.getEdges()) {
			double time = e.getDestination().getPoint().getAttractionTime();
			if (time != 0) {
				// It's an attraction
				routes.add(e);
			}
			if (e.getDestination().getPoint().getName() == startingPoint.getName()) {
				// It's the starting point (the hotel)
				routes.add(e);
=======
		
		// ------------------------------------------------------------
		// Calculate total score (F)
		double score = remainingBudget + remainingActivities + tiredness;
		
		if (neverVisited) {
			neighborEdge.setRemainingActivities(remainingActivities);
			neighborEdge.setRemainingBudget(remainingBudget);
			neighborEdge.setRemainingTime(remainingTime);
			neighborEdge.setTiredness(tiredness);
			neighborEdge.setScore(score);
			neighborEdge.setPrevious(currentEdge);
		} else {
			if (neighborEdge.getScore() < score) {
				// The new score is better than the old one
				neighborEdge.setRemainingActivities(remainingActivities);
				neighborEdge.setRemainingBudget(remainingBudget);
				neighborEdge.setRemainingTime(remainingTime);
				neighborEdge.setTiredness(tiredness);
				neighborEdge.setScore(score);
				neighborEdge.setPrevious(currentEdge);
>>>>>>> Stashed changes
			}
		}
	}
	
	private Edge getBestRoute(Set<Edge> set) {
		double min = Double.MAX_VALUE;
		Edge bestRoute = null;
		
		for (Edge e : set) {
			if (e.getScore() < min) {
				min = e.getScore();
				bestRoute = e;
			}
		}
		
		return bestRoute;
	}
		
	private Node findPointInGraph(JourneyPoint point) {
		
		Node startNode = itineraryGraph.getHead();
        
        Queue<Node> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        
        queue.add(startNode);
        visited.add(startNode.getPoint().getName());
        
        while (!queue.isEmpty()) {
            Node currentNode = queue.remove();
<<<<<<< Updated upstream
=======
            
            if (currentNode.getPoint().getName().equals(point.getName())) {
            	return currentNode;
            }
            
>>>>>>> Stashed changes
            for (Edge e : currentNode.getEdges()) {
            	Node neighbour = e.getDestination();
                if (!visited.contains(neighbour.getPoint().getName())) {
                	if (neighbour.getPoint().getName() == point.getName()) {
                		return neighbour;
                	}
                    queue.add(neighbour);
                    visited.add(neighbour.getPoint().getName());
                }
            }
        }
        
        return null;
    }
	
}
