/*
 * UsersRemote.java - Users Remote Interface class
 * Interface to allow server access
 */
package ejb;

import jakarta.ejb.Remote;
import java.util.List;

/**
 * * @author HeimannK
 */
@Remote
public interface UsersRemote {
    
    // Public methods
    List<Staff> findStaff();
    
    List<Volunteer> findVolunteer();
    
    Staff findStaffById(Long id);
    
    Volunteer findVolById(Long id);
    
    Staff createStaff(Staff staff);
    
    Volunteer createVolunteer(Volunteer volunteer);
    
    void deleteStaff(Staff staff);
    
    void deleteVolunteer(Volunteer volunteer);
    
    Staff updateStaff(Staff staff);
    
    Volunteer updateVolunteer(Volunteer volunteer);
}
