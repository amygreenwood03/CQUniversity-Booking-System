package ejb;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import jakarta.faces.context.FacesContext;
import java.io.IOException;
import jakarta.ejb.EJB;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

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
    private String imageUrl;
    
    private Part promoImg;
    private File savedImg;
    
    public CategoryAddController() 
    {
    }
    
    public void create(Staff user)
    {
        uploadImg();
        
        category.setImageUrl(imageUrl);
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
    
    public void uploadImg()
    {
        FacesContext ctx = FacesContext.getCurrentInstance();
        
        String imagesPath = "/Users/Amy/glassfish7/glassfish/domains/domain1/docroot/images";
        String filename = Paths.get(promoImg.getSubmittedFileName()).getFileName().toString();
        
        imageUrl = "/images/" + filename;
        
        savedImg = new File(imagesPath, filename);
        
        try(InputStream input = promoImg.getInputStream())
        {
            Files.copy(input, savedImg.toPath());
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
    
    public Part getPromoImg() 
    {
        return promoImg;
    }

    public void setPromoImg(Part promoImg) 
    {
        this.promoImg = promoImg;
    }
}
