package beans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;

import businessLogic.IBusinessLogicController;
import businessLogic.stay.Stay;
import spring.springContainer;


@ManagedBean
@RequestScoped
public class FormBean {
    private IBusinessLogicController iblc;
    @ManagedProperty(value = "#{indexBean}")
    private IndexBean indexBean;

    public FormBean() {
    }

    public IndexBean getIndexBean() {
        return indexBean;
    }

    public void setIndexBean(IndexBean indexBean) {
        this.indexBean = indexBean;
    }
    /*public List<Stay> findStays() { 
        iblc.searchPlansForAStay(stayDuration);
    }*/
}