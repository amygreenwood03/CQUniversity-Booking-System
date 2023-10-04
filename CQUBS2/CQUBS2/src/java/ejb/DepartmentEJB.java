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
public class DepartmentEJB{
    
    // Attributes
    @PersistenceContext(unitName = "CQUBSPU")
    private EntityManager em; //entity manager instance
    
    @Resource
    SessionContext ctx; //current session context of server
    
    //returns all departments
    public List<Department> findDepartments() {
        TypedQuery<Department> query = em.createNamedQuery("findAllDepartments", Department.class);
        return query.getResultList();
    }

    //returns department based on id
    public Department findDeptById(Long id) {
        return em.find(Department.class, id);
    }

    //creates new department
    public Department createDept(Department dept) {
        em.persist(dept);
        System.out.println(ctx.getCallerPrincipal().getName());
        return dept;
    }

    //deletes existing department
    public void deleteDept(Department dept) {
        em.remove(em.merge(dept));
    }

    //updates existing department
    public Department updateDept(Department dept) {
        return em.merge(dept);
    }
}
