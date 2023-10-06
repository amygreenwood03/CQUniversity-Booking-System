package ejb;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * This class controls Service Details page for staff.
 * Page "service_details_staff.xhtml"
 */

@Named(value = "detailsStaffController")
@SessionScoped
public class DetailsStaffController implements Serializable {
    @EJB
    private ServiceAtLocationEJB salEJB; //ServiceAtLocationEJB instance
    
    @EJB
    private RegistrationEJB regEJB; //RegistrationEJB instance
    
    @EJB
    private ServiceEJB serviceEJB; //ServiceEJB instance
    
    @EJB
    private LocationEJB locationEJB; //LocationEJB instance
    
    @EJB
    private CategoryEJB categoryEJB; //CategoryEJB instance
    
    private ServiceAtLocation sal = new ServiceAtLocation(); //stores current sal
    private Long salId = 0L; //stores sal id passed from previous view
    private String pageName = ""; //page title
    private static final DecimalFormat df = new DecimalFormat("0.00"); //decimal formatting for prices
    
    private Service service = new Service(); //stores service to be edited
    
    private List<Location> selectedLocationsList = new ArrayList<>(); //stores locations selected by user
    private List<Location> locationsList = new ArrayList<>(); //stores all locations in database
    
    private Long selectedCategoryId = 0L; //stores id of selected category
    private List<Category> categoriesList = new ArrayList<>(); //stores all categories in database
    
    private String 
        selectedPrice, //price selected by user (ie. free or charge)
        priceString, //cost entered by user if applicable
        imageUrl, //URL of image in string format for upload to database
        serviceName, //edited service name
        serviceDescription; //edited service description
    
    private Part promoImg; //stores image & related data uploaded through form
    private File savedImg; //stores image file to be written
    
    //default constructor
    public DetailsStaffController() {
        
    }
    
