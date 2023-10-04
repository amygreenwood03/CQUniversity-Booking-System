package ejb;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This is a controller for index.xhtml page.
 */

@Named(value = "homeController")
@SessionScoped
public class HomeController implements Serializable {
    @EJB
    private CategoryEJB categoryEJB; //CategoryEJB instance
    private List<Category> categoryList = new ArrayList<>(); //stores all categories in database
    private final String PAGE_NAME = "Home"; //page title
    
    //default constructor
    public HomeController() {

    }
    
    //initialises page upon load
    @PostConstruct
    public void init() {
        categoryList = categoryEJB.findCategories();
    }

    //categoryList accessor
    public List<Category> getCategoryList() {
        return categoryList;
    }

    //categoryList mutator
    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    //PAGE_NAME accessor
    public String getPAGE_NAME() {
        return PAGE_NAME;
    } 
}
