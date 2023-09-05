package ejb;

import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.context.FacesContext;
import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;

/**
 *
 * @author Amy
 */

@Named(value = "homeStaffController")
@SessionScoped
public class HomeStaffController implements Serializable
{
    @EJB
    private ServiceEJB serviceEJB;
    
    private List<Service> recentServicesList = new ArrayList<>();
    
    private final String PAGE_NAME = "Staff Home";
    
    public HomeStaffController() 
    {
    }
    
    @PostConstruct
    public void init()
    {
        FacesContext ctx = FacesContext.getCurrentInstance();
        
        Staff user = (Staff) ctx.getExternalContext().getSessionMap().get("user");
        
        List<Service> servicesList = serviceEJB.findServicesByDepartment(user.getDepartment());
        
        recentServicesList = servicesList;
        
        /*recentServicesList.clear();
        
        for(int i = servicesList.size() - 1; i >= servicesList.size() - 4; i--)
            recentServicesList.add(servicesList.get(i));*/
    }
    
    public String renderPrice(double price)
    {
        String priceAsString = "";
        
        if(price > 0.0)
            priceAsString = "$" + price;
        else
            priceAsString = "FREE";
        
        return priceAsString;
    }

    public List<Service> getRecentServicesList() 
    {
        return recentServicesList;
    }

    public void setRecentServicesList(List<Service> recentServicesList) 
    {
        this.recentServicesList = recentServicesList;
    }

    public String getPAGE_NAME()
    {
        return PAGE_NAME;
    }
}
