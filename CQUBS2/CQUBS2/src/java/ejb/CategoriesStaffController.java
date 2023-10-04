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
 * This class allow staffs to add, update or delete categories related to the departments they are assigned with.
 */

@Named(value = "categoriesStaffController")
@SessionScoped
public class CategoriesStaffController implements Serializable {
    @EJB
    private CategoryEJB categoryEJB; //categoryEJB instance
    
    @EJB
    private ServiceEJB serviceEJB; //serviceEJB instance
    
    private List<Category> categoriesList = new ArrayList<>(); //stores all categories in database
    
    //default constructor
    public CategoriesStaffController() {
        
    }
    
    //initialises the page upon load
    public void init() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        Staff user = (Staff) ctx.getExternalContext().getSessionMap().get("user");
        
        categoriesList = categoryEJB.findCategoriesByDepartment(user.getDepartment());
    }
    
    //returns services associated with a certain category
    public List<Service> getServiceList(Category cat) {
        List<Service> serviceList = serviceEJB.findServicesByCategory(cat);
        return serviceList;
    }

    //categoriesList accessor
    public List<Category> getCategoriesList() {
        return categoriesList;
    }

    //categoriesList mutator
    public void setCategoriesList(List<Category> categoriesList) {
        this.categoriesList = categoriesList;
    }
}
