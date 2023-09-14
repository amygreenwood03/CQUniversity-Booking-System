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
    private RegistrationEJB regEJB;
    
    @EJB
    private UsersEJB userEJB;
    
    private Volunteer volunteer;
    
    private List<RegEntry> entryList = new ArrayList<>();
    private List<Registration> removeRegList = new ArrayList<>();
    
    private final String PROFILE_NAME = "Your Profile";
    private final String EDIT_NAME = "Edit Your Profile";
    
    private String firstName, lastName, email, phone;
    
    public ProfileController() {
        
    }
  
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
    
    public String edit(Volunteer user) {
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
        
        if(sTest != null || vTest != null)
        {
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

    public Volunteer getVolunteer() {
        return volunteer;
    }

    public void setVolunteer(Volunteer volunteer) {
        this.volunteer = volunteer;
    }

    public List<Registration> getRemoveRegList() {
        return removeRegList;
    }

    public void setRemoveRegList(List<Registration> removeRegList) {
        this.removeRegList = removeRegList;
    }

    public List<RegEntry> getEntryList() {
        return entryList;
    }

    public void setEntryList(List<RegEntry> entryList) {
        this.entryList = entryList;
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

    public class RegEntry {
        private Registration reg;
        private boolean isSelected;
        
        public RegEntry() {
            
        }
        
        public RegEntry(Registration reg, boolean isSelected) {
            this.reg = reg;
            this.isSelected = isSelected;
        }

        public Registration getReg() {
            return reg;
        }

        public void setReg(Registration reg) {
            this.reg = reg;
        }

        public boolean getIsSelected() {
            return isSelected;
        }

        public void setIsSelected(boolean isSelected) {
            this.isSelected = isSelected;
        }
    }
}