package businessLogic.stay;

import java.util.ArrayList;
import java.util.List;

import businessLogic.journeyPoint.Hotel;
import businessLogic.journeyPoint.PeriodOfDay;

public class ChillTime extends StayActivity {
	
	private Hotel hotel;
	
	public ChillTime(PeriodOfDay periodOfActivity, Hotel hotel) {
		super(periodOfActivity);
		this.hotel = hotel;
	}

	@Override
	public double calculateConfort() {
		return hotel.getConfort();
	}

	@Override
	public double calculateCost() {
		return hotel.calculateCost(getPeriodOfActivity());
	}
	
	public Hotel getHotel() {
		return hotel;
	}

	@Override
	public StayActivityType getType() {
		return StayActivityType.ChillTime;
	}

	@Override
	public List<Route> getRoutes() {
		return new ArrayList<Route>();
	}

}
