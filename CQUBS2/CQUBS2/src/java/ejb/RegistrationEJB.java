/*
 * RegistrationEJB.java - Session Bean for Registration classes
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
//@Remote(RegistrationRemote.class)
public class RegistrationEJB {

    // Attributes
    @PersistenceContext(unitName = "CQUBSPU")
    private EntityManager em;
    
    @Resource
    SessionContext ctx;

    // Public methods
    //@Override
    public List<Registration> findRegistrations() {
        TypedQuery<Registration> query = em.createNamedQuery("findAllRegistrations", Registration.class);
        return query.getResultList();
    }

    //@Override
    public Registration findRegById(Long id) {
        return em.find(Registration.class, id);
    }

    //@Override
    public Registration createRegistration(Registration reg, Volunteer vol) {
        vol = em.find(Volunteer.class, vol.getId());
        
        reg.setVolunteer(vol);
        em.persist(reg);
        System.out.println(ctx.getCallerPrincipal().getName());
        return reg;
    }
    
    public List<Registration> findRegistrationsByVolunteer(Volunteer vol)
    {
        TypedQuery<Registration> query = em.createNamedQuery("findRegistrationsByVolunteer", Registration.class);
        query.setParameter("vid", vol.getId());
        return query.getResultList();
    }
    
    public List<Registration> findRegistrationsBySAL(ServiceAtLocation sal)
    {
        TypedQuery<Registration> query = em.createNamedQuery("findRegistrationsBySAL", Registration.class);
        query.setParameter("sid", sal.getSalId());
        return query.getResultList();
    }

    //@Override
    public void deleteRegistration(Registration reg) {
        reg = em.find(Registration.class, reg.getRegId());
        em.remove(em.merge(reg));
    }

    //@Override
    public Registration updateRegistration(Registration reg) {
        return em.merge(reg);
    }
}
