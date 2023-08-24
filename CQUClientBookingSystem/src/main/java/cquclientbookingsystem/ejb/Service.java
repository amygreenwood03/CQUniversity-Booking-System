/*
 * Service.java - Service entity class
 * Stores details of Services offered
 */
package cquclientbookingsystem.ejb;

import java.io.Serializable;
import java.util.ArrayList;
import javax.persistence.*;

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
    @ManyToOne
    @JoinColumn(name="CAT_ID")
    private Category category;
    @OneToMany(mappedBy = "service")
    private ArrayList<ServiceAtLocation> salList;
    private ArrayList<Volunteer> volunteerList;
    
    // Constructors
    public Service() {
        
    }
    
    public Service(String serviceName, String serviceDescription, Double servicePrice, Category category, ArrayList<ServiceAtLocation> salList, ArrayList<Volunteer> volunteerList) {
        this.serviceName = serviceName;
        this.serviceDescription = serviceDescription;
        this.servicePrice = servicePrice;
        this.category = category;
        this.salList = salList;
        this.volunteerList = volunteerList;
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
    
    public ArrayList<Volunteer> getVolunteerList() {
        return volunteerList;
    }
    
    public void setVolunteerList(ArrayList<Volunteer> volunteerList) {
        this.volunteerList = volunteerList;
    } 
}
