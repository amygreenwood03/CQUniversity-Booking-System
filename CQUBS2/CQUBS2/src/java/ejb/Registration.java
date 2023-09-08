/*
 * Registration.java - Registration entity class
 * Stores details of Volunteer Registrations for ServiceAtLocations
 */
package ejb;

import java.io.Serializable;
import jakarta.persistence.*;

@Entity
@NamedQuery(
    name="findAllRegistrations",
        query="SELECT r FROM Registration r")
@NamedQuery(name = "findRegistrationsByVolunteer", query = "SELECT r FROM Registration r WHERE r.volunteer.id = :vid")
@NamedQuery(name = "findRegistrationsBySAL", query = "SELECT r FROM Registration r WHERE r.sal.salId = :sid")
public class Registration implements Serializable {
    // Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="REG_ID")
    private Long regId;
    @ManyToOne
    @JoinColumn(name="SAL_ID")
    private ServiceAtLocation sal;
    @ManyToOne
    @JoinColumn(name="USER_ID")
    private Volunteer volunteer;
    
    // Constructors
    public Registration() {
        
    }
    
    public Registration(ServiceAtLocation sal, Volunteer volunteer) {
        this.sal = sal;
        this.volunteer = volunteer;
    }
    
    // Getters & Setters
    public Long getRegId() {
        return regId;
    }

    public void setRegId(Long regId) {
        this.regId = regId;
    }
    
    public ServiceAtLocation getSAL() {
        return sal;
    }
    
    public void setSAL(ServiceAtLocation sal) {
        this.sal = sal;
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
        hash += (regId != null ? regId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the regId fields are not set
        if (!(object instanceof Registration)) {
            return false;
        }
        Registration other = (Registration) object;
        if ((this.regId == null && other.regId != null) || (this.regId != null && !this.regId.equals(other.regId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cquclientbookingsystem.ejb.Registration[ id=" + regId + " ]";
    }
}
