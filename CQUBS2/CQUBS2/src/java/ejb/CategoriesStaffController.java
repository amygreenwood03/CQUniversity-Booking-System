package ejb;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Amy
 */

@Named(value = "categoriesStaffController")
@SessionScoped
public class CategoriesStaffController implements Serializable
{
    @EJB
    private CategoryEJB categoryEJB;
    
    @EJB
    private ServiceEJB serviceEJB;
    
    private List<Category> categoriesList = new ArrayList<>();
    
    public CategoriesStaffController() 
    {
    }
    
    public void init()
    {
        FacesContext ctx = FacesContext.getCurrentInstance();
        Staff user = (Staff) ctx.getExternalContext().getSessionMap().get("user");
        
        categoriesList = categoryEJB.findCategoriesByDepartment(user.getDepartment());
    }
    
    public List<Service> getServiceList(Category cat)
    {
        List<Service> serviceList = serviceEJB.findServicesByCategory(cat);
        return serviceList;
    }

    public List<Category> getCategoriesList() 
    {
        return categoriesList;
    }

    public void setCategoriesList(List<Category> categoriesList) 
    {
        this.categoriesList = categoriesList;
    }
}
