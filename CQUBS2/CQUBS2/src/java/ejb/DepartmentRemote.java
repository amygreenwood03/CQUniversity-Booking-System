/*
 * DepartmentRemote.java - Department Remote Interface class
 * Interface to allow server access
 */
package ejb;

import jakarta.ejb.Remote;
import java.util.List;

/**
 * * @author HeimannK
 */
@Remote
public interface DepartmentRemote {
    
    // Public methods
    List<Department> findDepartments();
    
    Department findDeptById(Long id);
    
    Department findDeptByName(String deptName);
    
    Department createDept(Department dept);
    
    void deleteDept(Department dept);
    
    Department updateDept(Department dept);
}
