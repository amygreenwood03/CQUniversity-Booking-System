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
    private RegistrationEJB regEJB; //RegistrationEJB instance
    
    @EJB
    private UsersEJB userEJB; //UsersEJB instance
    
    private Staff staff; //stores current staff object
    private final String PROFILE_NAME = "Your Profile"; //profile page title
    private final String EDIT_NAME = "Edit Your Profile"; //edit page title
    
    private String firstName, lastName, email, phone; //form fields
    
    //default constructor
    public ProfileStaffController() {
        
    }
  
    //initialises page upon load
    public void init(Staff user) {
        staff = userEJB.findStaffById(user.getId());
        
        firstName = staff.getFirstName();
        lastName = staff.getLastName();
        email = staff.getEmail();
        phone = staff.getPhone();
    }
    
    //returns whether form fields are empty or invalid
    public boolean checkFields() {
        if(firstName.isBlank() || lastName.isBlank() || email.isBlank() || phone.isBlank())
            return true;
        else if(!phone.matches("^[0-9]{10}$") || !email.matches("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\."
            + "[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@"
            + "(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9]"
            + "(?:[a-z0-9-]*[a-z0-9])?"))
            return true;
        
        return false;
    }
    
    //edits existing staff account
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
        
        if(sTest != null || vTest != null) {
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

    //PROFILE_NAME accessor
    public String getPROFILE_NAME() {
        return PROFILE_NAME;
    }
    
    //EDIT_NAME accessor
    public String getEDIT_NAME() {
        return EDIT_NAME;
    }

    //staff accessor
    public Staff getStaff() {
        return staff;
    }

    //staff mutator
    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    //firstName accessor
    public String getFirstName() {
        return firstName;
    }

    //firstName mutator
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    //lastName accessor
    public String getLastName() {
        return lastName;
    }

    //lastName mutator
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    //email accessor
    public String getEmail() {
        return email;
    }

    //email mutator
    public void setEmail(String email) {
        this.email = email;
    }

    //phone accessor
    public String getPhone() {
        return phone;
    }

    //phone mutator
    public void setPhone(String phone) {
        this.phone = phone;
    }
}