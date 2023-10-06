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
 * This controller allows staff to add a new service.
 */

@Named(value = "serviceAddController")
@SessionScoped
public class ServiceAddController implements Serializable {
    @EJB
    private LocationEJB locationEJB; //LocationEJB instance
    
    @EJB
    private CategoryEJB categoryEJB; //CategoryEJB instance
    
    @EJB
    private ServiceEJB serviceEJB; //ServiceEJB instance
    
    @EJB
    private ServiceAtLocationEJB salEJB; //ServiceAtLocationEJB instance
    
    private final String PAGE_NAME = "Add Service"; //page title
    private String 
        selectedPrice, //price selected by user (ie. free or charge)
        priceString, //cost entered if applicable
        imageUrl; //URL of image in string format for upload to database
    
    private List<Location> selectedLocationsList = new ArrayList<>(); //stores locations selected by user
    private List<Location> locationsList = new ArrayList<>(); //stores all locations in database
    
    private Long selectedCategoryId = 0L; //stores selected category id
    private List<Category> categoriesList = new ArrayList<>(); //stores all categories in database
    
    private Service service; //stores service to be added
    
    private Part promoImg; //stores image & related data uploaded through form
    private File savedImg; //stores image file to be written
    
    //default constructor
    public ServiceAddController() {
        
    }
    
    //initialises page upon load
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
    
    //returns whether form fields are empty
    public boolean checkFields() {
        if(service.getServiceName().isBlank() || selectedLocationsList.isEmpty() || selectedPrice.isBlank() || selectedCategoryId == 0L || promoImg == null || service.getServiceDescription().isBlank())
            return true;
        
        return false;
    }
    
    //creates new service & associated sals
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

    //selectedPrice accessor
    public String getSelectedPrice() {
        return selectedPrice;
    }

    //selectedPrice mutator
    public void setSelectedPrice(String selectedPrice) {
        this.selectedPrice = selectedPrice;
    }

    //priceString accessor
    public String getPriceString() {
        return priceString;
    }

    //priceString mutator
    public void setPriceString(String priceString) {
        this.priceString = priceString;
    }

    //selectedLocationsList accessor
    public List<Location> getSelectedLocationsList() {
        return selectedLocationsList;
    }

    //selectedLocationsList mutator
    public void setSelectedLocationsList(List<Location> selectedLocationsList) {
        this.selectedLocationsList = selectedLocationsList;
    }

    //locationsList accessor
    public List<Location> getLocationsList() {
        return locationsList;
    }

    //locationsList mutator
    public void setLocationsList(List<Location> locationsList) {
        this.locationsList = locationsList;
    }

    //selectedCategoryId accessor
    public Long getSelectedCategoryId() {
        return selectedCategoryId;
    }

    //selectedCategoryId mutator
    public void setSelectedCategoryId(Long selectedCategoryId) {
        this.selectedCategoryId = selectedCategoryId;
    }

    //categoriesList accessor
    public List<Category> getCategoriesList() {
        return categoriesList;
    }

    //categoriesList mutator
    public void setCategoriesList(List<Category> categoriesList) {
        this.categoriesList = categoriesList;
    }

    //service accessor
    public Service getService() {
        return service;
    }

    //service mutator
    public void setService(Service service) {
        this.service = service;
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