    //initialises page upon load
    public void init() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        
        if(salId > 0L) {
            sal = salEJB.findSALById(salId);
            pageName = sal.getService().getServiceName();
        }
        else {
            try {
                ctx.getExternalContext().redirect("services_staff.faces");
            }
            catch(IOException e) {
                
            }
        }
    }
    
    //initialises page for editing upon load
    public void editInit(Staff user) {
        FacesContext ctx = FacesContext.getCurrentInstance();
        
        if(salId > 0L) {
            service = sal.getService();
            
            serviceName = service.getServiceName();
            serviceDescription = service.getServiceDescription();
            
            locationsList = locationEJB.findLocations();
            
            for(int i = 0; i < locationsList.size(); i++) {
                for(int j = 0; j < service.getSalList().size(); j++){
                    if(locationsList.get(i).getLocationId() == service.getSalList().get(j).getLocation().getLocationId()) {
                        locationsList.remove(i);
                        break;
                    }
                }
            }
            
            categoriesList = categoryEJB.findCategoriesByDepartment(user.getDepartment());
            selectedCategoryId = service.getCategory().getCat_id();
            
            if(service.getServicePrice() > 0) {
                selectedPrice = "Charge";
                priceString = df.format(service.getServicePrice());
            }
            else {
                selectedPrice = "Free";
                priceString = "";
            }
        }
        else {
            try {
                ctx.getExternalContext().redirect("services_staff.faces");
            }
            catch(IOException e) {
                
            }
        }
    }
    
    //returns representation of price as a string
    public String renderPrice(double price) {
        String priceAsString = "";
        if(price > 0.0)
            priceAsString = "$" + df.format(price);
        else
            priceAsString = "FREE";
        
        return priceAsString;
    }
    
    //returns all registrations for a sal
    public List<Registration> getRegList(ServiceAtLocation servAtLocation) {
        List<Registration> regList = regEJB.findRegistrationsBySAL(servAtLocation);
        return regList;
    }
    
    //edits existing service
    public String serviceEdit() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        FacesMessage editError = new FacesMessage("", "Please fill out all fields.");
        
        if(serviceName.isBlank() || serviceDescription.isBlank())
        {
            ctx.addMessage("editForm", editError);
            return null;
        }
        
        service.setServiceName(serviceName);
        service.setServiceDescription(serviceDescription);
        
        if(promoImg != null) {
            uploadImg();
            service.setImageUrl(imageUrl);
        }
        
        if(selectedCategoryId != service.getCategory().getCat_id())
            service.setCategory(categoryEJB.findCategoryById(selectedCategoryId));
        
        if(selectedPrice.equals("Free")) {
            if(service.getServicePrice() > 0.0)
                service.setServicePrice(0.0);
        }
        else {
            if(priceString.isBlank())
            {
                ctx.addMessage("editForm", editError);
                return null;
            }
            else
            {
                try
                {
                    service.setServicePrice(Double.parseDouble(priceString));
                }
                catch(NumberFormatException e)
                {
                    ctx.addMessage("editForm", new FacesMessage("", "The specified service price must be a number."));
                    return null;
                }
            }
        }
        
        if(!selectedLocationsList.isEmpty()) {
            List<ServiceAtLocation> sals = new ArrayList<>();
            
            for(int i = 0; i < selectedLocationsList.size(); i++) {
                sals.add(new ServiceAtLocation(service, selectedLocationsList.get(i)));
            }
            
            service.getSalList().addAll(sals);
        }
        
        serviceEJB.updateService(service);
        
        return "service_details_staff.faces?faces-redirect=true";
    }
    
    //deletes existing sal (& service if no sals remain)
    public String serviceDelete() {
        List<Registration> regList = getRegList(sal);
        
        if(regList != null && !regList.isEmpty()) {
            for(int i = 0; i < regList.size(); i++)
                regEJB.deleteRegistration(regList.get(i));
        }
        
        List<ServiceAtLocation> sals = salEJB.findSALsByService(sal.getService());
        
        if(sals.size() == 1)
        {
            salEJB.deleteSAL(sal);
            serviceEJB.deleteService(sal.getService());
        }
        else
            salEJB.deleteSAL(sal);
        
        return "services_staff.faces?faces-redirect=true";
    }
    
    //saves uploaded image to dedicated images directory on server
    public void uploadImg() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        
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

    //sal accessor
    public ServiceAtLocation getSal() {
        return sal;
    }

    //sal mutator
    public void setSal(ServiceAtLocation sal) {
        this.sal = sal;
    }

    //salId accessor
    public Long getSalId() {
        return salId;
    }

    //salId mutator
    public void setSalId(Long salId) {
        this.salId = salId;
    }

    //pageName accessor
    public String getPageName() {
        return pageName;
    }

    //pageName mutator
    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    //service accessor
    public Service getService() {
        return service;
    }

    //service mutator
    public void setService(Service service) {
        this.service = service;
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
    public void setSelectedCategoryId(Long selectedCategoryId) 
    {
        this.selectedCategoryId = selectedCategoryId;
    }

    //categoriesList accessor
    public List<Category> getCategoriesList()
    {
        return categoriesList;
    }

    //categoriesList mutator
    public void setCategoriesList(List<Category> categoriesList) 
    {
        this.categoriesList = categoriesList;
    }

    //selectedPrice accessor
    public String getSelectedPrice()
    {
        return selectedPrice;
    }

    //selectedPrice mutator
    public void setSelectedPrice(String selectedPrice) 
    {
        this.selectedPrice = selectedPrice;
    }

    //priceString accessor
    public String getPriceString() 
    {
        return priceString;
    }

    //priceString mutator
    public void setPriceString(String priceString) 
    {
        this.priceString = priceString;
    }
    
    //promoImg accessor
    public Part getPromoImg() 
    {
        return promoImg;
    }

    //promoImg mutator
    public void setPromoImg(Part promoImg) 
    {
        this.promoImg = promoImg;
    }

    //serviceName accessor
    public String getServiceName() {
        return serviceName;
    }

    //serviceName mutator
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    //serviceDescription accessor
    public String getServiceDescription() {
        return serviceDescription;
    }

    //serviceDescription mutator
    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }
}
