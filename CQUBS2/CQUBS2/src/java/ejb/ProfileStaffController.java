package ejb;

import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
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
    
    private String firstName, lastName, email, phone;
    
    public ProfileStaffController() {
        
    }
  
    public void init(Staff user) {
        staff = userEJB.findStaffById(user.getId());
        
        firstName = staff.getFirstName();
        lastName = staff.getLastName();
        email = staff.getEmail();
        phone = staff.getPhone();
    }
    
    public boolean checkFields()
    {
        if(firstName.isBlank() || lastName.isBlank() || email.isBlank() || phone.isBlank())
            return true;
        else if(!phone.matches("^[0-9]{10}$") || !email.matches("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\."
            + "[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@"
            + "(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9]"
            + "(?:[a-z0-9-]*[a-z0-9])?"))
            return true;
        
        return false;
    }
    
    public String edit(Staff user) {
        FacesContext ctx = FacesContext.getCurrentInstance();
        FacesMessage editError = new FacesMessage("", "One or more fields are empty or filled incorrectly. Please check and try again.");
        
        if(checkFields())
        {
            ctx.addMessage("editForm", editError);
            return null;
        }
        
        Staff sTest;
        Volunteer vTest;
        
        try {
            sTest = userEJB.findStaffByEmail(email);
            
            if(user.getId() == sTest.getId())
                sTest = null;
        } 
        catch (Exception e) {
            sTest = null;
        }
        
        try {
            vTest = userEJB.findVolByEmail(email);
        } 
        catch (Exception e) {
            vTest = null;
        }
        
        if(sTest != null || vTest != null)
        {
            ctx.addMessage("editForm", new FacesMessage("", "That email is already in use by another user."));
            return null;
        }
        
        staff.setFirstName(firstName);
        staff.setLastName(lastName);
        staff.setEmail(email);
        staff.setPhone(phone);
        
        userEJB.updateStaff(staff);

        ctx.getExternalContext().getSessionMap().put("user", staff);
        return "profile_staff.faces?faces-redirect=true";
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}