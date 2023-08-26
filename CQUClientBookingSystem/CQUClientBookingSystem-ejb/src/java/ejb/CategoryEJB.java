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
import java.util.List;

/**
 * * @author HeimannK
 */
@Stateless
@Remote(CategoryRemote.class)
public class CategoryEJB implements CategoryLocal, CategoryRemote {
    
    // Attributes
    @PersistenceContext(unitName = "CQUClientBookingSystem-ejbPU")
    private EntityManager em;
    
    @Resource
    SessionContext ctx;

    // Public methods
    @Override
    public List<Category> findCategories() {
        Query query = em.createNamedQuery("Category.findAllCategories");
        return query.getResultList();
    }

    @Override
    public Category findCategoryById(Long id) {
        return em.find(Category.class, id);
    }

    @Override
    public Category createCategory(Category category) {
        em.persist(category);
        System.out.println(ctx.getCallerPrincipal().getName());
        return category;
    }

    @Override
    public void deleteCategory(Category category) {
        em.remove(em.merge(category));
    }

    @Override
    public Category updateCategory(Category category) {
        return em.merge(category);
    }
}
