/*
 * Category.java - Category entity class
 * Stores details of Categories offered
 */
package cquclientbookingsystem.ejb;

import java.io.Serializable;
import javax.persistence.*;

/**
 * * @author HeimannK
 */
@Entity
public class Category implements Serializable {

    // Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long categoryId;
    private String categoryName;
    
    // Constructors
    public Category() {
        
    }
    
    public Category(String categoryName) {
        this.categoryName = categoryName;
    }
    
    // Getters & Setters
    public Long getId() {
        return categoryId;
    }

    public void setId(Long id) {
        this.categoryId = id;
    }
    
    public String getCategoryName() {
        return categoryName;
    }
    
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (categoryId != null ? categoryId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the categoryId fields are not set
        if (!(object instanceof Category)) {
            return false;
        }
        Category other = (Category) object;
        if ((this.categoryId == null && other.categoryId != null) || (this.categoryId != null && !this.categoryId.equals(other.categoryId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cquclientbookingsystem.ejb.Category[ id=" + categoryId + " ]";
    }
    
}
