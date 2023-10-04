/*
 * Location.java - Location entity class
 * Stores details of Locations that offer Services
 */
package ejb;

import java.io.Serializable;
import java.util.List;
import jakarta.persistence.*;

/**
 * * @author HeimannK
 */
@Entity
@NamedQuery(name="findAllLocations", query="SELECT l FROM Location l")
public class Location implements Serializable {

    // Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="LOC_ID")
    private Long locationId;
    @Column(name="LOC_NAME")
    private String locationName;
    @Column(name="LOC_ADDR")
    private String locationAddress;
    @OneToMany(mappedBy = "location")
    private List<ServiceAtLocation> salList;
    
    // Constructors
    public Location() {
        
    }
    
    public Location(String locationName, String locationAddress) {
        this.locationName = locationName;
        this.locationAddress = locationAddress;
    }
    
    public Location(String locationName, String locationAddress, List<ServiceAtLocation> salList) {
        this.locationName = locationName;
        this.locationAddress = locationAddress;
        this.salList = salList;
    }

    // Getters & Setters
    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }
    
    public String getLocationName() {
        return locationName;
    }
    
    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }
    
    public String getLocationAddress() {
        return locationAddress;
    }
    
    public void setLocationAddress(String locationAddress) {
        this.locationAddress = locationAddress;
    }
    
    public List<ServiceAtLocation> getSalList() {
        return salList;
    }
    
    public void setSalList(List<ServiceAtLocation> salList) {
        this.salList = salList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (locationId != null ? locationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the locationId fields are not set
        if (!(object instanceof Location)) {
            return false;
        }
        Location other = (Location) object;
        if ((this.locationId == null && other.locationId != null) || (this.locationId != null && !this.locationId.equals(other.locationId))) {
            return false;
        }
        return true;
    }

    //returns representation of object as string
    @Override
    public String toString() {
        return "cquclientbookingsystem.ejb.Location[ id=" + locationId + " ]";
    }
    
}
