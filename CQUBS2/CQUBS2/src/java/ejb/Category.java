/*
 * Category.java - Category entity class
 * Stores details of Categories offered
 */
package ejb;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.List;

/**
 * * @author HeimannK
 */
@Entity
@NamedQuery(
    name="findAllCategories",
        query="SELECT c FROM Category c")
public class Category implements Serializable {

    // Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="CAT_ID")
    private Long cat_id;
    @Column(name="CAT_NAME")
    private String categoryName;
    @Column(name="IMAGE_URL")
    private String imageUrl;
    @ManyToOne
    @JoinColumn(name="DEPT_ID")
    private Department dept;
    @OneToMany(mappedBy = "category")
    private List<Service> services;
    
    // Constructors
    public Category() {
        
    }
    public Category(String categoryName, Department department) {
        this.categoryName = categoryName;
        this.dept = department;
    }
    
    public Category(String categoryName, Department dept, List<Service> services, String imageUrl) {
        this.categoryName = categoryName;
        this.dept = dept;
        this.services = services;
        this.imageUrl = imageUrl;
    }
    
    // Getters & Setters

    public Long getCat_id() 
    {
        return cat_id;
    }

    public void setCat_id(Long cat_id) 
    {
        this.cat_id = cat_id;
    }

    public Department getDept() 
    {
        return dept;
    }

    public void setDept(Department dept) 
    {
        this.dept = dept;
    }
    
    
    public String getCategoryName() 
    {
        return categoryName;
    }
    
    public void setCategoryName(String categoryName) 
    {
        this.categoryName = categoryName;
    }

    public String getImageUrl() 
    {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) 
    {
        this.imageUrl = imageUrl;
    }
    
    public List<Service> getServices() 
    {
        return services;
    }
    
    public void setServices(List<Service> services) 
    {
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
