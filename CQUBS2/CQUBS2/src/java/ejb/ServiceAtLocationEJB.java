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
import jakarta.persistence.TypedQuery;
import java.util.List;

/**
 * * @author HeimannK
 */
@Stateless
//@Remote(ServiceAtLocationRemote.class)
public class ServiceAtLocationEJB{

    // Attributes
    @PersistenceContext(unitName = "CQUBSPU")
    private EntityManager em;
    
    @Resource
    SessionContext ctx;

    // Public methods
    //@Override
    public List<ServiceAtLocation> findSALs() {
        TypedQuery<ServiceAtLocation> query = em.createNamedQuery("findAllSALs", ServiceAtLocation.class);
        return query.getResultList();
    }

    //@Override
    public ServiceAtLocation findSALById(Long id) {
        return em.find(ServiceAtLocation.class, id);
    }
    
    public List<ServiceAtLocation> findSALsByLocation(Location location)
    {
        TypedQuery<ServiceAtLocation> query = em.createNamedQuery("findSALsByLocation", ServiceAtLocation.class);
        query.setParameter("lid", location.getLocationId());
        return query.getResultList();
    }
    
    public List<ServiceAtLocation> findSALsByCategory(Category category)
    {
        TypedQuery<ServiceAtLocation> query = em.createNamedQuery("findSALsByCategory", ServiceAtLocation.class);
        query.setParameter("cid", category.getCat_id());
        return query.getResultList();
    }
    
    public List<ServiceAtLocation> findSALsByService(Service service)
    {
        TypedQuery<ServiceAtLocation> query = em.createNamedQuery("findSALsByService", ServiceAtLocation.class);
        query.setParameter("sid", service.getServiceId());
        return query.getResultList();
    }
    
    public List<ServiceAtLocation> findSALsByDepartment(Department department)
    {
        TypedQuery<ServiceAtLocation> query = em.createNamedQuery("findSALsByDepartment", ServiceAtLocation.class);
        query.setParameter("did", department.getDepartmentId());
        return query.getResultList();
    }
    
    //@Override
    public ServiceAtLocation createSAL(ServiceAtLocation sal) {
        em.persist(sal);
        System.out.println(ctx.getCallerPrincipal().getName());
        return sal;
    }

    //@Override
    public void deleteSAL(ServiceAtLocation sal) {
        sal = em.find(ServiceAtLocation.class, sal.getSalId());
        sal = em.merge(sal);
        em.remove(sal);
        /*Service service = em.find(Service.class, sal.getService().getServiceId());
        
        if(service.getSalList().size() == 1)
        {
            if(service.getSalList().get(0).getService().getServiceId() == sal.getSalId())
            {
                em.remove(em.merge(sal));
                em.remove(em.merge(service));
            }
        }
        else
            em.remove(em.merge(sal));*/
    }

    //@Override
    public ServiceAtLocation updateSAL(ServiceAtLocation sal) {
        return em.merge(sal);
    }
}
