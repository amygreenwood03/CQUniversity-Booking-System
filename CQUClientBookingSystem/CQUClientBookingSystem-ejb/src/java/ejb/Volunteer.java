/*
 * Volunteer.java - Volunteer entity class
 * Stores details relating to Volunteer users
 */
package ejb;

import java.util.ArrayList;
import jakarta.persistence.*;

/**
 * * @author HeimannK
 */
@Entity
@DiscriminatorValue("VOL")
@Table(name="VOLUNTEER")
public class Volunteer extends Users {

    // Attributes
    @OneToMany(mappedBy = "volunteer")
    private ArrayList<Registration> regList;
    
    // Constructors
    public Volunteer() {
        
    }
    
    public Volunteer(String fullName, String phone, String emailAddress, ArrayList<Registration> regList) {
        super(fullName, phone, emailAddress);
        this.regList = regList;
    }

   // Getters & Setters
    public ArrayList<Registration> getRegList() {
        return regList;
    }
    
    public void setRegList(ArrayList<Registration> regList) {
        this.regList = regList;
    }

    @Override
    public String toString() {
        return "cquclientbookingsystem.ejb.Volunteer[ id=" + id + " ]";
    }
    
}
