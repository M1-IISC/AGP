package businessLogic.stay.builders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

import businessLogic.itinerary.Edge;
import businessLogic.itinerary.Node;
import businessLogic.itinerary.ItineraryGraph;
import businessLogic.journeyPoint.Hotel;
import businessLogic.journeyPoint.PeriodOfDay;
import businessLogic.stay.ChillTime;
import businessLogic.stay.Excursion;
import businessLogic.stay.Stay;
import businessLogic.stay.StayActivity;
import businessLogic.stay.StayActivityType;
import businessLogic.stay.StayProfile;

public class StayBuilder implements IStayBuilder {

	private ItineraryGraph itinaryGraph;
	private int stayDuration;
	private double minimumPrice;
	private double maximumPrice;
	private StayProfile profile;
	private double quality;
	private String keywords;
	
	@Override
	public Stay build(ItineraryGraph itinaryGraph, int stayDuration, double minimumPrice, double maximumPrice, StayProfile profile, double quality, String keywords) {	
		
		this.itinaryGraph = itinaryGraph;
		this.stayDuration = stayDuration;
		this.minimumPrice = minimumPrice;
		this.maximumPrice = maximumPrice;
		this.profile = profile;
		this.quality = quality;
		this.keywords = keywords;
		
		// Tools
		Random rand = new Random();
		
		// New instance of the stay
		Stay stay = new Stay();
		
		// Definition of the starting hotel
		Hotel hotel = selectBestBeginPoint();
		stay.setBeginPoint(hotel);
		
		// Plan the excursions according to the excursion profile
		IStayActivityBuilder activityBuilder = new StayActivityBuilder(itinaryGraph, (minimumPrice + maximumPrice) / 2);
		int day;
		boolean dayIsOff = false;
		for (day = 1; day <= stayDuration; day++) {
			StayActivity morningActivity;
			StayActivity afternoonActivity;
			if (dayIsOff) {
				morningActivity = activityBuilder.build(hotel, null, PeriodOfDay.Morning, StayActivityType.ChillTime);
				afternoonActivity = activityBuilder.build(hotel, null, PeriodOfDay.Afternoon, StayActivityType.ChillTime);
				dayIsOff = false; // Next day is not off
			} else {
				if (profile == StayProfile.Discovery || profile == StayProfile.Relax) {
					int randomPeriodOfExcursion = rand.nextInt(2); 
					if (randomPeriodOfExcursion == 0) {
						// The excursion will be in the morning
						morningActivity = activityBuilder.build(hotel, null, PeriodOfDay.Morning, StayActivityType.Excursion);
						// And chill time in the afternoon
						afternoonActivity = activityBuilder.build(hotel, null, PeriodOfDay.Afternoon, StayActivityType.ChillTime);
					} else {
						// The excursion will be in the afternoon
						afternoonActivity = activityBuilder.build(hotel, null, PeriodOfDay.Afternoon, StayActivityType.Excursion);
						// And chill time in the morning
						morningActivity = activityBuilder.build(hotel, null,PeriodOfDay.Morning, StayActivityType.ChillTime);
					}
					
					if (profile == StayProfile.Relax) {
						dayIsOff = true; // Next day will be off for Relax profile
					}
				} else {
					morningActivity = activityBuilder.build(hotel, null, PeriodOfDay.Morning, StayActivityType.Excursion);
					afternoonActivity = activityBuilder.build(hotel, null,PeriodOfDay.Afternoon, StayActivityType.Excursion);
				}
			}
			// Add the activity to the planning for the current day
			stay.addActivity(morningActivity);
			stay.addActivity(afternoonActivity);
		}
		
		reorganise(stay);

		return stay;
	}
	
	
	private Hotel selectBestBeginPoint() {

		Node startNode = itinaryGraph.getHead();

        Queue<Node> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        queue.add(startNode);
        visited.add(startNode.getPoint().getName());
        
        List<Node> interestingHotel = new ArrayList<>();

        while (!queue.isEmpty()) {
            Node currentNode = queue.remove();

            if (currentNode.getPoint().getAttractionTime() == 0
            		&& currentNode.getPoint().getConfort() > quality - 0.1
            		&& currentNode.getPoint().getConfort() < quality + 0.1) {
            	interestingHotel.add(currentNode);
            }

            for (Edge e : currentNode.getEdges()) {
            	Node neighbour = e.getDestination();
                if (!visited.contains(neighbour.getPoint().getName())) {
                    queue.add(neighbour);
                    visited.add(neighbour.getPoint().getName());
                }
            }
        }

        if (interestingHotel.isEmpty()) {
        	return null;
        } else {
        	Random rand = new Random();
            return (Hotel) interestingHotel.get(rand.nextInt(interestingHotel.size())).getPoint();
        }
	}
	
	private void reorganise(Stay stay) {

		Hotel lastHotel = stay.getBeginPoint();
		List<StayActivity> newActivities = new ArrayList<StayActivity>();

		for (StayActivity activity : stay.getActivities()) {
			if (activity.getType() == StayActivityType.Excursion) {
				Excursion excursion = (Excursion) activity;
				if (excursion.getRoutes().isEmpty()) {
					ChillTime chillTime = new ChillTime(excursion.getPeriodOfActivity(), lastHotel);
					newActivities.add(chillTime);
				} else {
					newActivities.add(excursion);
				}
			} else {
				newActivities.add(activity);
			}
		}
		Collections.shuffle(newActivities);
		stay.setActivities(newActivities);
	}
	
}
