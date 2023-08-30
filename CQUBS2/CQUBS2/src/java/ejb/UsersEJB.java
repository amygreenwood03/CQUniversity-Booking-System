/*
 * UsersEJB.java - Session Bean for User/Staff/Volunteer classes
 * Bean to implement UsersRemote interface
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
//@Remote(UsersRemote.class)
public class UsersEJB{

    // Attributes
    @PersistenceContext(unitName = "CQUBSPU")
    private EntityManager em;
    
    @Resource
    SessionContext ctx;
    
    // Public methods
    //@Override
    public List<Staff> findStaff() {
        TypedQuery<Staff> query = em.createNamedQuery("findAllStaff", Staff.class);
        return query.getResultList();
    }

    //@Override
    public List<Volunteer> findVolunteer() {
        TypedQuery<Volunteer> query = em.createNamedQuery("findAllVolunteers", Volunteer.class);
        return query.getResultList();
    }

    //@Override
    public Staff findStaffById(Long id) {
        return em.find(Staff.class, id);
    }

    //@Override
    public Volunteer findVolById(Long id) {
        return em.find(Volunteer.class, id);
    }

    //@Override
    public Staff createStaff(Staff staff) {
        em.persist(staff);
        System.out.println(ctx.getCallerPrincipal().getName());
        return staff;
    }

    //@Override
    public Volunteer createVolunteer(Volunteer volunteer) {
        em.persist(volunteer);
        System.out.println(ctx.getCallerPrincipal().getName());
        return volunteer;
    }

    //@Override
    public void deleteStaff(Staff staff) {
        em.remove(em.merge(staff));
    }

    //@Override
    public void deleteVolunteer(Volunteer volunteer) {
        em.remove(em.merge(volunteer));
    }

    //@Override
    public Staff updateStaff(Staff staff) {
        return em.merge(staff);
    }

    //@Override
    public Volunteer updateVolunteer(Volunteer volunteer) {
        return em.merge(volunteer);
    }

    //@Override
    public Staff findStaffByEmail(String email) {
        TypedQuery<Staff> query = em.createNamedQuery("findStaffByEmail", Staff.class);
        query.setParameter("email", email);
        return query.getSingleResult();
    }

    //@Override
    public Volunteer findVolByEmail(String email) {
        TypedQuery<Volunteer> query = em.createNamedQuery("findVolByEmail", Volunteer.class);
        query.setParameter("email", email);
        return query.getSingleResult();
    }
}
