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
import java.util.List;

/**
 * * @author HeimannK
 */
@Stateless
@Remote(RegistrationRemote.class)
public class RegistrationEJB implements RegistrationLocal, RegistrationRemote {

    // Attributes
    @PersistenceContext(unitName = "CQUClientBookingSystem-ejbPU")
    private EntityManager em;
    
    @Resource
    SessionContext ctx;

    // Public methods
    @Override
    public List<Registration> findRegistrations() {
        Query query = em.createNamedQuery("Registration.findAllRegistrations");
        return query.getResultList();
    }

    @Override
    public Registration findRegById(Long id) {
        return em.find(Registration.class, id);
    }

    @Override
    public Registration createRegistration(Registration reg) {
        em.persist(reg);
        System.out.println(ctx.getCallerPrincipal().getName());
        return reg;
    }

    @Override
    public void deleteRegistration(Registration reg) {
        em.remove(em.merge(reg));
    }

    @Override
    public Registration updateRegistration(Registration reg) {
        return em.merge(reg);
    }
}
