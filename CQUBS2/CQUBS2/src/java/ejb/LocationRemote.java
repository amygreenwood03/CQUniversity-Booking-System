/*
 * LocationRemote.java - Location Remote Interface class
 * Interface to allow server access
 */
package ejb;

import jakarta.ejb.Remote;
import java.util.List;

/**
 * * @author HeimannK
 */
@Remote
public interface LocationRemote {
    
    // Public methods
    List<Location> findLocations();
    
    Location findLocationById(Long id);
    
    Location findLocationByName(String name);
    
    Location createLocation(Location loc);
    
    void deleteLocation(Location loc);
    
    Location updateLocation(Location loc);
    
}
