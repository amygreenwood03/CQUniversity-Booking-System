/*
 * RegistrationEJB.java - Session Bean for Registration classes
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
public class RegistrationEJB {

    // Attributes
    @PersistenceContext(unitName = "CQUBSPU")
    private EntityManager em; //entity manager instance
    
    @Resource
    SessionContext ctx; //current session context of server

    //returns all registrations
    public List<Registration> findRegistrations() {
        TypedQuery<Registration> query = em.createNamedQuery("findAllRegistrations", Registration.class);
        return query.getResultList();
    }

    //returns registration based on id
    public Registration findRegById(Long id) {
        return em.find(Registration.class, id);
    }

    //creates new registration
    public Registration createRegistration(Registration reg, Volunteer vol) {
        vol = em.find(Volunteer.class, vol.getId());
        
        reg.setVolunteer(vol);
        em.persist(reg);
        System.out.println(ctx.getCallerPrincipal().getName());
        return reg;
    }
    
    //returns registrations based on volunteer
    public List<Registration> findRegistrationsByVolunteer(Volunteer vol) {
        TypedQuery<Registration> query = em.createNamedQuery("findRegistrationsByVolunteer", Registration.class);
        query.setParameter("vid", vol.getId());
        return query.getResultList();
    }
    
    //returns registrations based on sal
    public List<Registration> findRegistrationsBySAL(ServiceAtLocation sal) {
        TypedQuery<Registration> query = em.createNamedQuery("findRegistrationsBySAL", Registration.class);
        query.setParameter("sid", sal.getSalId());
        return query.getResultList();
    }
    
    //returns registrations based on service
    public List<Registration> findRegistrationsByService(Service service) {
        TypedQuery<Registration> query = em.createNamedQuery("findRegistrationsByService", Registration.class);
        query.setParameter("sid", service.getServiceId());
        return query.getResultList();
    }

    //deletes existing registration
    public void deleteRegistration(Registration reg) {
        reg = em.find(Registration.class, reg.getRegId());
        em.remove(em.merge(reg));
    }

    //edits existing registration
    public Registration updateRegistration(Registration reg) {
        return em.merge(reg);
    }
}
