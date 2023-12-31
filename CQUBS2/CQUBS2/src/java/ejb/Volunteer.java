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
@NamedQuery(name="findAllVolunteers", query="SELECT v FROM Volunteer v")
@DiscriminatorValue("VOL")
@Table(name="VOLUNTEER")
public class Volunteer extends Users {

    // Attributes
    @OneToMany(mappedBy = "volunteer")
    private List<Registration> regList;
    
    // Constructors
    public Volunteer() {
        
    }
    
    public Volunteer(String firstName, String lastName, String phone, String email, String password, String salt) {
        super(firstName, lastName, phone, email, password, salt);
    }

   // Getters & Setters
    public List<Registration> getRegList() {
        return regList;
    }
    
    public void setRegList(List<Registration> regList) {
        this.regList = regList;
    }

    //returns representation of object as string
    @Override
    public String toString() {
        return "cquclientbookingsystem.ejb.Volunteer[ id=" + id + " ]";
    }
}
