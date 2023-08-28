/*
 * Users.java - Users entity class
 * Stores common details between Staff and Volunteer users
 */
package ejb;

import jakarta.validation.constraints.Pattern;
import java.io.Serializable;
import jakarta.persistence.*;

/**
 * * @author HeimannK
 */
@Entity
@NamedQuery(
    name="findAllUsers",
        query="SELECT * FROM Users")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name="USER_TYPE")
public class Users implements Serializable {
    
    // Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;
    @Column(name="NAME")
    protected String fullName;
    @Column(name="PHONE")
    @Pattern(regexp = "^[0-9]{10}$",
            message = "{invalid.phonenumber}")
    protected String phone;
    @Column(name="EMAIL")
    @Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\."
            + "[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@"
            + "(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9]"
            + "(?:[a-z0-9-]*[a-z0-9])?",
            message = "{invalid.email}")
    protected String email;
    
    // Constructors
    public Users() {
        
    }
    
    public Users(String fullName, String phone, String emailAddress) {
        this.fullName = fullName;
        this.phone = phone;
        this.email = emailAddress;
    }
    
    // Getters & Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getFullName() {
        return fullName;
    }
    
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String emailAddress) {
        this.email = emailAddress;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cquclientbookingsystem.ejb.Person[ id=" + id + " ]";
    }
    
}
