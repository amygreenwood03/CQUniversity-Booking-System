/*
 * StaffLogin.java - StaffLogin entity class
 * Stores details for Staff Logins
 */
package cquclientbookingsystem.ejb;

import java.io.Serializable;
import javax.persistence.*;

/**
 * * @author HeimannK
 */
@Entity
public class StaffLogin implements Serializable {

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
    private Staff staff;
    
    // Constructors
    public StaffLogin() {
        
    }
    
    public StaffLogin(Staff staff) {
        this.staff = staff;
    }
    
    // Getters & Setters
    public Long getLoginId() {
        return loginId;
    }

    public void setLoginId(Long loginId) {
        this.loginId = loginId;
    }
    
    public Staff getStaff() {
        return staff;
    }
    
    public void setStaff(Staff staff) {
        this.staff = staff;
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
        if (!(object instanceof StaffLogin)) {
            return false;
        }
        StaffLogin other = (StaffLogin) object;
        if ((this.loginId == null && other.loginId != null) || (this.loginId != null && !this.loginId.equals(other.loginId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cquclientbookingsystem.ejb.StaffLogin[ id=" + loginId + " ]";
    }
    
}
