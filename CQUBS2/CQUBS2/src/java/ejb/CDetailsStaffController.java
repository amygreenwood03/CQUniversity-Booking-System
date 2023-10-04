package ejb;

import jakarta.ejb.EJB;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import java.util.List;
import java.io.Serializable;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * This class in charge of managing category details. 
 * Staff can change the details of categories related to the departments they are associated with. 
 */

@Named(value = "cDetailsStaffController")
@SessionScoped
public class CDetailsStaffController implements Serializable {
    @EJB
    private CategoryEJB categoryEJB; //CategoryEJB instance
    
    @EJB
    private RegistrationEJB regEJB; //RegistrationEJB instance
    
    @EJB
    private ServiceEJB serviceEJB; //ServiceEJB instance
    
    @EJB
    private ServiceAtLocationEJB salEJB; //ServiceAtLocationEJB instance
    
    private Category category; //stores category object
    private Long categoryId = 0L; //stores category id passed from previous view
    
    private String pageName, categoryName = ""; //page title & current category name
    private String imageUrl; //URL of image in string format for upload to database
    
    private Part promoImg; //stores image & related data uploaded through form
    private File savedImg; //stores image file to be written
    
    //default constructor
    public CDetailsStaffController() {
        
    }
    
    //initialises page upon load
    public void init() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        
        if(categoryId > 0L) {
            category = categoryEJB.findCategoryById(categoryId);
            pageName = category.getCategoryName();
        }
        else {
            try {
                ctx.getExternalContext().redirect("categories_staff.faces");
            }
            catch(IOException e) {
                
            }
        }
    }
    
    //initialises page for edit upon load
    public void editInit() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        
        if(categoryId == 0L) {
            try {
                ctx.getExternalContext().redirect("categories_staff.faces");
            }
            catch(IOException e) {
                
            }
        }
        else {   
            categoryName = category.getCategoryName();
        }
    }
    
    //returns all registrations for a service
    public List<Registration> getTotalRegList(Service service) {
        List<Registration> totalRegList = regEJB.findRegistrationsByService(service);
        return totalRegList;
    }
    
    //returns all services associated with a particular category
    public List<Service> getServiceList(Category cat) {
        List<Service> serviceList = serviceEJB.findServicesByCategory(cat);
        return serviceList;
    }
    
    //edits existing category
    public String edit() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        FacesMessage editError = new FacesMessage("", "Please fill out all fields.");
        
        if(categoryName.isBlank()) {
            ctx.addMessage("editForm", editError);
            return null;
        }
        
        category.setCategoryName(categoryName);
        
        if(promoImg != null) {
            uploadImg();
            category.setImageUrl(imageUrl);
        }
        
        categoryEJB.updateCategory(category);
        
        return "category_details_staff.faces?faces-redirect=true";
    }
    
    //deletes existing category (including all services & registrations if applicable)
    public String delete() {   
        List<Service> serviceList = getServiceList(category);
        
        if(serviceList != null && !serviceList.isEmpty()) {
            for(int i = 0; i < serviceList.size(); i++) {
                List<Registration> regList = getTotalRegList(serviceList.get(i));
                
                if(regList != null && !regList.isEmpty()) {
                    for(int j = 0; j < regList.size(); j++)
                        regEJB.deleteRegistration(regList.get(j));
                }
            }
        }
        
        List<ServiceAtLocation> salList = salEJB.findSALsByCategory(category);
        
        if(salList != null && !salList.isEmpty()) {
            for(int i = 0; i < salList.size(); i++)
                salEJB.deleteSAL(salList.get(i));
        }
        
        for(int i = 0; i < serviceList.size(); i++)
            serviceEJB.deleteService(serviceList.get(i));
        
        categoryEJB.deleteCategory(category);
        
        return "categories_staff.faces?faces-redirect=true";
    }
    
    //saves uploaded image to dedicated images directory on server
    public void uploadImg() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        
        //Change the image path to your Glassfish docroot/images folder
        //String imagesPath = "/Users/Producer.P/glassfish7/glassfish/domains/domain1/docroot/images";
        String imagesPath = "/Users/Amy/glassfish7/glassfish/domains/domain1/docroot/images";
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

    //categoryId accessor
    public Long getCategoryId() {
        return categoryId;
    }

    //categoryId mutator
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    //pageName accessor
    public String getPageName() {
        return pageName;
    }

    //pageName mutator
    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    //promoImg accessor
    public Part getPromoImg() {
        return promoImg;
    }

    //promoImg mutator
    public void setPromoImg(Part promoImg) {
        this.promoImg = promoImg;
    }

    //categoryName accessor
    public String getCategoryName() {
        return categoryName;
    }

    //categoryName mutator
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
