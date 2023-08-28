/*
 * RegistrationRemote.java - Registration Remote Interface class
 * Interface to allow server access
 */
package ejb;

import jakarta.ejb.Remote;
import java.util.List;

/**
 * * @author HeimannK
 */
@Remote
public interface RegistrationRemote {
    
    // Public methods
    List<Registration> findRegistrations();

    Registration findRegById(Long id);
    
    Registration createRegistration(Registration reg);
    
    void deleteRegistration(Registration reg);
    
    Registration updateRegistration(Registration reg);
}
