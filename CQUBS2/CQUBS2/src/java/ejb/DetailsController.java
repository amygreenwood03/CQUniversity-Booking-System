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
    private ServiceAtLocationEJB salEJB;
    @EJB
    private RegistrationEJB regEJB;
    private ServiceAtLocation sal = new ServiceAtLocation();
    private Long salId = 0L;
    private String pageName = "";
    private static final DecimalFormat df = new DecimalFormat("0.00");
    
    public DetailsController() {
        
    }
    
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
    
    public String renderPrice(double price) {
        String priceAsString = "";
        if(price > 0.0)
            priceAsString = "$" + df.format(price);
        else
            priceAsString = "FREE";
        
        return priceAsString;
    }
    
    public String register(Volunteer user) {
        /**
         * volunteer registration implementation
         * 1 : will register user to particular sal
         * 2 : will refresh user session variable to account for new entry in regList
         * 3 : will return user to refreshed details page
         */
        Registration reg = new Registration();
        reg.setSAL(sal);
        
        reg = regEJB.createRegistration(reg, user);
        
        FacesContext ctx = FacesContext.getCurrentInstance();
        ctx.getExternalContext().getSessionMap().put("user", reg.getVolunteer());
        
        return "service_details.faces?salId=" + sal.getSalId();
    }
    
    public boolean registrationStatus(Volunteer user) {
        /**
         * will be used to check if user is currently registered or not to a specific sal
         * will return true if so, & UI won't display register button
         * will return false if not, & UI will display register button
         */        
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

    public ServiceAtLocation getSal() {
        return sal;
    }

    public void setSal(ServiceAtLocation sal) {
        this.sal = sal;
    }

    public Long getSalId() {
        return salId;
    }

    public void setSalId(Long salId) {
        this.salId = salId;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }    
}
