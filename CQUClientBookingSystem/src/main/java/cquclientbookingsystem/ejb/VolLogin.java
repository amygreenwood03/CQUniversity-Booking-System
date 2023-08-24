/*
 * VolLogin.java - VolLogin entity class
 * Stores details for Volunteer Logins
 */
package cquclientbookingsystem.ejb;

import java.io.Serializable;
import javax.persistence.*;

/**
 * * @author HeimannK
 */
@Entity
public class VolLogin implements Serializable {

    // Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long loginId;
    @OneToOne
    @JoinColumns({
        @JoinColumn(name="PERSON_ID"),
        @JoinColumn(name="EMAIL"),
        @JoinColumn(name="PASSWORD")
    })
    private Volunteer volunteer;
    
    // Constructors
    public VolLogin() {
        
    }

    public VolLogin(Volunteer volunteer) {
        this.volunteer = volunteer;
    }

    // Getters & Setters
    public Long getLoginId() {
        return loginId;
    }

    public void setLoginId(Long loginId) {
        this.loginId = loginId;
    }
    
    public Volunteer getVolunteer() {
        return volunteer;
    }
    
    public void setVolunteer(Volunteer volunteer) {
        this.volunteer = volunteer;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (loginId != null ? loginId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the loginId fields are not set
        if (!(object instanceof VolLogin)) {
            return false;
        }
        VolLogin other = (VolLogin) object;
        if ((this.loginId == null && other.loginId != null) || (this.loginId != null && !this.loginId.equals(other.loginId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cquclientbookingsystem.ejb.Login[ id=" + loginId + " ]";
    }
    
}
