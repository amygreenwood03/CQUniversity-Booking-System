/*
 * Volunteer.java - Volunteer entity class
 * Stores details relating to Volunteer users
 */
package ejb;

import java.util.List;
import jakarta.persistence.*;

/**
 * * @author HeimannK
 */
@Entity
@NamedQuery(
    name="findAllVolunteers",
        query="SELECT * FROM Volunteers")
@DiscriminatorValue("VOL")
@Table(name="VOLUNTEER")
public class Volunteer extends Users {

    // Attributes
    @OneToMany(mappedBy = "volunteer")
    private List<Registration> regList;
    
    // Constructors
    public Volunteer() {
        
    }
    
    public Volunteer(String fullName, String phone, String emailAddress, List<Registration> regList) {
        super(fullName, phone, emailAddress);
        this.regList = regList;
    }

   // Getters & Setters
    public List<Registration> getRegList() {
        return regList;
    }
    
    public void setRegList(List<Registration> regList) {
        this.regList = regList;
    }

    @Override
    public String toString() {
        return "cquclientbookingsystem.ejb.Volunteer[ id=" + id + " ]";
    }
    
}
