package ejb;

import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.ejb.EJB;
import java.util.List;
import java.io.Serializable;

/**
 * This controller manages profile.xhtml, the page where users can manage their user account profiles.
 */

@Named(value = "profileController")
@SessionScoped
public class ProfileController implements Serializable {
    @EJB
    private RegistrationEJB regEJB;
    private final String PAGE_NAME = "Your Profile";
    
    public ProfileController() {
        
    }
    
    public List<Registration> getRegList(Volunteer user) {
        List<Registration> regList = regEJB.findRegistrationsByVolunteer(user);
        return regList;
    }

    public String getPAGE_NAME() {
        return PAGE_NAME;
    }
}
