/*
 * UsersRemote.java - Users Local Interface class
 * Interface to allow server access
 */
package ejb;

import jakarta.ejb.Local;
import java.util.List;

/**
 * * @author HeimannK
 */
@Local
public interface UsersLocal {
    
    // Public methods
    List<Staff> findStaff();
    
    List<Volunteer> findVolunteer();
    
    Staff findStaffById(Long id);
    
    Volunteer findVolById(Long id);
    
    Staff findStaffByEmail(String email);
    
    Volunteer findVolByEmail(String email);
    
    Staff createStaff(Staff staff);
    
    Volunteer createVolunteer(Volunteer volunteer);
    
    void deleteStaff(Staff staff);
    
    void deleteVolunteer(Volunteer volunteer);
    
    Staff updateStaff(Staff staff);
    
    Volunteer updateVolunteer(Volunteer volunteer);    
}
