/*
 * ServiceAtLocationRemote.java - ServiceAtLocation Remote Interface class
 * Interface to allow server access
 */
package ejb;

import jakarta.ejb.Remote;
import java.util.List;

/**
 * * @author HeimannK
 */
@Remote
public interface ServiceAtLocationRemote {
    
    // Public methods
    List<ServiceAtLocation> findSALs();
    
    ServiceAtLocation findSALById(Long id);
    
    ServiceAtLocation createSAL(ServiceAtLocation sal);
    
    void deleteSAL(ServiceAtLocation sal);
    
    ServiceAtLocation updateSAL(ServiceAtLocation sal);
    
}
