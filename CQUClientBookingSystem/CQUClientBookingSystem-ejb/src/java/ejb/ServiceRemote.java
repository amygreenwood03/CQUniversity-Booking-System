/*
 * ServiceRemote.java - Service Remote Interface class
 * Interface to allow server access
 */
package ejb;

import jakarta.ejb.Remote;
import java.util.List;

/**
 * * @author HeimannK
 */
@Remote
public interface ServiceRemote {
    
    // Public method
    List<Service> findServices();
    
    Service findServiceById(Long id);
    
    Service createService(Service service);
    
    void deleteService(Service service);
    
    Service updateService(Service service);
}
