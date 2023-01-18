package beans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;

import businessLogic.IBusinessLogicController;
import businessLogic.stay.Stay;
import businessLogic.stay.StayProfile;
import spring.springContainer;


@ManagedBean
@SessionScoped
public class FormBean {
    private IBusinessLogicController iblc;
    private List<Stay> stays;
    private int stayDuration = 7;
    private double minimumPrice = 1;
    private double maximumPrice = 1;
    private StayProfile profile = StayProfile.Relax;
    private double quality = 3;
    private String keywords = "";
    
    @ManagedProperty(value = "#{indexBean}")
    private IndexBean indexBean;

    public FormBean() {
    }

    public IBusinessLogicController getIblc() {
		return iblc;
	}

	public void setIblc(IBusinessLogicController iblc) {
		this.iblc = iblc;
	}

	public List<Stay> getStays() {
		return stays;
	}

	public void setStays(List<Stay> stays) {
		this.stays = stays;
	}

	public int getStayDuration() {
		return stayDuration;
	}

	public void setStayDuration(int stayDuration) {
		this.stayDuration = stayDuration;
	}

	public double getMinimumPrice() {
		return minimumPrice;
	}

	public void setMinimumPrice(double minimumPrice) {
		this.minimumPrice = minimumPrice;
	}

	public double getMaximumPrice() {
		return maximumPrice;
	}

	public void setMaximumPrice(double maximumPrice) {
		this.maximumPrice = maximumPrice;
	}

	public StayProfile getProfile() {
		return profile;
	}

	public void setProfile(StayProfile profile) {
		this.profile = profile;
	}

	public double getQuality() {
		return quality;
	}

	public void setQuality(double quality) {
		this.quality = (float) quality / 5;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public IndexBean getIndexBean() {
        return indexBean;
    }

    public void setIndexBean(IndexBean indexBean) {
        this.indexBean = indexBean;
    }
    
    
	public String findStays() { 
        //stays = iblc.searchPlansForAStay(stayDuration, minimumPrice, maximumPrice, profile, quality, keywords);
        return "resultOffers";
    }
	
	
}