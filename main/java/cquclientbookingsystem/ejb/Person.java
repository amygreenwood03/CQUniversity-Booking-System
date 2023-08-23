/*
 * Person.java - Person entity class
 * Stores common details between Staff and Volunteer users
 */
package cquclientbookingsystem.ejb;

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
    protected Long id;
    protected String firstName;
    protected String lastName;
    protected String fullName;
    protected int phone;
    protected String emailAddress;
    protected String password;
    
    // Constructors
    public Person() {
        
    }
    
    public Person(String firstName,  String lastName, int phone, String emailAddress, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = firstName + " " + lastName;
        this.phone = phone;
        this.emailAddress = emailAddress;
        this.password = password;
    }
    
    // Getters & Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getFullName() {
        return fullName;
    }
    
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public String getEmail() {
        return emailAddress;
    }
    
    public void setEmail(String emailAddress) {
        this.emailAddress = emailAddress;
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
