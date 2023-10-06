package ejb;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import jakarta.faces.context.FacesContext;
import java.io.IOException;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * This controller allows staff to add a new category
 */

@Named(value = "categoryAddController")
@SessionScoped
public class CategoryAddController implements Serializable {
    @EJB
    private CategoryEJB categoryEJB; //CategoryEJB instance
    
    private Category category = new Category(); //stores category object to be added
    
    private final String PAGE_NAME = "Add Category"; //page title
    private String imageUrl; //URL of image in string format for upload to database
    
    private Part promoImg; //stores image & related data uploaded through form
    private File savedImg; //stores image file to be written
    
    //default constructor
    public CategoryAddController() {
        
    }
    
    //returns whether or not any form fields are empty
    public boolean checkFields() {
        if(category.getCategoryName().isBlank() || promoImg == null)
            return true;
        
        return false;
    }
    
    //creates new category
    public String create(Staff user) {
        FacesContext ctx = FacesContext.getCurrentInstance();
        FacesMessage createError = new FacesMessage("", "Please fill out all fields and upload a promotional image.");
        
        if(checkFields()) {
            ctx.addMessage("createForm", createError);
            return null;
        }
        
        uploadImg();
        
        category.setImageUrl(imageUrl);
        category.setDept(user.getDepartment());
        
        categoryEJB.createCategory(category);
        
        return "categories_staff.faces?faces-redirect=true";
    }
    
    //saves uploaded image to dedicated images directory on server
    public void uploadImg() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        
        //Change the image path to your Glassfish docroot/images folder
        //String imagesPath = "/Users/Producer.P/glassfish7/glassfish/domains/domain1/docroot/images";
        //String imagesPath = "/Users/Amy/glassfish7/glassfish/domains/domain1/docroot/images";
	String imagesPath = "/Users/Petre/glassfish7/glassfish/domains/domain1/docroot/images";
        String filename = Paths.get(promoImg.getSubmittedFileName()).getFileName().toString();
        
        imageUrl = "/images/" + filename;
        
        savedImg = new File(imagesPath, filename);
        
        try(InputStream input = promoImg.getInputStream()) {
            Files.copy(input, savedImg.toPath());
        }
        catch(IOException e) {
            
        }
    }

    //category accessor
    public Category getCategory() {
        return category;
    }

    //category mutator
    public void setCategory(Category category) {
        this.category = category;
    }

    //PAGE_NAME accessor
    public String getPAGE_NAME() {
        return PAGE_NAME;
    }
    
    //promoImg accessor
    public Part getPromoImg() {
        return promoImg;
    }

    //promoImg mutator
    public void setPromoImg(Part promoImg) {
        this.promoImg = promoImg;
    }
}
