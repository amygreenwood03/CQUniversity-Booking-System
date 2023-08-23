/*
 * Location.java - Location entity class
 * Stores details of Locations that offer Services
 */
package cquclientbookingsystem.ejb;

import java.io.Serializable;
import javax.persistence.*;

/**
 * * @author HeimannK
 */
@Entity
public class Location implements Serializable {

    // Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long locationId;
    private String locationName;
    private String locationAddress;
    
    // Constructors
    public Location() {
        
    }
    
    public Location(String locationName, String locationAddress) {
        this.locationName = locationName;
        this.locationAddress = locationAddress;
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

    @Override
    public String toString() {
        return "cquclientbookingsystem.ejb.Location[ id=" + locationId + " ]";
    }
    
}
