/*
 * Department.java - Department entity class
 * Stores details of Departments with Categories that offers Services
 */
package cquclientbookingsystem.ejb;

import java.io.Serializable;
import javax.persistence.*;

/**
 * * @author HeimannK
 */
@Entity
public class Department implements Serializable {

    // Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long departmentId;
    private String departmentName;
    
    // Constructors
    public Department() {
        
    }
    
    public Department(String departmentName) {
        this.departmentName = departmentName;
    }
    
    // Getters & Setters
    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }
    
    public String getDepartmentName() {
        return departmentName;
    }
    
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (departmentId != null ? departmentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Department)) {
            return false;
        }
        Department other = (Department) object;
        if ((this.departmentId == null && other.departmentId != null) || (this.departmentId != null && !this.departmentId.equals(other.departmentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cquclientbookingsystem.ejb.Department[ id=" + departmentId + " ]";
    }
    
}
