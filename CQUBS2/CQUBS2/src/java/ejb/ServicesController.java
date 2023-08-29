package ejb;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Amy
 */

@Named(value = "servicesController")
@SessionScoped
public class ServicesController implements Serializable
{
    @EJB
    private LocationEJB locationEJB;
    
    @EJB
    private CategoryEJB categoryEJB;
    
    @EJB
    private ServiceEJB serviceEJB;
    
    @EJB
    private ServiceAtLocationEJB salEJB;
    
    private List<Location> locationsList, selectedLocationsList = new ArrayList<>(); //to store all locations & locations selected in filter respectively
    private List<Category> categoriesList, selectedCategoriesList = new ArrayList<>(); //to store all categories & categories selected in filter respectively
    private List<Service> servicesList, selectedServicesList = new ArrayList<>(); //to store all services & services selected in filter respectively
    private List<ServiceAtLocation> salList = new ArrayList<>(); //to store search results since we want them split by location
    
    private Long fromHomeId;
    
    private final String PAGE_NAME = "Services Search";
    
    public ServicesController() 
    {
    }
    
    public void init()
    {
        locationsList = locationEJB.findLocations();
        categoriesList = categoryEJB.findCategories();
        servicesList = serviceEJB.findServices();
        salList = salEJB.findSALs();
        //refreshSalList();
        
        if(fromHomeId != null)
        {
            for(int i = 0; i < categoriesList.size(); i++)
            {
                if(categoriesList.get(i).getCat_id() == fromHomeId)
                    selectedCategoriesList.add(categoriesList.get(i));
            }
            
            search();
        }
    }
    
    public String renderPrice(double price)
    {
        String priceAsString = "";
        
        if(price > 0.0)
            priceAsString = "$" + price;
        else
            priceAsString = "FREE";
        
        return priceAsString;
    }
    
    public void search()
    {
        salList = salEJB.findSALs();
        
        List<ServiceAtLocation> results = new ArrayList<>();
        
        if(!selectedLocationsList.isEmpty() || !selectedCategoriesList.isEmpty() || !selectedServicesList.isEmpty())
        {
            if(!selectedLocationsList.isEmpty())
            {
                for(int i = 0; i < selectedLocationsList.size(); i++)
                    results.addAll(salEJB.findSALsByLocation(selectedLocationsList.get(i)));
            }
            
            if(!selectedCategoriesList.isEmpty())
            {   
                if(results.isEmpty())
                {
                    for(int i = 0; i < selectedCategoriesList.size(); i++)
                        results.addAll(salEJB.findSALsByCategory(selectedCategoriesList.get(i)));
                }
                else
                {
                    List<ServiceAtLocation> sals = new ArrayList<>();
                    List<ServiceAtLocation> list = new ArrayList<>();
                    
                    for(int i = 0; i < selectedCategoriesList.size(); i++)
                        sals.addAll(salEJB.findSALsByCategory(selectedCategoriesList.get(i)));
                    
                    for(int i = 0; i < results.size(); i++)
                    {
                        for(int j = 0; j < sals.size(); j++)
                        {
                            if(results.get(i).getSalId() == sals.get(j).getSalId())
                            {
                                list.add(results.get(i));
                                break;
                            }
                        }
                    }
                    
                    results = list;
                }
            }
            
            if(!selectedServicesList.isEmpty())
            {
                if(results.isEmpty())
                {
                    for(int i = 0; i < selectedServicesList.size(); i++)
                        results.addAll(salEJB.findSALsByService(selectedServicesList.get(i)));
                }
                else
                {
                    List<ServiceAtLocation> sals = new ArrayList<>();
                    List<ServiceAtLocation> list = new ArrayList<>();
                    
                    for(int i = 0; i < selectedServicesList.size(); i++)
                        sals.addAll(salEJB.findSALsByService(selectedServicesList.get(i)));
                    
                    for(int i = 0; i < results.size(); i++)
                    {
                        for(int j = 0; j < sals.size(); j++)
                        {
                            if(results.get(i).getSalId() == sals.get(j).getSalId())
                            {
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
    
    /*public void refreshSalList()
    {
        for(int i = 0; i < servicesList.size(); i++)
        {
            salList.addAll(servicesList.get(i).getSalList());
            for(int j = 0; j < servicesList.get(i).getSalList().size(); j++)
                salList.add(servicesList.get(i).getSalList().get(j));
        }
    }*/

    public List<Location> getLocationsList() 
    {
        return locationsList;
    }

    public void setLocationsList(List<Location> locationsList) 
    {
        this.locationsList = locationsList;
    }

    public List<Location> getSelectedLocationsList() 
    {
        return selectedLocationsList;
    }

    public void setSelectedLocationsList(List<Location> selectedLocationsList) 
    {
        this.selectedLocationsList = selectedLocationsList;
    }

    public List<Category> getCategoriesList() 
    {
        return categoriesList;
    }

    public void setCategoriesList(List<Category> categoriesList) 
    {
        this.categoriesList = categoriesList;
    }

    public List<Category> getSelectedCategoriesList() 
    {
        return selectedCategoriesList;
    }

    public void setSelectedCategoriesList(List<Category> selectedCategoriesList) 
    {
        this.selectedCategoriesList = selectedCategoriesList;
    }

    public List<Service> getServicesList() 
    {
        return servicesList;
    }

    public void setServicesList(List<Service> servicesList) 
    {
        this.servicesList = servicesList;
    }

    public List<Service> getSelectedServicesList() 
    {
        return selectedServicesList;
    }

    public void setSelectedServicesList(List<Service> selectedServicesList) 
    {
        this.selectedServicesList = selectedServicesList;
    }

    public List<ServiceAtLocation> getSalList() 
    {
        return salList;
    }

    public void setSalList(List<ServiceAtLocation> salList) 
    {
        this.salList = salList;
    }

    public String getPAGE_NAME() 
    {
        return PAGE_NAME;
    } 

    public Long getFromHomeId() 
    {
        return fromHomeId;
    }

    public void setFromHomeId(Long fromHomeId) 
    {
        this.fromHomeId = fromHomeId;
    }
}
