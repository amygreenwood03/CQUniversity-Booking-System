/*
 * LocationEJB.java - Session Bean for Location entity class
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
//@Remote(LocationRemote.class)
public class LocationEJB{
    
    //Attributes 
    @PersistenceContext(unitName = "CQUBSPU")
    private EntityManager em;

    @Resource
    SessionContext ctx;
    
    // Public methods
    //@Override
    public List<Location> findLocations() {
        TypedQuery<Location> query = em.createNamedQuery("findAllLocations", Location.class);
        return query.getResultList();
    }

    //@Override
    public Location findLocationById(Long id) {
        return em.find(Location.class, id);
    }

    //@Override
    public Location createLocation(Location loc) {
        em.persist(loc);
        System.out.println(ctx.getCallerPrincipal().getName());
        return loc;
    }

    //@Override
    public void deleteLocation(Location loc) {
        em.remove(em.merge(loc));
    }

    //@Override
    public Location updateLocation(Location loc) {
        return em.merge(loc);
    }

    //@Override
    public Location findLocationByName(String name) {
        return em.find(Location.class, name);
    }

}
