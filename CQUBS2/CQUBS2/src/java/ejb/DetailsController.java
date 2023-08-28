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
    
    private Long salId;
    
    private String pageName = "";
    
    public DetailsController() 
    {
    }
    
    @PostConstruct
    public void init()
    {
        List<ServiceAtLocation> salList = salEJB.findSALs();
        
        for(int i = 0; i < salList.size(); i++)
        {
            if(salList.get(i).getSalId() == salId)
                sal = salList.get(i);
        }
        
        pageName = sal.getService().getServiceName();
    }
}
