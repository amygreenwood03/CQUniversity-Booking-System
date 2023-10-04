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
    private ServiceEJB serviceEJB; //ServiceEJB instance
    
    private List<Service> recentServicesList = new ArrayList<>(); //stores most recent services in database
    private final String PAGE_NAME = "Staff Home"; //page title
    private static final DecimalFormat df = new DecimalFormat("0.00"); //decimal formatting for prices
    
    //default constructor
    public HomeStaffController() {
        
    }
    
    //initialises page upon load
    public void init() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        Staff user = (Staff) ctx.getExternalContext().getSessionMap().get("user");
        List<Service> servicesList = serviceEJB.findServicesByDepartment(user.getDepartment());
        recentServicesList = servicesList;
    }
    
    //returns representation of price as string
    public String renderPrice(double price) {
        String priceAsString = "";
        
        if(price > 0.0)
            priceAsString = "$" + df.format(price);
        else
            priceAsString = "FREE";
        
        return priceAsString;
    }

    //recentServicesList accessor
    public List<Service> getRecentServicesList() {
        return recentServicesList;
    }

    //recentServicesList mutator
    public void setRecentServicesList(List<Service> recentServicesList) {
        this.recentServicesList = recentServicesList;
    }

    //PAGE_NAME accessor
    public String getPAGE_NAME() {
        return PAGE_NAME;
    }
}
