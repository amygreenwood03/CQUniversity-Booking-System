/*
 * Category.java - Category entity class
 * Stores details of Categories offered
 */
package ejb;

import java.io.Serializable;
import java.util.ArrayList;
import jakarta.persistence.*;

/**
 * * @author HeimannK
 */
@Entity
public class Category implements Serializable {

    // Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="CAT_ID")
    private Long cat_id;
    @Column(name="CAT_NAME")
    private String categoryName;
    @ManyToOne
    @JoinColumn(name="DEPT_ID")
    private Department dept;
    @OneToMany(mappedBy = "category")
    private ArrayList<Service> services;
    
    // Constructors
    public Category() {
        
    }
    public Category(String categoryName, Department department) {
        this.categoryName = categoryName;
        this.dept = department;
    }
    
    public Category(String categoryName, Department dept, ArrayList<Service> services) {
        this.categoryName = categoryName;
        this.dept = dept;
        this.services = services;
    }
    
    // Getters & Setters
    public Long getId() {
        return cat_id;
    }

    public void setId(Long id) {
        this.cat_id = id;
    }
    
    public String getCategoryName() {
        return categoryName;
    }
    
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    
    public Department getDepartment() {
        return dept;
    }
    
    public void setDepartment(Department department) {
        this.dept = department;
    }
    
    public ArrayList<Service> getServices() {
        return services;
    }
    
    public void setServices(ArrayList<Service> services) {
        this.services = services;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cat_id != null ? cat_id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the cat_id fields are not set
        if (!(object instanceof Category)) {
            return false;
        }
        Category other = (Category) object;
        if ((this.cat_id == null && other.cat_id != null) || (this.cat_id != null && !this.cat_id.equals(other.cat_id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cquclientbookingsystem.ejb.Category[ id=" + cat_id + " ]";
    }
    
}
