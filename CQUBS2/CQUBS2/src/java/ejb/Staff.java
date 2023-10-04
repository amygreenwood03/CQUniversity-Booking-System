/*
 * Staff.java - Staff entity class
 * Stores details relating to Staff users
 */
package ejb;

import jakarta.persistence.*;

@Entity
@NamedQuery(name="findAllStaff", query="SELECT s FROM Staff s")
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
    
    public Staff(String firstName, String lastName, String phone, String email, String password, String salt, Department department) {
        super(firstName, lastName, phone, email, password, salt);
        this.department = department;
    }
    
    // Getters & Setters
    public Department getDepartment() {
        return department;
    }
    
    public void setDepartment(Department department) {
        this.department = department;
    }
    
    //returns representation of object as string
    @Override
    public String toString() {
        return "cquclientbookingsystem.ejb.NewEntity[ id=" + id + " ]";
    }
}