/*
 * Staff.java - Staff entity class
 * Stores details relating to Staff users
 */
package cquclientbookingsystem.ejb;

import javax.persistence.*;

/**
 * * @author HeimannK@Entity
 */
@Entity
public class Staff extends Person {
    
    // Attributes
    @ManyToOne
    @JoinColumn(name="DEPT_ID")
    private Department department;
    
    // Constructors
    public Staff() {
        
    }
    
    public Staff(String fullName, String phone, String emailAddress, String password, Department department) {
        super(fullName, phone, emailAddress, password);
        this.department = department;
    }
    
    // Getters & Setters
    public Department getDepartment() {
        return department;
    }
    
    public void setDepartment(Department department) {
        this.department = department;
    }

    
    @Override
    public String toString() {
        return "cquclientbookingsystem.ejb.NewEntity[ id=" + id + " ]";
    }
    
}
