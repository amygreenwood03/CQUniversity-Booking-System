/*
 * DepartmentLocal.java - Department Local Interface class
 * Interface to allow server access
 */
package ejb;

import jakarta.ejb.Local;
import java.util.List;

/**
 * * @author HeimannK
 */
@Local
public interface DepartmentLocal {
    
    // Public methods
    List<Department> findDepartments();
    
    Department findDeptById(Long id);
    
    Department findDeptByName(String deptName);
    
    Department createDept(Department dept);
    
    void deleteDept(Department dept);
    
    Department updateDept(Department dept);
}
