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
import java.util.List;

/**
 * * @author HeimannK
 */
@Stateless
@Remote(ServiceRemote.class)
public class ServiceEJB implements ServiceLocal, ServiceRemote {

    //Attributes 
    @PersistenceContext(unitName = "CQUBSPU")
    private EntityManager em;

    @Resource
    SessionContext ctx;
    
    // Public methods
    @Override
    public List<Service> findServices() {
        Query query = em.createNamedQuery("Service.findAllServices");
        return query.getResultList();
    }

    @Override
    public Service findServiceById(Long id) {
        return em.find(Service.class, id);
    }

    @Override
    public Service createService(Service service) {
        em.persist(service);
        System.out.println(ctx.getCallerPrincipal().getName());
        return service;
    }

    @Override
    public void deleteService(Service service) {
        em.remove(em.merge(service));
    }

    @Override
    public Service updateService(Service service) {
        return em.merge(service);
    }

    @Override
    public Service findServiceByName(String name) {
        return em.find(Service.class, name);
    }
}
