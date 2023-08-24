/*
 * Person.java - Person entity class
 * Stores common details between Staff and Volunteer users
 */
package cquclientbookingsystem.ejb;

import jakarta.validation.constraints.Pattern;
import java.io.Serializable;
import javax.persistence.*;

/**
 * * @author HeimannK
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Person implements Serializable {
    
    // Attributes
    @Id
    @GeneratedValue
    @Column(name = "PERSON_ID")
    protected Long id;
    protected String fullName;
    @Pattern(regexp = "^[0-9]{10}$",
            message = "{invalid.phonenumber}")
    protected String phone;
    @Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\."
            + "[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@"
            + "(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9]"
            + "(?:[a-z0-9-]*[a-z0-9])?",
            message = "{invalid.email}")
    protected String email;
    protected String password;
    
    // Constructors
    public Person() {
        
    }
    
    public Person(String fullName, String phone, String emailAddress, String password) {
        this.fullName = fullName;
        this.phone = phone;
        this.email = emailAddress;
        this.password = password;
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
        if (!(object instanceof Person)) {
            return false;
        }
        Person other = (Person) object;
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
