/*
 * ServiceAtLocationEJB.java - Session Bean for Service At Location classes
 * Bean to implement ServiceAtLocationRemote interface
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
@Remote(ServiceAtLocationRemote.class)
public class ServiceAtLocationEJB implements ServiceAtLocationLocal, ServiceAtLocationRemote {

    // Attributes
    @PersistenceContext(unitName = "CQUClientBookingSystem-ejbPU")
    private EntityManager em;
    
    @Resource
    SessionContext ctx;

    // Public methods
    @Override
    public List<ServiceAtLocation> findSALs() {
        Query query = em.createNamedQuery("ServiceAtLocation.findAllSALs");
        return query.getResultList();
    }

    @Override
    public ServiceAtLocation findSALById(Long id) {
        return em.find(ServiceAtLocation.class, id);
    }

    @Override
    public ServiceAtLocation createSAL(ServiceAtLocation sal) {
        em.persist(sal);
        System.out.println(ctx.getCallerPrincipal().getName());
        return sal;
    }

    @Override
    public void deleteSAL(ServiceAtLocation sal) {
        em.remove(em.merge(sal));
    }

    @Override
    public ServiceAtLocation updateSAL(ServiceAtLocation sal) {
        return em.merge(sal);
    }
}
