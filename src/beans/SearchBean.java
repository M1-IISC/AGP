package beans;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import businessLogic.IBusinessLogicController;
import businessLogic.journeyPoint.TouristicSite;
import spring.springContainer;

@ManagedBean
@RequestScoped
public class SearchBean {
		
	private IBusinessLogicController controller = springContainer.getBeanOfClass(IBusinessLogicController.class);
	
    @ManagedProperty(value = "#{indexBean}")
    private IndexBean indexBean;
	
	public SearchBean() {}
	
	public String back() {
		return "index?faces-redirect=true";
	}
	
	public int resultsLenght() {
		return indexBean.getResults().size();
	}

	public List<TouristicSite> getResults() {
		return indexBean.getResults();
	}


	public IndexBean getIndexBean() {
		return indexBean;
	}

	public void setIndexBean(IndexBean indexBean) {
		this.indexBean = indexBean;
	}
}
