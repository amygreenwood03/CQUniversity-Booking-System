/*
 * Volunteer.java - Volunteer entity class
 * Stores details relating to Volunteer users
 */
package cquclientbookingsystem.ejb;

import java.util.ArrayList;
import javax.persistence.*;

/**
 * * @author HeimannK
 */
@Entity
public class Volunteer extends Person {

    // Attributes
    @OneToMany(mappedBy = "volunteer")
    private ArrayList<Registration> regList;
    
    // Constructors
    public Volunteer() {
        
    }
    
    public Volunteer(String fullName, String phone, String emailAddress, String password, ArrayList<Registration> regList) {
        super(fullName, phone, emailAddress, password);
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
