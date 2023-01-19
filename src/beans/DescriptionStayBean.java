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
public class DescriptionStayBean {
    private IBusinessLogicController contro2 = springContainer.getBeanOfClass(IBusinessLogicController.class);

	@ManagedProperty(value = "#{resultOffersBean}")
    private ResultOffersBean resultOffersBean;

	
	public DescriptionStayBean() {
		
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
		return resultOffersBean.getFormBean().getIndexBean().getSelectedStay();
	}
	
	/*public String printComfort(Stay stay) {
		if (stay != null) {
			double comfort = stay.calculateConfort() * 5;
			return String.valueOf((int)comfort);
		}
		return "null";
	}*/
	
}
