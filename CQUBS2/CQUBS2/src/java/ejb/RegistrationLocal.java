/*
 * RegistrationLocal.java - Registration Local Interface class
 * Interface to allow server access
 */
package ejb;

import jakarta.ejb.Local;
import java.util.List;

/**
 * * @author HeimannK
 */
@Local
public interface RegistrationLocal {
    
    // Public methods
    List<Registration> findRegistrations();

    Registration findRegById(Long id);
    
    Registration createRegistration(Registration reg);
    
    void deleteRegistration(Registration reg);
    
    Registration updateRegistration(Registration reg);    
}
