package ejb;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for Guest/User side service search page
 */

@Named(value = "servicesController")
@SessionScoped
public class ServicesController implements Serializable {
    @EJB
    private LocationEJB locationEJB; //LocationEJB instance
    
    @EJB
    private CategoryEJB categoryEJB; //CategoryEJB instance
    
    @EJB
    private ServiceEJB serviceEJB; //ServiceEJB instance
    
    @EJB
    private ServiceAtLocationEJB salEJB; //ServiceAtLocationEJB instance
    
    private List<Location> locationsList, selectedLocationsList = new ArrayList<>(); //stores all locations & locations selected in filter respectively
    private List<Category> categoriesList, selectedCategoriesList = new ArrayList<>(); //stores all categories & categories selected in filter respectively
    private List<Service> servicesList, selectedServicesList = new ArrayList<>(); //stores all services & services selected in filter respectively
    private List<ServiceAtLocation> salList = new ArrayList<>(); //stores search results
    private static final DecimalFormat df = new DecimalFormat("0.00"); //decimal formatting for prices
    private Long fromHomeId; //stores category id passed from home page
    
    private final String PAGE_NAME = "Services Search"; //page title
    
    //default constructor
    public ServicesController() {
        
    }
    
    //initialises page upon load
    public void init() {
        locationsList = locationEJB.findLocations();
        categoriesList = categoryEJB.findCategories();
        servicesList = serviceEJB.findServices();
        salList = salEJB.findSALs();
        
        selectedCategoriesList.clear();
        
        if(fromHomeId != null) {
            for(int i = 0; i < categoriesList.size(); i++) {
                if(categoriesList.get(i).getCat_id() == fromHomeId)
                    selectedCategoriesList.add(categoriesList.get(i));
            }
            
            fromHomeId = null;
            search();
        }
    }
    
    //returns representation of price as string
    public String renderPrice(double price) {
        String priceAsString = "";
        if(price > 0.0)
            priceAsString = "$" + df.format(price);
        else
            priceAsString = "FREE";
        
        return priceAsString;
    }
    
    //searches database for sals based upon selected filters if applicable
    public void search() {
        salList = salEJB.findSALs();
        List<ServiceAtLocation> results = new ArrayList<>();
        
        //if any filters have been applied
        if(!selectedLocationsList.isEmpty() || !selectedCategoriesList.isEmpty() || !selectedServicesList.isEmpty()) {
            //if location filters applied
            if(!selectedLocationsList.isEmpty()) {
                for(int i = 0; i < selectedLocationsList.size(); i++)
                    results.addAll(salEJB.findSALsByLocation(selectedLocationsList.get(i)));
            }
            
            //if category filters applied
            if(!selectedCategoriesList.isEmpty()) {   
                if(results.isEmpty()) {
                    for(int i = 0; i < selectedCategoriesList.size(); i++)
                        results.addAll(salEJB.findSALsByCategory(selectedCategoriesList.get(i)));
                }
                else {
                    List<ServiceAtLocation> sals = new ArrayList<>();
                    List<ServiceAtLocation> list = new ArrayList<>();
                    
                    for(int i = 0; i < selectedCategoriesList.size(); i++)
                        sals.addAll(salEJB.findSALsByCategory(selectedCategoriesList.get(i)));
                    
                    for(int i = 0; i < results.size(); i++) {
                        for(int j = 0; j < sals.size(); j++) {
                            if(results.get(i).getSalId() == sals.get(j).getSalId()) {
                                list.add(results.get(i));
                                break;
                            }
                        }
                    }
                    
                    results = list;
                }
            }
            
            //if service type filters applied
            if(!selectedServicesList.isEmpty()) {
                if(results.isEmpty()) {
                    for(int i = 0; i < selectedServicesList.size(); i++)
                        results.addAll(salEJB.findSALsByService(selectedServicesList.get(i)));
                }
                else {
                    List<ServiceAtLocation> sals = new ArrayList<>();
                    List<ServiceAtLocation> list = new ArrayList<>();
                    
                    for(int i = 0; i < selectedServicesList.size(); i++)
                        sals.addAll(salEJB.findSALsByService(selectedServicesList.get(i)));
                    
                    for(int i = 0; i < results.size(); i++) {
                        for(int j = 0; j < sals.size(); j++) {
                            if(results.get(i).getSalId() == sals.get(j).getSalId()) {
                                list.add(results.get(i));
                                break;
                            }
                        }
                    }
                    
                    results = list;
                }
            }
            
            salList = results;
        }
    }
    
    //locationsList accessor
    public List<Location> getLocationsList() {
        return locationsList;
    }

    //locationsList mutator
    public void setLocationsList(List<Location> locationsList) {
        this.locationsList = locationsList;
    }

    //selectedLocationsList accessor
    public List<Location> getSelectedLocationsList() 
    {
        return selectedLocationsList;
    }

    //selectedLocationsList mutator
    public void setSelectedLocationsList(List<Location> selectedLocationsList) 
    {
        this.selectedLocationsList = selectedLocationsList;
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

    //selectedCategoriesList accessor
    public List<Category> getSelectedCategoriesList() 
    {
        return selectedCategoriesList;
    }

    //selectedCategoriesList mutator
    public void setSelectedCategoriesList(List<Category> selectedCategoriesList) 
    {
        this.selectedCategoriesList = selectedCategoriesList;
    }

    //servicesList accessor
    public List<Service> getServicesList() 
    {
        return servicesList;
    }

    //servicesList mutator
    public void setServicesList(List<Service> servicesList) 
    {
        this.servicesList = servicesList;
    }

    //selectedServicesList accessor
    public List<Service> getSelectedServicesList() 
    {
        return selectedServicesList;
    }

    //selectedServicesList mutator
    public void setSelectedServicesList(List<Service> selectedServicesList) 
    {
        this.selectedServicesList = selectedServicesList;
    }

    //salList accessor
    public List<ServiceAtLocation> getSalList() 
    {
        return salList;
    }

    //salList mutator
    public void setSalList(List<ServiceAtLocation> salList) 
    {
        this.salList = salList;
    }

    //PAGE_NAME accessor
    public String getPAGE_NAME() 
    {
        return PAGE_NAME;
    } 

    //fromHomeId accessor
    public Long getFromHomeId() 
    {
        return fromHomeId;
    }

    //fromHomeId mutator
    public void setFromHomeId(Long fromHomeId) 
    {
        this.fromHomeId = fromHomeId;
    }
}
