package ejb;

import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.context.FacesContext;
import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;
import java.text.DecimalFormat;

/**
 * This is a controller for index_staff.xhtml page.
 * Redirection to home after logging in as staff leads to index_staff page.
 */

@Named(value = "homeStaffController")
@SessionScoped
public class HomeStaffController implements Serializable {
    @EJB
    private ServiceEJB serviceEJB;
    
    private List<Service> recentServicesList = new ArrayList<>();
    private final String PAGE_NAME = "Staff Home";
    private static final DecimalFormat df = new DecimalFormat("0.00");
    
    public HomeStaffController() {
        
    }
    
    public void init() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        Staff user = (Staff) ctx.getExternalContext().getSessionMap().get("user");
        List<Service> servicesList = serviceEJB.findServicesByDepartment(user.getDepartment());
        recentServicesList = servicesList;
    }
    
    public String renderPrice(double price) {
        String priceAsString = "";
        
        if(price > 0.0)
            priceAsString = "$" + df.format(price);
        else
            priceAsString = "FREE";
        
        return priceAsString;
    }
    
    public String redirect() {
        return "index.faces";
    }

    public List<Service> getRecentServicesList() {
        return recentServicesList;
    }

    public void setRecentServicesList(List<Service> recentServicesList) {
        this.recentServicesList = recentServicesList;
    }

    public String getPAGE_NAME() {
        return PAGE_NAME;
    }
}
