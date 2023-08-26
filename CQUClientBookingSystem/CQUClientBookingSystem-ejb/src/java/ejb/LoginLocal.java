/*
 * LoginLocal.java - Login Local Interface class
 * Interface to allow server access
 */
package ejb;

import jakarta.ejb.Local;
import java.util.List;

/**
 * * @author HeimannK
 */
@Local
public interface LoginLocal {
    
    // Public methods
    List<Login> findLogins();
    
    Login findLoginById(long id);
    
    Login createLogin(Login login);
    
    void deleteLogin(Login login);
    
    Login updateLogin(Login login);     
}
