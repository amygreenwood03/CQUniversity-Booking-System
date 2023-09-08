package ejb;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * This class controls Service Details page for users and guests.
 * Page "service_details_staff.xhtml"
 */

@Named(value = "detailsStaffController")
@SessionScoped
public class DetailsStaffController implements Serializable {
    @EJB
    private ServiceAtLocationEJB salEJB;
    
    @EJB
    private RegistrationEJB regEJB;
    
    @EJB
    private ServiceEJB serviceEJB;
    
    @EJB
    private LocationEJB locationEJB;
    
    @EJB
    private CategoryEJB categoryEJB;
    
    private ServiceAtLocation sal = new ServiceAtLocation();
    private Long salId = 0L;
    private String pageName = "";
    private static final DecimalFormat df = new DecimalFormat("0.00");
    
    private Service service = new Service();
    
    private List<Location> selectedLocationsList = new ArrayList<>();
    private List<Location> locationsList = new ArrayList<>();
    
    private Long selectedCategoryId = 0L;
    private List<Category> categoriesList = new ArrayList<>();
    
    private String selectedPrice, priceString;
    
    public DetailsStaffController() {
        
    }
    
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
    
    public void editInit(Staff user)
    {
        FacesContext ctx = FacesContext.getCurrentInstance();
        
        if(salId > 0L)
        {
            service = sal.getService();
            
            locationsList = locationEJB.findLocations();
            
            for(int i = 0; i < locationsList.size(); i++)
            {
                for(int j = 0; j < service.getSalList().size(); j++)
                {
                    if(locationsList.get(i).getLocationId() == service.getSalList().get(j).getLocation().getLocationId())
                    {
                        locationsList.remove(i);
                        break;
                    }
                }
            }
            
            categoriesList = categoryEJB.findCategoriesByDepartment(user.getDepartment());
            selectedCategoryId = service.getCategory().getCat_id();
            
            if(service.getServicePrice() > 0)
            {
                selectedPrice = "Charge";
                priceString = df.format(service.getServicePrice());
            }
            else
                selectedPrice = "Free";
        }
        else
        {
            try {
                ctx.getExternalContext().redirect("service_details_staff.faces");
            }
            catch(IOException e) {
                
            }
        }
    }
    
    public String renderPrice(double price) {
        String priceAsString = "";
        if(price > 0.0)
            priceAsString = "$" + df.format(price);
        else
            priceAsString = "FREE";
        
        return priceAsString;
    }
    
    public void serviceEdit() {
        if(selectedCategoryId != service.getCategory().getCat_id())
            service.setCategory(categoryEJB.findCategoryById(selectedCategoryId));
        
        if(selectedPrice.equals("Free"))
        {
            if(service.getServicePrice() > 0.0)
                service.setServicePrice(0.0);
        }
        else
        {
            service.setServicePrice(Double.parseDouble(priceString));
        }
        
        if(!selectedLocationsList.isEmpty())
        {
            List<ServiceAtLocation> sals = new ArrayList<>();
            
            for(int i = 0; i < selectedLocationsList.size(); i++)
            {
                sals.add(new ServiceAtLocation(service, selectedLocationsList.get(i)));
            }
            
            service.getSalList().addAll(sals);
        }
        
        serviceEJB.updateService(service);
        
        FacesContext ctx = FacesContext.getCurrentInstance();
        
        try {
            ctx.getExternalContext().redirect("service_details_staff.faces?salId=" + sal.getSalId());
        }
        catch(IOException e) {
                
        }
    }
    
    public void serviceDelete() {
        if(sal.getRegList() != null && !sal.getRegList().isEmpty())
        {
            for(int i = 0; i < sal.getRegList().size(); i++)
                regEJB.deleteRegistration(sal.getRegList().get(i));
        }
        
        List<ServiceAtLocation> sals = salEJB.findSALsByService(sal.getService());
        
        if(sals.size() == 1)
        {
            salEJB.deleteSAL(sal);
            serviceEJB.deleteService(sal.getService());
        }
        else
            salEJB.deleteSAL(sal);
        
        FacesContext ctx = FacesContext.getCurrentInstance();
        
        try
        {
            ctx.getExternalContext().redirect("services_staff.faces");
        }
        catch(IOException e)
        {
            
        }
    }

    public ServiceAtLocation getSal() {
        return sal;
    }

    public void setSal(ServiceAtLocation sal) {
        this.sal = sal;
    }

    public Long getSalId() {
        return salId;
    }

    public void setSalId(Long salId) {
        this.salId = salId;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public Service getService() 
    {
        return service;
    }

    public void setService(Service service) 
    {
        this.service = service;
    }

    public List<Location> getSelectedLocationsList() 
    {
        return selectedLocationsList;
    }

    public void setSelectedLocationsList(List<Location> selectedLocationsList) 
    {
        this.selectedLocationsList = selectedLocationsList;
    }

    public List<Location> getLocationsList() 
    {
        return locationsList;
    }

    public void setLocationsList(List<Location> locationsList) 
    {
        this.locationsList = locationsList;
    }

    public Long getSelectedCategoryId() 
    {
        return selectedCategoryId;
    }

    public void setSelectedCategoryId(Long selectedCategoryId) 
    {
        this.selectedCategoryId = selectedCategoryId;
    }

    public List<Category> getCategoriesList()
    {
        return categoriesList;
    }

    public void setCategoriesList(List<Category> categoriesList) 
    {
        this.categoriesList = categoriesList;
    }

    public String getSelectedPrice()
    {
        return selectedPrice;
    }

    public void setSelectedPrice(String selectedPrice) 
    {
        this.selectedPrice = selectedPrice;
    }

    public String getPriceString() 
    {
        return priceString;
    }

    public void setPriceString(String priceString) 
    {
        this.priceString = priceString;
    }
}
