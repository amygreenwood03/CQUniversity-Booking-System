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
    name="findAllUsers", query="SELECT u FROM Users u")
@NamedQuery(
    name="findVolByEmail", query="SELECT v FROM Volunteer v WHERE v.email = :email")
@NamedQuery(
    name="findStaffByEmail", query="SELECT s FROM Staff s WHERE s.email = :email")
@Table(name = "User")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name="USER_TYPE")
public class Users implements Serializable {
    // Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="USER_ID")
    protected Long id;
    @Column(name="FIRST_NAME")
    protected String firstName;
    @Column(name="LAST_NAME")
    protected String lastName;
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
    @Column(name="PASSWORD")
    protected String password;
    @Column(name="SALT")
    protected String salt;
    @Column(name="USER_TYPE") protected String usertype;
    
    // Constructors
    public Users() {
        
    }
    
    public Users(String firstName, String lastName, String phone, String email, String password, String salt) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.salt = salt;
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
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getSalt() {
        return salt;
    }
    
    public void setSalt(String salt) {
        this.salt = salt;
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
