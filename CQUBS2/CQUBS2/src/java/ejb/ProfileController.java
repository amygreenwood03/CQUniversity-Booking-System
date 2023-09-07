package ejb;

import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.ejb.EJB;
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
    
    public ProfileController() {
        
    }
  
    public void init(Volunteer user) {
        volunteer = userEJB.findVolById(user.getId());
        removeRegList.clear();
        entryList.clear();
        
        List<Registration> regList = getRegList(volunteer);
        
        if(regList != null && !regList.isEmpty())
        {
            for(int i = 0; i < regList.size(); i++)
                entryList.add(new RegEntry(regList.get(i), false));
        }
    }
    
    public String edit() {
        for(int i = 0; i < entryList.size(); i++) {
            if(entryList.get(i).isSelected)
                removeRegList.add(entryList.get(i).getReg());
        }
        
        if(!removeRegList.isEmpty()) {
            for(int i = 0; i < removeRegList.size(); i++)
                regEJB.deleteRegistration(removeRegList.get(i));
        }
        
        userEJB.updateVolunteer(volunteer);
        
        FacesContext ctx = FacesContext.getCurrentInstance();
        ctx.getExternalContext().getSessionMap().put("user", volunteer);
        
        entryList.clear();
        return "profile.faces";
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