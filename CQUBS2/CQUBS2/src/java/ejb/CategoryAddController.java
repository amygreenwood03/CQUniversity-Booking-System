package ejb;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import jakarta.faces.context.FacesContext;
import java.io.IOException;
import jakarta.ejb.EJB;

/**
 *
 * @author Amy
 */

@Named(value = "categoryAddController")
@SessionScoped
public class CategoryAddController implements Serializable
{
    @EJB
    private CategoryEJB categoryEJB;
    
    private Category category = new Category();
    
    private final String PAGE_NAME = "Add Category";
    
    public CategoryAddController() 
    {
    }
    
    public void create(Staff user)
    {
        category.setImageUrl("img/placeholder.png");
        category.setDept(user.getDepartment());
        
        categoryEJB.createCategory(category);
        
        FacesContext ctx = FacesContext.getCurrentInstance();
        
        try
        {
            ctx.getExternalContext().redirect("categories_staff.faces");
        }
        catch(IOException e)
        {
            
        }
    }

    public Category getCategory() 
    {
        return category;
    }

    public void setCategory(Category category) 
    {
        this.category = category;
    }

    public String getPAGE_NAME() 
    {
        return PAGE_NAME;
    }
}
