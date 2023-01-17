package beans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import businessLogic.IBusinessLogicController;
import businessLogic.journeyPoint.Hotel;
import businessLogic.journeyPoint.TouristicSite;

@ManagedBean
@SessionScoped
public class IndexBean {

    private IBusinessLogicController controleur ; 

    public IndexBean() { 
    }

    /*public  List<Hotel> startHostels() {
        controleur.getAllHotels();
        return "Hotels'; 
    }*/

    public String startCreateYourDreamTrip() {
        return "form";
    }

/*    public List<TouristicSite> startHistoricalSites() {
        controleur.getAllHistoricalSites();
        return "ToursticSites";
    }/

    /public List<TouristicSite> startHistoricalSites() {
        controleur.getAllHistoricalSites();
        return "ToursticSites";*/



}