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
@RequestScoped
public class ResultOffersBean {
	
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
}
