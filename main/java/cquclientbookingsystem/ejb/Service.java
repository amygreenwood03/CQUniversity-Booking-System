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
    private String categoryName;
    private ArrayList<Volunteer> volunteerList;
    
    // Constructors
    public Service() {
        
    }
    
    public Service(String serviceName, String serviceDescription, Double servicePrice, String categoryName, ArrayList<Volunteer> volunteerList) {
        this.serviceName = serviceName;
        this.serviceDescription = serviceDescription;
        this.servicePrice = servicePrice;
        this.categoryName = categoryName;
        this.volunteerList = volunteerList;
    }
    
    // Getters & Setters
    public Long getId() {
        return serviceId;
    }
    
    public void setID(Long serviceId) {
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
    
    public String getCategoryName() {
        return categoryName;
    }
    
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    
    public ArrayList<Volunteer> getVolunteerList() {
        return volunteerList;
    }
    
    public void setVolunteerList(ArrayList<Volunteer> volunteerList) {
        this.volunteerList = volunteerList;
    }    

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }
}
