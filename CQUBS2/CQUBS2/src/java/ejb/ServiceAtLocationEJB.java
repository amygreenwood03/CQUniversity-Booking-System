/*
 * ServiceAtLocationEJB.java - Session Bean for Service At Location classes
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
public class ServiceAtLocationEJB{

    // Attributes
    @PersistenceContext(unitName = "CQUBSPU")
    private EntityManager em; //entity manager instance
    
    @Resource
    SessionContext ctx; //current session context of server

    //returns all sals
    public List<ServiceAtLocation> findSALs() {
        TypedQuery<ServiceAtLocation> query = em.createNamedQuery("findAllSALs", ServiceAtLocation.class);
        return query.getResultList();
    }

    //returns sal based on id
    public ServiceAtLocation findSALById(Long id) {
        return em.find(ServiceAtLocation.class, id);
    }
    
    //returns sals based on location
    public List<ServiceAtLocation> findSALsByLocation(Location location) {
        TypedQuery<ServiceAtLocation> query = em.createNamedQuery("findSALsByLocation", ServiceAtLocation.class);
        query.setParameter("lid", location.getLocationId());
        return query.getResultList();
    }
    
    //returns sals based on category
    public List<ServiceAtLocation> findSALsByCategory(Category category) {
        TypedQuery<ServiceAtLocation> query = em.createNamedQuery("findSALsByCategory", ServiceAtLocation.class);
        query.setParameter("cid", category.getCat_id());
        return query.getResultList();
    }
    
    //returns sals based on service
    public List<ServiceAtLocation> findSALsByService(Service service) {
        TypedQuery<ServiceAtLocation> query = em.createNamedQuery("findSALsByService", ServiceAtLocation.class);
        query.setParameter("sid", service.getServiceId());
        return query.getResultList();
    }
    
    //return sals by department
    public List<ServiceAtLocation> findSALsByDepartment(Department department) {
        TypedQuery<ServiceAtLocation> query = em.createNamedQuery("findSALsByDepartment", ServiceAtLocation.class);
        query.setParameter("did", department.getDepartmentId());
        return query.getResultList();
    }
    
    //creates new sal
    public ServiceAtLocation createSAL(ServiceAtLocation sal) {
        em.persist(sal);
        System.out.println(ctx.getCallerPrincipal().getName());
        return sal;
    }

    //deletes existing sal
    public void deleteSAL(ServiceAtLocation sal) {
        sal = em.find(ServiceAtLocation.class, sal.getSalId());
        sal = em.merge(sal);
        em.remove(sal);
    }

    //edits existing sal
    public ServiceAtLocation updateSAL(ServiceAtLocation sal) {
        return em.merge(sal);
    }
}
