/*
 * Service.java - Service entity class
 * Stores details of Services offered
 */
package ejb;

import java.io.Serializable;
import java.util.ArrayList;
import jakarta.persistence.*;

/**
 * * @author HeimannK
 */
@Entity
public class Service implements Serializable {

   // Attributes
    @Id
    @GeneratedValue
    private Long serviceId;
    private String serviceName;
    private String serviceDescription;
    private Double servicePrice;
    private String imageUrl;
    @ManyToOne
    @JoinColumn(name="CAT_ID")
    private Category category;
    @OneToMany(mappedBy = "service")
    private ArrayList<ServiceAtLocation> salList;
    
    // Constructors
    public Service() {
        
    }
    
    public Service(String serviceName, String serviceDescription, Double servicePrice, String imageUrl, Category category) {
        this.serviceName = serviceName;
        this.serviceDescription = serviceDescription;
        this.servicePrice = servicePrice;
        this.imageUrl = imageUrl;
        this.category = category;
    }
    
    // Getters & Setters
    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }
    
    public String getServiceName() {
        return serviceName;
    }
    
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
    
    public String getServiceDescription() {
        return serviceDescription;
    }
    
    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }
    
    public Double getServicePrice(){
        return servicePrice;
    }
    
    public void setServicePrice(Double servicePrice) {
        this.servicePrice = servicePrice;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }
    
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
   public Category getCategory() {
       return category;
   }
   
   public void setCategory(Category category) {
       this.category = category;
   }
    
    public ArrayList<ServiceAtLocation> getSalList() {
        return salList;
    }
    
    public void setSalList(ArrayList<ServiceAtLocation> salList) {
        this.salList = salList;
    }
}
