/*
 * CategoryLocal.java - Category Local Interface class
 * Interface to allow server access
 */
package ejb;

import jakarta.ejb.Local;
import java.util.List;

/**
 * * @author HeimannK
 */
@Local
public interface CategoryLocal {
    
    // Public methods
    List<Category> findCategories();
    
    Category findCategoryById(Long id);
    
    Category findCategoryByName(String catName);
    
    Category createCategory(Category category);
    
    void deleteCategory(Category category);
    
    Category updateCategory(Category category);    
}
