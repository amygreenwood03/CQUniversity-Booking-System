package cqubs;

import ejb.Category;
import ejb.CategoryEJB;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Amy
 */

@Named(value = "homeController")
@SessionScoped
public class HomeController 
{
    @EJB
    private CategoryEJB categoryEJB;
    
    private List<Category> categoryList = new ArrayList<>();
    
    private final String PAGE_NAME = "Home";
    
    public HomeController() 
    {

    }
    
    @PostConstruct
    public void init()
    {
        categoryList = categoryEJB.findCategories();
    }

    public List<Category> getCategoryList() 
    {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) 
    {
        this.categoryList = categoryList;
    }

    public String getPAGE_NAME() 
    {
        return PAGE_NAME;
    } 
}
