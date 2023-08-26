/*
 * Department.java - Department entity class
 * Stores details of Departments with Categories that offers Services
 */
package ejb;

import java.io.Serializable;
import java.util.ArrayList;
import jakarta.persistence.*;

/**
 * * @author HeimannK
 */
@Entity
public class Department implements Serializable {

    // Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="DEPT_ID")
    private Long departmentId;
    @Column(name="DEPT_NAME")
    private String departmentName;
    @OneToMany(mappedBy = "dept")
    private ArrayList<Category> categories;
    @OneToMany(mappedBy = "department")
    private ArrayList<Staff> staffList;
    
    // Constructors
    public Department() {
        
    }
    
    public Department(String departmentName) {
        this.departmentName = departmentName;
    }
    
    public Department(String departmentName, ArrayList<Category> categories, ArrayList<Staff> staffList) {
        this.departmentName = departmentName;
        this.categories = categories;
        this.staffList = staffList;
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
    
    public ArrayList<Category> getCategories() {
        return categories;
    }
    
    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }
    
    public ArrayList<Staff> getStaffList() {
        return staffList;
    }
    
    public void setStaffList(ArrayList<Staff> staffList) {
        this.staffList = staffList;
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