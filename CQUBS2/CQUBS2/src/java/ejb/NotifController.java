package ejb;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.List;

/**
 *
 * @author Amy
 */

@Named(value = "notifController")
@SessionScoped
public class NotifController implements Serializable
{
    @EJB
    private ServiceAtLocationEJB salEJB;
    
    @EJB
    private RegistrationEJB regEJB;
    
    private Long salId = 0L;
    
    private ServiceAtLocation sal = new ServiceAtLocation();
    
    private String firstName, lastName, phone, detailsText;
    
    private final String PAGE_NAME = "Send Email Notification";
    
    private static final DecimalFormat df = new DecimalFormat("0.00");
    
    public NotifController() 
    {
    }
    
    public void init()
    {
        FacesContext ctx = FacesContext.getCurrentInstance();
        
        firstName = "";
        lastName = "";
        phone = "";
        detailsText = "";
        
        if(salId > 0L)
            sal = salEJB.findSALById(salId);
        else {
            try {
                ctx.getExternalContext().redirect("service_details_staff.faces");
            }
            catch(IOException e) {
                
            }
        }
    }
    
    public void sendEmail()
    {
        //to be implemented
    }
    
    public String renderPrice(double price) {
        String priceAsString = "";
        if(price > 0.0)
            priceAsString = "$" + df.format(price);
        else
            priceAsString = "FREE";
        
        return priceAsString;
    }
    
    public List<Registration> getRegList(ServiceAtLocation servAtLocation)
    {
        List<Registration> regList = regEJB.findRegistrationsBySAL(servAtLocation);
        return regList;
    }

    public Long getSalId() 
    {
        return salId;
    }

    public void setSalId(Long salId) 
    {
        this.salId = salId;
    }

    public ServiceAtLocation getSal() 
    {
        return sal;
    }

    public void setSal(ServiceAtLocation sal) 
    {
        this.sal = sal;
    }

    public String getFirstName() 
    {
        return firstName;
    }

    public void setFirstName(String firstName) 
    {
        this.firstName = firstName;
    }

    public String getLastName() 
    {
        return lastName;
    }

    public void setLastName(String lastName) 
    {
        this.lastName = lastName;
    }

    public String getPhone() 
    {
        return phone;
    }

    public void setPhone(String phone) 
    {
        this.phone = phone;
    }

    public String getDetailsText() 
    {
        return detailsText;
    }

    public void setDetailsText(String detailsText) 
    {
        this.detailsText = detailsText;
    }

    public String getPAGE_NAME() 
    {
        return PAGE_NAME;
    }
}
