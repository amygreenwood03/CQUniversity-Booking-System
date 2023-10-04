/*
 * CategoryEJB.java - Session Bean for Category entity class
 * Interface to allow server access
 */
package ejb;

import jakarta.annotation.Resource;
import jakarta.ejb.Remote;
import jakarta.ejb.SessionContext;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import java.util.List;

@Stateless
public class CategoryEJB{
    
    // Attributes
    @PersistenceContext(unitName = "CQUBSPU")
    private EntityManager em; //entity manager instance
    
    @Resource
    SessionContext ctx; //current session context of server

    //returns all categories in database
    public List<Category> findCategories() {
        TypedQuery<Category> query = em.createNamedQuery("findAllCategories", Category.class);
        return query.getResultList();
    }

    //returns category based on id
    public Category findCategoryById(Long id) {
        return em.find(Category.class, id);
    }
    
    //returns categories based on associated department
    public List<Category> findCategoriesByDepartment(Department department)
    {
        TypedQuery<Category> query = em.createNamedQuery("findCategoriesByDepartment", Category.class);
        query.setParameter("did", department.getDepartmentId());
        return query.getResultList();
    }

    //creates a new category
    public Category createCategory(Category category) {
        em.persist(category);
        System.out.println(ctx.getCallerPrincipal().getName());
        return category;
    }

    //deletes existing category
    public void deleteCategory(Category category) {
        category = em.find(Category.class, category.getCat_id());
        category = em.merge(category);
        em.remove(category);
    }

    //updates existing category
    public Category updateCategory(Category category) {
        Category updatedCategory = em.find(Category.class, category.getCat_id());
        updatedCategory.setCategoryName(category.getCategoryName());
        updatedCategory.setImageUrl(category.getImageUrl());
        return em.merge(updatedCategory);
    }
}
