/*
 * CategoryRemote.java - Category Remote Interface class
 * Interface to allow server access
 */
package ejb;

import jakarta.ejb.Remote;
import java.util.List;

/**
 * * @author HeimannK
 */
@Remote
public interface CategoryRemote {
    
    // Public methods
    List<Category> findCategories();
    
    Category findCategoryById(Long id);
    
    Category createCategory(Category category);
    
    void deleteCategory(Category category);
    
    Category updateCategory(Category category);
}
