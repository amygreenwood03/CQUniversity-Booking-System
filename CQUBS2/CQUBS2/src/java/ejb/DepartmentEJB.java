/*
 * DepartmentEJB.java - Session Bean for Department entity class
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

/**
 * * @author HeimannK
 */
@Stateless
//@Remote(DepartmentRemote.class)
public class DepartmentEJB{
    
    // Attributes
    @PersistenceContext(unitName = "CQUBSPU")
    private EntityManager em;
    
    @Resource
    SessionContext ctx;
    
    // Public methods
    //@Override
    public List<Department> findDepartments() {
        TypedQuery<Department> query = em.createNamedQuery("findAllDepartments", Department.class);
        return query.getResultList();
    }

    //@Override
    public Department findDeptById(Long id) {
        return em.find(Department.class, id);
    }

    //@Override
    public Department createDept(Department dept) {
        em.persist(dept);
        System.out.println(ctx.getCallerPrincipal().getName());
        return dept;
    }

    //@Override
    public void deleteDept(Department dept) {
        em.remove(em.merge(dept));
    }

    //@Override
    public Department updateDept(Department dept) {
        return em.merge(dept);
    }

    //@Override
    public Department findDeptByName(String deptName) {
        return em.find(Department.class, deptName);
    }
}
