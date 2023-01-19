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

	public IndexBean() {}
	
	
	public String startHotels() {
		//hotels = controller.getAllHotels();
		return "hotels";
	}
	
	public String startCreateYourDreamTrip() {
		return "form";
	}
	
	public String startHistoricalSites() {
		controller.getAllHistoricalSites();
		return "toursticsites";
	}


	public IBusinessLogicController getController() {
		return controller;
	}


	public void setController(IBusinessLogicController controller) {
		this.controller = controller;
	}


	public List<Hotel> getHotels() {
		return hotels;
	}


	public void setHotels(List<Hotel> hotels) {
		this.hotels = hotels;
	}

}

