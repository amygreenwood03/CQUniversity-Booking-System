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
 * Staffs can change the details of categories related to the departments they are associated with. 
 */

@Named(value = "cDetailsStaffController")
@SessionScoped
public class CDetailsStaffController implements Serializable {
    @EJB
    private CategoryEJB categoryEJB;
    
    @EJB
    private RegistrationEJB regEJB;
    
    @EJB
    private ServiceEJB serviceEJB;
    
    @EJB
    private ServiceAtLocationEJB salEJB;
    
    private Category category;
    private Long categoryId = 0L;
    
    private String pageName, categoryName = "";
    private String imageUrl;
    
    private Part promoImg;
    private File savedImg;
    
    public CDetailsStaffController() {
        
    }
    
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
    
    public void editInit() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        
        if(categoryId == 0L) {
            try {
                ctx.getExternalContext().redirect("categories_staff.faces");
            }
            catch(IOException e) {
                
            }
        }
        else
        {   
            categoryName = category.getCategoryName();
        }
    }
    
    public List<Registration> getTotalRegList(Service service) {
        List<Registration> totalRegList = regEJB.findRegistrationsByService(service);
        return totalRegList;
    }
    
    public List<Service> getServiceList(Category cat) {
        List<Service> serviceList = serviceEJB.findServicesByCategory(cat);
        return serviceList;
    }
    
    public String edit() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        FacesMessage editError = new FacesMessage("", "Please fill out all fields.");
        
        if(categoryName.isBlank())
        {
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
    
    public void uploadImg() {
        FacesContext ctx = FacesContext.getCurrentInstance();
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public Part getPromoImg() {
        return promoImg;
    }

    public void setPromoImg(Part promoImg) {
        this.promoImg = promoImg;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
