/*
 * Staff.java - Staff entity class
 * Stores details relating to Staff users
 */
package ejb;

import jakarta.persistence.*;

/**
 * * @author HeimannK@Entity
 */
@Entity
@NamedQuery(
    name="findAllStaff",
        query="SELECT * FROM Staff")
@DiscriminatorValue("STAFF")
@Table(name="STAFF")
public class Staff extends Users {
    
    // Attributes
    @ManyToOne
    @JoinColumn(name="DEPT_ID")
    private Department department;
    
    // Constructors
    public Staff() {
        
    }
    
    public Staff(String fullName, String phone, String emailAddress, Department department) {
        super(fullName, phone, emailAddress);
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
