/*
 * ServiceAtLocationLocal.java - ServiceAtLocation Local Interface class
 * Interface to allow server access
 */
package ejb;

import jakarta.ejb.Local;
import java.util.List;

/**
 * * @author HeimannK
 */
@Local
public interface ServiceAtLocationLocal {
    
    // Public methods
    List<ServiceAtLocation> findSALs();
    
    ServiceAtLocation findSALById(Long id);
    
    ServiceAtLocation createSAL(ServiceAtLocation sal);
    
    void deleteSAL(ServiceAtLocation sal);
    
    ServiceAtLocation updateSAL(ServiceAtLocation sal);    
}
