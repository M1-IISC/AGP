package businessLogic.stay;

import java.util.ArrayList;
import java.util.List;

import businessLogic.journeyPoint.Hotel;
import businessLogic.journeyPoint.PeriodOfDay;

public class Stay {
	private Hotel beginPoint;
	private List<StayActivity> activities = new ArrayList<StayActivity>();
	
	
	public double calculateConfort()
	{
		double accumulator = beginPoint.getConfort();
		double count = 1;
		for (StayActivity stayActivity : activities) {
			accumulator+=stayActivity.calculateConfort();
			count++;
		}
		return accumulator / count;
	}
	
	public double calculateCost()
	{
		double accumulator = 0;
		for (StayActivity stayActivity : activities) {
			accumulator+=stayActivity.calculateCost();
		}
		return accumulator + beginPoint.calculateCost(PeriodOfDay.Afternoon);
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
