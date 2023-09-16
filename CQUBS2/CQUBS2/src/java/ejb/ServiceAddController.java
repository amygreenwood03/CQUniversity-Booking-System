package ejb;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import java.io.IOException;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * This controller allows staff to add a new service on a category.
 */

@Named(value = "serviceAddController")
@SessionScoped
public class ServiceAddController implements Serializable {
    @EJB
    private LocationEJB locationEJB;
    
    @EJB
    private CategoryEJB categoryEJB;
    
    @EJB
    private ServiceEJB serviceEJB;
    
    @EJB
    private ServiceAtLocationEJB salEJB;
    
    private final String PAGE_NAME = "Add Service";
    private String selectedPrice, priceString, imageUrl;
    
    private List<Location> selectedLocationsList = new ArrayList<>();
    private List<Location> locationsList = new ArrayList<>();
    
    private Long selectedCategoryId = 0L;
    private List<Category> categoriesList = new ArrayList<>();
    
    private Service service;
    
    private Part promoImg;
    private File savedImg;
    
    public ServiceAddController() {
        
    }
    
    @PostConstruct
    public void init() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        Staff user = (Staff) ctx.getExternalContext().getSessionMap().get("user");
        
        locationsList = locationEJB.findLocations();
        categoriesList = categoryEJB.findCategoriesByDepartment(user.getDepartment());
        
        selectedLocationsList.clear();
        selectedPrice = "Free";
        priceString = "";
        
        service = new Service();
    }
    
    public boolean checkFields() {
        if(service.getServiceName().isBlank() || selectedLocationsList.isEmpty() || selectedPrice.isBlank() || selectedCategoryId == 0L || promoImg == null || service.getServiceDescription().isBlank())
            return true;
        
        return false;
    }
    
    public String create() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        FacesMessage createError = new FacesMessage("", "Please fill out all fields and upload a promotional image.");
        
        if(checkFields()) {
            ctx.addMessage(("createForm"), createError);
            return null;
        }
        
        uploadImg();
        
        if(selectedPrice.equals("Free"))
            service.setServicePrice(0.0);
        else {
            if(priceString.isBlank()) {
                ctx.addMessage(("createForm"), createError);
                return null;
            }
            else {
                try {
                    service.setServicePrice(Double.parseDouble(priceString));
                }
                catch(NumberFormatException e) {
                    ctx.addMessage("createForm", new FacesMessage("", "The specified service price must be a number."));
                    return null;
                }
            }
        }
        
        service.setCategory(categoryEJB.findCategoryById(selectedCategoryId));
        service.setImageUrl(imageUrl);
        
        List<ServiceAtLocation> sals = new ArrayList<>();
        
        for(int i = 0; i < selectedLocationsList.size(); i++) {
            sals.add(new ServiceAtLocation(service, selectedLocationsList.get(i)));
        }
        
        service.setSalList(sals);
        
        serviceEJB.createService(service);
        
        return "services_staff.faces?faces-redirect=true";
    }
    
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

    public String getSelectedPrice() {
        return selectedPrice;
    }

    public void setSelectedPrice(String selectedPrice) {
        this.selectedPrice = selectedPrice;
    }

    public String getPriceString() {
        return priceString;
    }

    public void setPriceString(String priceString) {
        this.priceString = priceString;
    }

    public List<Location> getSelectedLocationsList() {
        return selectedLocationsList;
    }

    public void setSelectedLocationsList(List<Location> selectedLocationsList) {
        this.selectedLocationsList = selectedLocationsList;
    }

    public List<Location> getLocationsList() {
        return locationsList;
    }

    public void setLocationsList(List<Location> locationsList) {
        this.locationsList = locationsList;
    }

    public Long getSelectedCategoryId() {
        return selectedCategoryId;
    }

    public void setSelectedCategoryId(Long selectedCategoryId) {
        this.selectedCategoryId = selectedCategoryId;
    }

    public List<Category> getCategoriesList() {
        return categoriesList;
    }

    public void setCategoriesList(List<Category> categoriesList) {
        this.categoriesList = categoriesList;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public String getPAGE_NAME() {
        return PAGE_NAME;
    } 

    public Part getPromoImg() {
        return promoImg;
    }

    public void setPromoImg(Part promoImg) {
        this.promoImg = promoImg;
    }
}
