/*
 * Staff.java - Staff entity class
 * Stores details relating to Staff users
 */
package cquclientbookingsystem.ejb;

import javax.persistence.Entity;

/**
 * * @author HeimannK@Entity
 */
@Entity
public class Staff extends Person {
    
    // Attributes
    private String department;
    
    // Constructors
    public Staff() {
        
    }
    
    public Staff(String firstName,  String lastName, int phone, String emailAddress, String password, String department) {
        super(firstName, lastName, phone, emailAddress, password);
        this.department = department;
    }
    
    // Getters & Setters
    public String getDepartment() {
        return department;
    }
    
    public void setDepartment(String department) {
        this.department = department;
    }

    
    @Override
    public String toString() {
        return "cquclientbookingsystem.ejb.NewEntity[ id=" + id + " ]";
    }
    
}
