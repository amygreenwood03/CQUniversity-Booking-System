package ejb;

import jakarta.ejb.EJB;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;
import jakarta.faces.context.FacesContext;
import java.io.IOException;

/**
 *
 * @author Amy
 */

@Named(value = "cDetailsStaffController")
@SessionScoped
public class CDetailsStaffController implements Serializable
{
    @EJB
    private CategoryEJB categoryEJB;
    
    @EJB
    private RegistrationEJB regEJB;
    
    @EJB
    private ServiceEJB serviceEJB;
    
    @EJB
    private ServiceAtLocationEJB salEJB;
    
    private Category category = new Category();
    private Long categoryId = 0L;
    
    private String pageName = "";
    
    public CDetailsStaffController() 
    {
    }
    
    public void init()
    {
        FacesContext ctx = FacesContext.getCurrentInstance();
        
        if(categoryId > 0L)
        {
            category = categoryEJB.findCategoryById(categoryId);
            pageName = category.getCategoryName();
        }
        else
        {
            try {
                ctx.getExternalContext().redirect("categories_staff.faces");
            }
            catch(IOException e) {
                
            }
        }
    }
    
    public void editInit()
    {
        FacesContext ctx = FacesContext.getCurrentInstance();
        
        if(categoryId == 0L)
        {
            try {
                ctx.getExternalContext().redirect("categories_staff.faces");
            }
            catch(IOException e) {
                
            }
        }
    }
    
    public List<Registration> getTotalRegList(Service service)
    {
        List<Registration> totalRegList = regEJB.findRegistrationsByService(service);
        return totalRegList;
    }
    
    public List<Service> getServiceList(Category cat)
    {
        List<Service> serviceList = serviceEJB.findServicesByCategory(cat);
        return serviceList;
    }
    
    public void edit()
    {
        categoryEJB.updateCategory(category);
        
        FacesContext ctx = FacesContext.getCurrentInstance();
        
        try {
            ctx.getExternalContext().redirect("category_details_staff.faces?categoryId=" + category.getCat_id());
        }
        catch(IOException e) {
                
        }
    }
    
    public void delete()
    {   
        List<Service> serviceList = getServiceList(category);
        
        if(serviceList != null && !serviceList.isEmpty())
        {
            for(int i = 0; i < serviceList.size(); i++)
            {
                List<Registration> regList = getTotalRegList(serviceList.get(i));
                
                if(regList != null && !regList.isEmpty())
                {
                    for(int j = 0; j < regList.size(); j++)
                        regEJB.deleteRegistration(regList.get(j));
                }
            }
        }
        
        List<ServiceAtLocation> salList = salEJB.findSALsByCategory(category);
        
        if(salList != null && !salList.isEmpty())
        {
            for(int i = 0; i < salList.size(); i++)
                salEJB.deleteSAL(salList.get(i));
        }
        
        for(int i = 0; i < serviceList.size(); i++)
            serviceEJB.deleteService(serviceList.get(i));
        
        categoryEJB.deleteCategory(category);
        
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

    public Long getCategoryId() 
    {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) 
    {
        this.categoryId = categoryId;
    }

    public String getPageName() 
    {
        return pageName;
    }

    public void setPageName(String pageName) 
    {
        this.pageName = pageName;
    }
}
