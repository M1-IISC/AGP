package beans;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import businessLogic.IBusinessLogicController;
import businessLogic.stay.Route;
import businessLogic.stay.Stay;
import businessLogic.stay.StayActivity;
import businessLogic.stay.StayActivityType;
import businessLogic.stay.StayProfile;
import spring.springContainer;

@ManagedBean
@ViewScoped
public class DescriptionStayBean {
    private IBusinessLogicController contro2 = springContainer.getBeanOfClass(IBusinessLogicController.class);

	@ManagedProperty(value = "#{resultOffersBean}")
    private ResultOffersBean resultOffersBean;

	private Stay stay;
	
	public DescriptionStayBean() {
		
	}

	public String back() {
		return "form?faces-redirect=true";
	}

	public IBusinessLogicController getContro2() {
		return contro2;
	}


	public void setContro2(IBusinessLogicController contro2) {
		this.contro2 = contro2;
	}


	public ResultOffersBean getResultOffersBean() {
		return resultOffersBean;
	}


	public void setResultOffersBean(ResultOffersBean resultOffersBean) {
		this.resultOffersBean = resultOffersBean;
	}
	
	public Stay getStay() {
		if (stay == null) {
			stay = resultOffersBean.getFormBean().getIndexBean().getSelectedStay();
		}
		return stay;
	}
	
	public void setStay(Stay stay) {
		this.stay = stay;
	}


	public List<Integer> listOfDays() {
		List<Integer> days = new ArrayList<Integer>();
		int day;
		for(day = 1; day <= resultOffersBean.getFormBean().getStayDuration(); day++) {
			days.add(day);
		}
		return days;
	}
	
	public List<StayActivity> getActivities() {
		return getStay().getActivities();
	}
	
	public StayActivity getActivityOfMorning(int day) {
		int index = (day * 2);
		index = index - 2;
		if (index < 0 || index >=  getStay().getActivities().size()) {
			index = 0;
		}
		return getStay().getActivities().get(index);
	}
	
	public StayActivity getActivityOfAfternoon(int day) {
		int index = (day * 2);
		index = index - 1;
		if (index < 0 || index >= getStay().getActivities().size()) {
			index = 0;
		}
		return getStay().getActivities().get(index);
	}
	
	public String nameOfSite(Route route) {

		
		if (route.getDestination().getAttractionTime() == 0) {
			// It's an hotel
			return "🏨 Retour à l'hôtel";
		} else {
			double attractionTimeDouble = route.getDestination().getAttractionTime() * 60;
			int attractionTime = (int) attractionTimeDouble;
			double attractionCostDouble = route.getDestination().calculateCost(null);
			int attractionCost = (int) attractionCostDouble;
			return "📷 Visite : " + route.getDestination().getName() + " - Durée sur place : " + attractionTime + " minutes - Prix : " + attractionCost + " €";
		}
	}
	
	public String nameOfHotel(Route route) {
		if (route.getDestination().getAttractionTime() == 0) {
			// It's an hotel
			return route.getDestination().getName();
		}
		return "";
	}
	
	public String transport(Route route) {
		String transport = "";
		double timeDouble = route.getStrategy().calculateTime(route.getDistance()) * 60;
		int time = (int) timeDouble;
		double costDouble = route.getStrategy().calculatePrice(route.getDistance());
		int cost = (int) costDouble;
		
		switch (route.getStrategy().getType()) {
			case BOAT:
				transport = " 🛥️  Trajet en bateau (" + String.valueOf(time) + " minutes, environ "+ cost +" €)";
				break;
			case BUS:
				transport = "🚍  Trajet en bus (" + String.valueOf(time) + " minutes, environ " + cost + " €)";
				break;
			case WALK:
				transport = "🚶  Trajet à pied (" + String.valueOf(time) + " minutes, environ "+ cost + " €)";
				break;
			default:
				break;
			
		}
		return transport;
	}
	
	public String descriptionOfSite(Route route) {
		return route.getDestination().getDescription();
	}
	
	public boolean isExcursion(StayActivity activity) {
		if (activity.getType() == StayActivityType.Excursion) {
			return true;
		}
		return false;
	}
	
	public boolean checkType(StayActivity activity, String test) {
		return activity.getType().toString().equals(test);
	}
	
	public boolean isChillTime(StayActivity activity) {
		if (activity.getType() == StayActivityType.ChillTime) {
			return true;
		}
		return false;
	}
	
	public boolean isMove(StayActivity activity) {
		if (activity.getType() == StayActivityType.Move) {
			return true;
		}
		return false;
	}
}
