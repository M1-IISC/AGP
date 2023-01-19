package beans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import businessLogic.IBusinessLogicController;
import businessLogic.stay.Stay;
import businessLogic.stay.StayProfile;
import spring.springContainer;

@ManagedBean
@ViewScoped
public class ResultOffersBean {
    private IBusinessLogicController contro = springContainer.getBeanOfClass(IBusinessLogicController.class);
    
	@ManagedProperty(value = "#{formBean}")
    private FormBean formBean;
	
	
	public ResultOffersBean() {
		
	}
	
	public FormBean getFormBean() {
	   return formBean;
	}

    public void setFormBean(FormBean formBean) {
        this.formBean = formBean;
    }
    
    public List<Stay> getStays() {
    	return formBean.getStays();
    }
    
    public int getStayDuration() {
    	return formBean.getStayDuration();
    }

	public IBusinessLogicController getContro() {
		return contro;
	}

	public void setContro(IBusinessLogicController contro) {
		this.contro = contro;
	}
	
	
	public String printComfort(Stay stay) {
		double comfort = stay.calculateConfort() * 5;
		return String.valueOf((int)comfort);
	}
	
	public String descStay(Stay stay) {
		formBean.getIndexBean().setSelectedStay(stay);
        return "descriptionStay";
    }

}
