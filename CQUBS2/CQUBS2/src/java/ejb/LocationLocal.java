/*
 * LocationLocal.java - Location Local Interface class
 * Interface to allow server access
 */
package ejb;

import jakarta.ejb.Local;
import java.util.List;

/**
 * * @author HeimannK
 */
@Local
public interface LocationLocal {
    
    // Public methods
    List<Location> findLocations();
    
    Location findLocationById(Long id);
    
    Location findLocationByName(String name);
    
    Location createLocation(Location loc);
    
    void deleteLocation(Location loc);
    
    Location updateLocation(Location loc);    
}
