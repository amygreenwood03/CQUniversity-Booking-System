package ejb;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.List;

/**
 * This class controls Service Details page for users and guests.
 * Page "service_details.xhtml"
 */

@Named(value = "detailsController")
@SessionScoped
public class DetailsController implements Serializable {
    @EJB
    private ServiceAtLocationEJB salEJB; //ServiceAtLocationEJB instance
    @EJB
    private RegistrationEJB regEJB; //RegistrationEJB instance
    private ServiceAtLocation sal = new ServiceAtLocation(); //stores current sal object
    private Long salId = 0L; //stores salId passed from previous view
    private String pageName = ""; //page title
    private static final DecimalFormat df = new DecimalFormat("0.00"); //decimal formatting for prices
    
    //default constructor
    public DetailsController() {
        
    }
    
    //initialises page upon load
    public void init() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        
        if(salId > 0L) {
            sal = salEJB.findSALById(salId);
            pageName = sal.getService().getServiceName();
        }
        else {
            try {
                ctx.getExternalContext().redirect("services.faces");
            }
            catch(IOException e) {
                
            }
        }
    }
    
    //returns representation of price as a string
    public String renderPrice(double price) {
        String priceAsString = "";
        if(price > 0.0)
            priceAsString = "$" + df.format(price);
        else
            priceAsString = "FREE";
        
        return priceAsString;
    }
    
    //creates new registration for particular sal
    public String register(Volunteer user) {
        Registration reg = new Registration();
        reg.setSAL(sal);
        
        reg = regEJB.createRegistration(reg, user);
        
        FacesContext ctx = FacesContext.getCurrentInstance();
        ctx.getExternalContext().getSessionMap().put("user", reg.getVolunteer());
        
        return "service_details.faces?salId=" + sal.getSalId();
    }
    
    //returns whether volunteer is currently registered to a specific sal
    public boolean registrationStatus(Volunteer user) {    
        boolean isRegistered = false;
        List<Registration> regList = regEJB.findRegistrationsByVolunteer(user);
        
        if(regList != null && !regList.isEmpty()) {
            for(int i = 0; i < regList.size(); i++) {
                if(salId == regList.get(i).getSAL().getSalId()) {
                    isRegistered = true;
                    break;
                }
            }
        }
        
        return isRegistered;
    }

    //sal accessor
    public ServiceAtLocation getSal() {
        return sal;
    }

    //sal mutator
    public void setSal(ServiceAtLocation sal) {
        this.sal = sal;
    }

    //salId accessor
    public Long getSalId() {
        return salId;
    }

    //salId mutator
    public void setSalId(Long salId) {
        this.salId = salId;
    }

    //pageName accessor
    public String getPageName() {
        return pageName;
    }

    //pageName mutator
    public void setPageName(String pageName) {
        this.pageName = pageName;
    }    
}
