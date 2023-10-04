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
public class LocationEJB{
    
    //Attributes 
    @PersistenceContext(unitName = "CQUBSPU")
    private EntityManager em;

    @Resource
    SessionContext ctx;
    
    //returns all locations 
    public List<Location> findLocations() {
        TypedQuery<Location> query = em.createNamedQuery("findAllLocations", Location.class);
        return query.getResultList();
    }

    //returns location based on id
    public Location findLocationById(Long id) {
        return em.find(Location.class, id);
    }

    //creates new location
    public Location createLocation(Location loc) {
        em.persist(loc);
        System.out.println(ctx.getCallerPrincipal().getName());
        return loc;
    }

    //deletes existing location
    public void deleteLocation(Location loc) {
        em.remove(em.merge(loc));
    }

    //edits existing location
    public Location updateLocation(Location loc) {
        return em.merge(loc);
    }
}
