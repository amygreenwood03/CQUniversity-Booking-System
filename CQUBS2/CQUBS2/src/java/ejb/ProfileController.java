package ejb;

import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * This controller manages profile.xhtml, the page where users can manage their user account profiles.
 */

@Named(value = "profileController")
@SessionScoped
public class ProfileController implements Serializable {
    @EJB
    private RegistrationEJB regEJB; //RegistrationEJB instance
    
    @EJB
    private UsersEJB userEJB; //UsersEJB instance
    
    private Volunteer volunteer; //stores current volunteer
    
    private List<RegEntry> entryList = new ArrayList<>(); //stores all RegEntry objects
    private List<Registration> removeRegList = new ArrayList<>(); //stores registrations to be removed
    
    private final String PROFILE_NAME = "Your Profile"; //profile page title
    private final String EDIT_NAME = "Edit Your Profile"; //edit profile page title
    
    private String firstName, lastName, email, phone; //form fields
    
    //default constructor
    public ProfileController() {
        
    }
  
    //initialises page upon load
    public void init(Volunteer user) {
        volunteer = userEJB.findVolById(user.getId());
        removeRegList.clear();
        entryList.clear();
        
        firstName = volunteer.getFirstName();
        lastName = volunteer.getLastName();
        email = volunteer.getEmail();
        phone = volunteer.getPhone();
        
        List<Registration> regList = getRegList(volunteer);
        
        if(regList != null && !regList.isEmpty())
        {
            for(int i = 0; i < regList.size(); i++)
                entryList.add(new RegEntry(regList.get(i), false));
        }
    }
    
    //returns whether form fields empty or invalid
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
    
    //edits existing volunteer
    public String edit(Volunteer user) {
        FacesContext ctx = FacesContext.getCurrentInstance();
        FacesMessage editError = new FacesMessage("", "One or more fields are empty or filled incorrectly. Please check and try again.");
        
        if(checkFields()) {
            ctx.addMessage("editForm", editError);
            return null;
        }
        
        Staff sTest;
        Volunteer vTest;
        
        try {
            sTest = userEJB.findStaffByEmail(email);
        } 
        catch (Exception e) {
            sTest = null;
        }
        
        try {
            vTest = userEJB.findVolByEmail(email);
            
            if(user.getId() == vTest.getId())
                vTest = null;
        } 
        catch (Exception e) {
            vTest = null;
        }
        
        if(sTest != null || vTest != null) {
            ctx.addMessage("editForm", new FacesMessage("", "That email is already in use by another user."));
            return null;
        }
        
        for(int i = 0; i < entryList.size(); i++) {
            if(entryList.get(i).isSelected)
                removeRegList.add(entryList.get(i).getReg());
        }
        
        if(!removeRegList.isEmpty()) {
            for(int i = 0; i < removeRegList.size(); i++)
                regEJB.deleteRegistration(removeRegList.get(i));
        }
        
        volunteer.setFirstName(firstName);
        volunteer.setLastName(lastName);
        volunteer.setEmail(email);
        volunteer.setPhone(phone);
        
        userEJB.updateVolunteer(volunteer);

        ctx.getExternalContext().getSessionMap().put("user", volunteer);
        
        entryList.clear();
        return "profile.faces?faces-redirect=true";
    }
    
    //returns all registrations for a volunteer
    public List<Registration> getRegList(Volunteer user) {
        List<Registration> regList = regEJB.findRegistrationsByVolunteer(user);
        return regList;
    }

    //PROFILE_NAME accessor
    public String getPROFILE_NAME() {
        return PROFILE_NAME;
    }
    
    //EDIT_NAME accessor
    public String getEDIT_NAME() {
        return EDIT_NAME;
    }

    //volunteer accessor
    public Volunteer getVolunteer() {
        return volunteer;
    }

    //volunteer mutator
    public void setVolunteer(Volunteer volunteer) {
        this.volunteer = volunteer;
    }

    //removeRegList accessor
    public List<Registration> getRemoveRegList() {
        return removeRegList;
    }

    //removeRegList mutator
    public void setRemoveRegList(List<Registration> removeRegList) {
        this.removeRegList = removeRegList;
    }

    //entryList accessor
    public List<RegEntry> getEntryList() {
        return entryList;
    }

    //entryList mutator
    public void setEntryList(List<RegEntry> entryList) {
        this.entryList = entryList;
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

    //inner class to represent registrations in edit page table
    public class RegEntry {
        private Registration reg; //current registration
        private boolean isSelected; //whether selected by user
        
        //default constructor
        public RegEntry() {
            
        }
        
        //parameterised constructor
        public RegEntry(Registration reg, boolean isSelected) {
            this.reg = reg;
            this.isSelected = isSelected;
        }

        //reg accessor
        public Registration getReg() {
            return reg;
        }

        //reg mutator
        public void setReg(Registration reg) {
            this.reg = reg;
        }

        //isSelected accessor
        public boolean getIsSelected() {
            return isSelected;
        }

        //isSelected mutator
        public void setIsSelected(boolean isSelected) {
            this.isSelected = isSelected;
        }
    }
}