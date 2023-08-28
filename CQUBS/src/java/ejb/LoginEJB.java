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
@Remote(LoginRemote.class)
public class LoginEJB implements LoginLocal, LoginRemote {

    // Attributes
    @PersistenceContext(unitName = "CQUBSPU")
    private EntityManager em;
    
    @Resource
    SessionContext ctx;

    // Public methods
    @Override
    public List<Login> findLogins() {
        Query query = em.createNamedQuery("Login.findAllLogins");
        return query.getResultList();
    }

    @Override
    public Login findLoginById(long id) {
        return em.find(Login.class, id);
    }
    
    @Override
    public Login findLoginByEmail(String email) {
        return em.find(Login.class, email);
    }

    @Override
    public Login createLogin(Login login) {
        em.persist(login);
        System.out.println(ctx.getCallerPrincipal().getName());
        return login;
    }

    @Override
    public void deleteLogin(Login login) {
        em.remove(em.merge(login));
    }

    @Override
    public Login updateLogin(Login login) {
        return em.merge(login);
    }
}
