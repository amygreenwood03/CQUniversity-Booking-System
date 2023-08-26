/*
 * ServiceAtLocation.java - ServiceAtLocation entity class
 * Stores details of Locations and Services
 */
package ejb;

import java.io.Serializable;
import java.util.ArrayList;
import jakarta.persistence.*;

/**
 * * @author HeimannK
 */
@Entity
public class ServiceAtLocation implements Serializable {

    // Attribues
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="SAL_ID")
    private Long salId;
    @ManyToOne
    @JoinColumn(name="SERV_ID")
    private Service service;
    @ManyToOne
    @JoinColumn(name="LOC_ID")
    private Location location;
    @OneToMany(mappedBy = "sal")
    private ArrayList<Registration> regList;
    
    // Constructors
    public ServiceAtLocation() {
        
    }
    
    public ServiceAtLocation(Service service, Location location) {
        this.service = service;
        this.location = location;
    }
    
    public ServiceAtLocation(Service service, Location location, ArrayList<Registration> regList) {
        this.service = service;
        this.location = location;
        this.regList = regList;
    }

    // Getters & Setters
    public Long getSalId() {
        return salId;
    }

    public void setSalId(Long salId) {
        this.salId = salId;
    }
    
    public Service getService() {
        return service;
    }
    
    public void setService(Service service) {
        this.service = service;
    }
    
    public Location getLocation() {
        return location;
    }
    
    public void setLocation(Location location) {
        this.location = location;
    }
    
    public ArrayList<Registration> getRegList() {
        return regList;
    }
    
    public void setRegList(ArrayList<Registration> regList) {
        this.regList = regList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (salId != null ? salId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the salId fields are not set
        if (!(object instanceof ServiceAtLocation)) {
            return false;
        }
        ServiceAtLocation other = (ServiceAtLocation) object;
        if ((this.salId == null && other.salId != null) || (this.salId != null && !this.salId.equals(other.salId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cquclientbookingsystem.ejb.ServiceAtLocation[ id=" + salId + " ]";
    }
    
}
