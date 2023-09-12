package ejb;

import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.ejb.EJB;
import jakarta.faces.context.FacesContext;
import java.util.List;
import java.io.Serializable;

/**
 * This controller manages profile_staff.xhtml, the page where staffs can manage their user account profiles.
 */

@Named(value = "profileStaffController")
@SessionScoped
public class ProfileStaffController implements Serializable {
    @EJB
    private RegistrationEJB regEJB;
    
    @EJB
    private UsersEJB userEJB;
    
    private Staff staff;
    private final String PROFILE_NAME = "Your Profile";
    private final String EDIT_NAME = "Edit Your Profile";
    
    private String department = "If you see this text, Initialisation did not work.";
    
    public ProfileStaffController() {
        
    }
  
    public void init(Staff user) {
        staff = userEJB.findStaffById(user.getId());
        department = staff.getDepartment().getDepartmentName();
    }
    
    public String edit() {
        userEJB.updateStaff(staff);
        
        FacesContext ctx = FacesContext.getCurrentInstance();
        ctx.getExternalContext().getSessionMap().put("user", staff);
        return "profile_staff.faces";
    }
    
    public List<Registration> getRegList(Volunteer user) {
        List<Registration> regList = regEJB.findRegistrationsByVolunteer(user);
        return regList;
    }

    public String getPROFILE_NAME() {
        return PROFILE_NAME;
    }
    
    public String getEDIT_NAME() {
        return EDIT_NAME;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public RegistrationEJB getRegEJB() {
        return regEJB;
    }

    public void setRegEJB(RegistrationEJB regEJB) {
        this.regEJB = regEJB;
    }

    public UsersEJB getUserEJB() {
        return userEJB;
    }

    public void setUserEJB(UsersEJB userEJB) {
        this.userEJB = userEJB;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}