package ejb;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;

/**
 *
 * @author Amy
 */

@Named(value = "detailsStaffController")
@SessionScoped
public class DetailsStaffController implements Serializable
{
    @EJB
    private ServiceAtLocationEJB salEJB;
    
    private ServiceAtLocation sal = new ServiceAtLocation();
    
    private Long salId = 0L;
    
    private String pageName = "";
    
    public DetailsStaffController() 
    {
    }
    
    public void init()
    {
        sal = salEJB.findSALById(salId);
        
        pageName = sal.getService().getServiceName();
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

    public ServiceAtLocation getSal() 
    {
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
