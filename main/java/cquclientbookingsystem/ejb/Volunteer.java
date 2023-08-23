/*
 * Volunteer.java - Volunteer entity class
 * Stores details relating to Volunteer users
 */
package cquclientbookingsystem.ejb;

import java.util.ArrayList;
import javax.persistence.Entity;

/**
 * * @author HeimannK
 */
@Entity
public class Volunteer extends Person {

    // Attributes
    private ArrayList<Service> serviceList;
    
    // Constructors
    public Volunteer() {
        
    }
    
    public Volunteer(String firstName,  String lastName, int phone, String emailAddress, String password, ArrayList<Service> serviceList) {
        super(firstName, lastName, phone, emailAddress, password);
        this.serviceList = serviceList;
    }

   // Getters & Setters
    public ArrayList<Service> getServiceList() {
        return serviceList;
    }
    
    public void setServiceList(ArrayList<Service> serviceList) {
        this.serviceList = serviceList;
    }

    @Override
    public String toString() {
        return "cquclientbookingsystem.ejb.Volunteer[ id=" + id + " ]";
    }
    
}
