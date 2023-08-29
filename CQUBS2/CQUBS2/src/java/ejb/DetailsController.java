package ejb;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Amy
 */

@Named(value = "detailsController")
@SessionScoped
public class DetailsController implements Serializable
{
    @EJB
    private ServiceAtLocationEJB salEJB;
    
    private ServiceAtLocation sal = new ServiceAtLocation();
    
    private Long salId = 0L;
    
    private String pageName = "";
    
    public DetailsController() 
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
