package businessLogic.stay.builders;

import java.util.ArrayList;
import java.util.HashSet;
import java.lang.Math;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import businessLogic.itinaryGraph.Edge;
import businessLogic.itinaryGraph.ItineraryGraph;
import businessLogic.itinaryGraph.Node;
import businessLogic.journeyPoint.Hotel;
import businessLogic.journeyPoint.JourneyPoint;
import businessLogic.journeyPoint.PeriodOfDay;
import businessLogic.stay.ChillTime;
import businessLogic.stay.Excursion;
import businessLogic.stay.Move;
import businessLogic.stay.Route;
import businessLogic.stay.StayActivity;
import businessLogic.stay.StayActivityType;
import businessLogic.transports.TransportStrategy;

public class StayActivityBuilder implements IStayActivityBuilder {
	
	private static final int MAX_ACTIVITIES = 5;  
	private static final double MAX_TIME = 5;  
	
	private ItineraryGraph itineraryGraph;
	private double budget;
	private PeriodOfDay periodOfDay;
	
	public StayActivityBuilder(ItineraryGraph itineraryGraph, double budget) {
		this.itineraryGraph = itineraryGraph;
		this.budget = budget;
	}

	@Override
	public StayActivity build(Hotel startingPoint, Hotel arrivalPoint, PeriodOfDay periodOfDay, StayActivityType type) {
		
		this.periodOfDay = periodOfDay; 
		
		StayActivity activity = null;
		
		switch(type) {
		case ChillTime:
			activity = new ChillTime(periodOfDay, startingPoint);
			break;
		case Excursion:
			List<Route> itinerary = findBestItinerary(startingPoint);
			activity = new Excursion(periodOfDay, itinerary);
			break;
		case Move:
			activity = new Move(periodOfDay, null);
			break;
		}
		
		// Remove the cost of activity to the client's budget
		budget -= activity.calculateCost();
		
		return activity;
	}
	
	private List<Route> findBestItinerary(Hotel startingPoint) {		
		// Initialization
		double remainingActivities = 1;
		double remainingTime = 1;
		double remainingBudget = 1;
		double tiredness = 0;
		
		double initialF = remainingActivities + remainingTime + remainingBudget + tiredness;
		
		Set<Edge> open = new HashSet<>();
		Set<Edge> closed = new HashSet<>();
		
		List<Route> routes = new ArrayList<Route>();
				
		// First step with the starting node
		Node startingNode = findPointInGraph(startingPoint);
		List<Edge> edges = findAvailableEdges(startingPoint, startingNode);
		for (Edge edge : edges) {
			double F = initialF - calculateF(startingPoint, edge);
			edge.setScore(F);
			edge.setPrevious(null);
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
			edges = findAvailableEdges(startingPoint, currentEdge.getDestination());
			for (Edge edge : edges) {
				double F = calculateF(startingPoint, edge);
				F = currentEdge.getScore() - F;
				if (!closed.contains(edge)) {
					if (open.contains(edge)) {
						// This is not a new route
						if (F < edge.getScore()) {
							// We find a better path
							edge.setScore(F);
							edge.setPrevious(currentEdge);
						}
					} else {
						// This is a new route
						open.add(edge);
						edge.setScore(F);
						edge.setPrevious(currentEdge);
					}
				}
			};
		}
		
		// Build the best itinerary
		while(currentEdge.getPrevious() != null) {
			routes.add(currentEdge.createRoute());
			currentEdge = currentEdge.getPrevious();
		}
		
		return routes;
	}	
	
	private double calculateF(Hotel startingPoint, Edge edge) {
		
		TransportStrategy transport = edge.getStrategy();
		JourneyPoint attraction = edge.getDestination().getPoint();
		
		// Impact the budget
		double transportCost = transport.calculatePrice(edge.getDistance());
		double attractionCost = attraction.calculateCost(periodOfDay);
		
		double routeCost = (transportCost + attractionCost) / budget;
		
		// Impact the remaining time 
		double transportTime = transport.calculateTime(edge.getDistance());
		double attractionTime = attraction.getAttractionTime();
		
		double routeTime = (transportTime + attractionTime) / MAX_TIME;
		
		// Impact the remaining activities to do
		double numberOfActivity = 1 / MAX_ACTIVITIES;
		
		// Impact the comfort sensation
		double transportTiredness = 1 - transport.calculateConfort(edge.getDistance());
		double attractionTiredness = 1 - attraction.getConfort();
		
		double routeTiredness = Math.max(attractionTiredness, transportTiredness);
		
		
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
		}
		
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
			}
		}
		
		return routes;
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
