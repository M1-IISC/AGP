package businessLogic.stay;

import java.util.List;

import businessLogic.journeyPoint.Hotel;

public class Stay {
	private Hotel beginPoint;
	private List<StayActivity> activities;
	
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

	
	public void setActivities(List<StayActivity> activities) {
		this.activities = activities;
	}
	
	
	public List<StayActivity> getActivities() {
		return activities;
	}
	
	
}
