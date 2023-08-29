package ejb;

import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 *
 * @author Amy
 */

@Named(value = "aboutController")
@SessionScoped
public class AboutController implements Serializable
{
    private final String PAGE_NAME = "About Us";
    
    public AboutController() 
    {
        
    }

    public String getPAGE_NAME() 
    {
        return PAGE_NAME;
    }
}
