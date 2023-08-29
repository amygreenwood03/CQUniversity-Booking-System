/*
 * ServiceEJB.java - Session Bean for Service entity class
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
//@Remote(ServiceRemote.class)
public class ServiceEJB{

    //Attributes 
    @PersistenceContext(unitName = "CQUBSPU")
    private EntityManager em;

    @Resource
    SessionContext ctx;
    
    // Public methods
    //@Override
    public List<Service> findServices() {
        TypedQuery<Service> query = em.createNamedQuery("findAllServices", Service.class);
        return query.getResultList();
    }

    //@Override
    public Service findServiceById(Long id) {
        return em.find(Service.class, id);
    }
    
    public List<Service> findServicesByCategory(Category category)
    {
        TypedQuery<Service> query = em.createNamedQuery("findServicesByCategory", Service.class);
        query.setParameter("cid", category.getCat_id());
        return query.getResultList();
    }

    //@Override
    public Service createService(Service service) {
        em.persist(service);
        System.out.println(ctx.getCallerPrincipal().getName());
        return service;
    }

    //@Override
    public void deleteService(Service service) {
        em.remove(em.merge(service));
    }

    //@Override
    public Service updateService(Service service) {
        return em.merge(service);
    }

    //@Override
    public Service findServiceByName(String name) {
        return em.find(Service.class, name);
    }
}
