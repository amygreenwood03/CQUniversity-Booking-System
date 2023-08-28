/*
 * LoginRemote.java - Login Remote Interface class
 * Interface to allow server access
 */
package ejb;

import jakarta.ejb.Remote;
import java.util.List;

/**
 * * @author HeimannK
 */
@Remote
public interface LoginRemote {
    
    // Public methods
    List<Login> findLogins();
    
    Login findLoginById(long id);
    
    Login findLoginByEmail(String email);
    
    Login createLogin(Login login);
    
    void deleteLogin(Login login);
    
    Login updateLogin(Login login);    
}
