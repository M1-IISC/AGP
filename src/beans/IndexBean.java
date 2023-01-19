package beans;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import businessLogic.IBusinessLogicController;
import businessLogic.journeyPoint.Hotel;
import businessLogic.journeyPoint.TouristicSite;
import spring.springContainer;

@ManagedBean
@SessionScoped
public class IndexBean {
	
	private IBusinessLogicController controller = springContainer.getBeanOfClass(IBusinessLogicController.class);
	private List<Hotel> hotels;
	private List<TouristicSite> historicalSites;
	
	public IndexBean() {
		hotels = controller.getAllHotels();
		setHistoricalSites(controller.getAllHistoricalSites());
	}
	
	
	public String startHotels() {
		return "hotels";
	}
	
	public String startCreateYourDreamTrip() {
		return "form";
	}
	
	public String startHistoricalSites() {
		return "toursticsites";
	}


	public IBusinessLogicController getController() {
		return controller;
	}


	public void setController(IBusinessLogicController controller) {
		this.controller = controller;
	}
	
	
	public double printHotelComfort(Hotel hotel) {
		double comfort = hotel.getConfort() * 5;
		return (int) comfort;
	}

	public List<Hotel> getHotels() {
		return hotels;
	}


	public void setHotels(List<Hotel> hotels) {
		this.hotels = hotels;
	}


	public List<TouristicSite> getHistoricalSites() {
		return historicalSites;
	}


	public void setHistoricalSites(List<TouristicSite> historicalSites) {
		this.historicalSites = historicalSites;
	}

}

