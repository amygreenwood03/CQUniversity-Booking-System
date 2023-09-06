package ejb;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.text.DecimalFormat;

/**
 * This class controls Service Details page for users and guests.
 * Page "service_details_staff.xhtml"
 */

@Named(value = "detailsStaffController")
@SessionScoped
public class DetailsStaffController implements Serializable {
    @EJB
    private ServiceAtLocationEJB salEJB;
    
    private ServiceAtLocation sal = new ServiceAtLocation();
    private Long salId = 0L;
    private String pageName = "";
    private static final DecimalFormat df = new DecimalFormat("0.00");
    
    public DetailsStaffController() {
        
    }
    
    public void init() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        
        if(salId > 0L) {
            sal = salEJB.findSALById(salId);
            pageName = sal.getService().getServiceName();
        }
        else {
            try {
                ctx.getExternalContext().redirect("services_staff.faces");
            }
            catch(IOException e)
            {
                
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

    public ServiceAtLocation getSal() {
        return sal;
    }

    public void setSal(ServiceAtLocation sal) 
    {
        this.sal = sal;
    }

    public Long getSalId() 
    {
        return salId;
    }

    public void setSalId(Long salId) 
    {
        this.salId = salId;
    }

    public String getPageName() 
    {
        return pageName;
    }

    public void setPageName(String pageName) 
    {
        this.pageName = pageName;
    }
}
