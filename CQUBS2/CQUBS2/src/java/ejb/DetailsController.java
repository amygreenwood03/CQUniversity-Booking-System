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
    
    public void register()
    {
        //volunteer registration implementation
        //1 : will register user to particular sal
        //2 : will refresh user session variable to account for new entry in regList
        //3 : will return user to refreshed details page
    }
    
    public boolean registrationStatus(Volunteer user)
    {
        //will be used to check if user is currently registered or not to a specific sal
        //will return true if so, & UI won't display register button
        //will return false if not, & UI will display register button
        
        boolean isRegistered = false;
        
        if(user.getRegList() != null && !user.getRegList().isEmpty())
        {
            for(int i = 0; i < user.getRegList().size(); i++)
            {
                if(salId == user.getRegList().get(i).getSAL().getSalId())
                {
                    isRegistered = true;
                    break;
                }
            }
        }
        
        return isRegistered;
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
