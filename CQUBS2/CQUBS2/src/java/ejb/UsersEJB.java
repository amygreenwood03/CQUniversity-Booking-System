/*
 * UsersEJB.java - Session Bean for User/Staff/Volunteer classes
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
public class UsersEJB{

    // Attributes
    @PersistenceContext(unitName = "CQUBSPU")
    private EntityManager em; //entity manager instance
    
    @Resource
    SessionContext ctx; //current session context of server
    
    //returns all staff
    public List<Staff> findStaff() {
        TypedQuery<Staff> query = em.createNamedQuery("findAllStaff", Staff.class);
        return query.getResultList();
    }

    //returns all volunteers
    public List<Volunteer> findVolunteer() {
        TypedQuery<Volunteer> query = em.createNamedQuery("findAllVolunteers", Volunteer.class);
        return query.getResultList();
    }

    //returns staff based on id
    public Staff findStaffById(Long id) {
        return em.find(Staff.class, id);
    }

    //returns volunteer based on id
    public Volunteer findVolById(Long id) {
        return em.find(Volunteer.class, id);
    }

    //creates new staff
    public Staff createStaff(Staff staff) {
        em.persist(staff);
        System.out.println(ctx.getCallerPrincipal().getName());
        return staff;
    }

    //creates new volunteer
    public Volunteer createVolunteer(Volunteer volunteer) {
        em.persist(volunteer);
        System.out.println(ctx.getCallerPrincipal().getName());
        return volunteer;
    }

    //deletes existing staff
    public void deleteStaff(Staff staff) {
        em.remove(em.merge(staff));
    }

    //deletes existing volunteer
    public void deleteVolunteer(Volunteer volunteer) {
        em.remove(em.merge(volunteer));
    }

    //edits existing staff
    public Staff updateStaff(Staff staff) {
        return em.merge(staff);
    }

    //edits existing volunteer
    public Volunteer updateVolunteer(Volunteer volunteer) {
        Volunteer vol = em.find(Volunteer.class, volunteer.getId());
        vol.setFirstName(volunteer.getFirstName());
        vol.setLastName(volunteer.getLastName());
        vol.setEmail(volunteer.getEmail());
        vol.setPhone(volunteer.getPhone());
        
        return em.merge(vol);
    }

    //returns staff based on email
    public Staff findStaffByEmail(String email) {
        TypedQuery<Staff> query = em.createNamedQuery("findStaffByEmail", Staff.class);
        query.setParameter("email", email);
        return query.getSingleResult();
    }

    //returns volunteers based on email
    public Volunteer findVolByEmail(String email) {
        TypedQuery<Volunteer> query = em.createNamedQuery("findVolByEmail", Volunteer.class);
        query.setParameter("email", email);
        return query.getSingleResult();
    }
}
