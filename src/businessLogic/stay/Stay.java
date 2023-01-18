package businessLogic.stay;

import java.util.ArrayList;
import java.util.List;

import businessLogic.journeyPoint.Hotel;

public class Stay {
	private Hotel beginPoint;
	private List<StayActivity> activities = new ArrayList<StayActivity>();
	
	
	public double calculateConfort()
	{
		//TODO implementation
		return 0;
	}
	
	public double calculateCost()
	{
		//TODO implementation
		return 0;
	}

	public Hotel getBeginPoint() {
		return beginPoint;
	}
	
	public void setBeginPoint(Hotel hotel) {
		this.beginPoint = hotel;
	}
	
	public void addActivity(StayActivity activity) {
		activities.add(activity);
	}

	public List<StayActivity> getActivities() {
		return activities;
	}
	
	
}
