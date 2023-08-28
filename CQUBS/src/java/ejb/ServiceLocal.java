/*
 * ServiceRemote.java - Service Local Interface class
 * Interface to allow server access
 */
package ejb;

import jakarta.ejb.Local;
import java.util.List;

/**
 * * @author HeimannK
 */
@Local
public interface ServiceLocal {
    
    // Public method
    List<Service> findServices();
    
    Service findServiceById(Long id);
    
    Service findServiceByName(String name);
    
    Service createService(Service service);
    
    void deleteService(Service service);
    
    Service updateService(Service service);    
}

