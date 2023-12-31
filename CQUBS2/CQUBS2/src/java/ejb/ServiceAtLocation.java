/*
 * ServiceAtLocation.java - ServiceAtLocation entity class
 * Stores details of Locations and Services
 */
package ejb;

import java.io.Serializable;
import java.util.List;
import jakarta.persistence.*;

/**
 * * @author HeimannK
 */
@Entity
@NamedQuery(name="findAllSALs",query="SELECT s FROM ServiceAtLocation s")
@NamedQuery(name = "findSALsByLocation", query = "SELECT s FROM ServiceAtLocation s WHERE s.location.locationId = :lid")
@NamedQuery(name = "findSALsByCategory", query = "SELECT s FROM ServiceAtLocation s WHERE s.service.category.cat_id = :cid")
@NamedQuery(name = "findSALsByService", query = "SELECT s FROM ServiceAtLocation s WHERE s.service.serviceId = :sid")
@NamedQuery(name = "findSALsByDepartment", query = "SELECT s FROM ServiceAtLocation s WHERE s.service.category.dept.departmentId = :did")
@Table(name = "services_at_location")
public class ServiceAtLocation implements Serializable {
    // Attribues
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="SAL_ID")
    private Long salId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SERV_ID")
    private Service service;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "LOC_ID")
    private Location location;
    @OneToMany(mappedBy = "sal")
    private List<Registration> regList;
    
    // Constructors
    public ServiceAtLocation() {
        
    }
    
    public ServiceAtLocation(Service service, Location location) {
        this.service = service;
        this.location = location;
    }
    
    public ServiceAtLocation(Service service, Location location, List<Registration> regList) {
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
    
    public List<Registration> getRegList() {
        return regList;
    }
    
    public void setRegList(List<Registration> regList) {
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

    //returns representation of object as string
    @Override
    public String toString() {
        return "cquclientbookingsystem.ejb.ServiceAtLocation[ id=" + salId + " ]";
    }
    
}
