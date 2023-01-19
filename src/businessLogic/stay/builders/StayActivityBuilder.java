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
	
	private int searchId = 0;

	private ItineraryGraph itineraryGraph;
	private double budget;
	private PeriodOfDay periodOfDay;
	private Hotel startingPoint;
	
	public StayActivityBuilder(ItineraryGraph itineraryGraph, double budget) {
		this.itineraryGraph = itineraryGraph;
		this.budget = budget;
	}

	@Override
	public StayActivity build(Hotel startingPoint, Hotel arrivalPoint, PeriodOfDay periodOfDay, StayActivityType type) {
		
		this.periodOfDay = periodOfDay;
		this.startingPoint = startingPoint;
		
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
		Set<Edge> open = new HashSet<>();
		Set<Edge> closed = new HashSet<>();
		
		List<Route> routes = new ArrayList<Route>();
				
		// First step with the starting node
		Node startingNode = findPointInGraph(startingPoint);
		List<Edge> edges = startingNode.getEdges();
		for (Edge edge : edges) {
			computeNeighborScore(null, edge);
			if (edge.getScore() > 0) {
				// The edge is valid, so we add it
				open.add(edge);
			}
		}
		
		Edge currentEdge = null;
		while(!open.isEmpty()) {
			currentEdge = getBestRoute(open);
			open.remove(currentEdge);
			closed.add(currentEdge);
			
			if (currentEdge.getDestination().getPoint().getName().equals(startingPoint.getName())) {
				// STOP THE ALGO
				break;
			}
			
			// Check all edges linked to the current node
			edges = currentEdge.getDestination().getEdges();
			for (Edge edge : edges) {
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
		
		// No itinerary
		if (currentEdge == null) { return routes; }

		// Build the best itinerary		
		while(currentEdge != null) {
			routes.add(currentEdge.createRoute());
			if (currentEdge.getDestination().getPoint().getAttractionTime() != 0) {
				// Newly visited tourist sites (during this excursion will no longer be offered)
				currentEdge.getDestination().setAvailable(false);	
			}	
			currentEdge = currentEdge.getPrevious(); 
		}
		
		Collections.reverse(routes); // Order the list - back to the hotel in last
		return routes;
	}	
	
	
	private void computeNeighborScore(Edge currentEdge, Edge neighborEdge) {
		TransportStrategy transport = neighborEdge.getStrategy();
		JourneyPoint attraction = neighborEdge.getDestination().getPoint();

		if (neighborEdge.getSearchId() < searchId) {
			// This is a new search, so the edge must be re-initialized
			neighborEdge.init();
		}
		
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
		
		// 3) REMAINING ACTIVITIES
		double numberOfActivity = 1.0 / (double) MAX_ACTIVITIES;

		double remainingActivities = -1;
		if (currentEdge != null) {
			remainingActivities = currentEdge.getRemainingActivities() - numberOfActivity;	
		} else {
			remainingActivities = neighborEdge.getRemainingActivities() - numberOfActivity;
		}
		
		if (remainingActivities < 0) {
			neighborEdge.setScore(-1); // This edge is not valid with this current itinerary
			return;
		}
		
		// 4) COMFORT SENSATION
		double transportTiredness = 1 - transport.calculateConfort(neighborEdge.getDistance());
		double attractionTiredness = 1 - attraction.getConfort();

		double edgeTiredness = Math.max(attractionTiredness, transportTiredness);
		
		double tiredness = -1;
		if (currentEdge != null) {
			tiredness = currentEdge.getTiredness() + edgeTiredness;	
		} else {
			tiredness = neighborEdge.getTiredness() + edgeTiredness;
		}
		
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

            if (currentNode.getPoint().getName().equals(point.getName())) {
            	return currentNode;
            }
            
            for (Edge e : currentNode.getEdges()) {
            	Node neighbour = e.getDestination();
                if (!visited.contains(neighbour.getPoint().getName())) {
                    queue.add(neighbour);
                    visited.add(neighbour.getPoint().getName());
                }
            }
        }
        
        return null;
    }
	
}
