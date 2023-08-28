/*
 * Login.java - Login entity class
 * Stores details for User Logins
 */
package ejb;

import java.io.Serializable;
import jakarta.persistence.*;

/**
 * * @author HeimannK
 */
@Entity
@NamedQuery(
    name="findAllLogins",
        query="SELECT * FROM Login")
public class Login implements Serializable {

    // Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="LOGIN_ID")
    private Long loginId;
    @OneToOne
    @JoinColumns({
        @JoinColumn(name="EMAIL"),
        @JoinColumn(name="USER_TYPE")
    })
    private Users user;
    @Column(name="PASSWORD")
    protected String password;
    @Column(name="SALT")
    private String salt;

    // Constructors
    public Login() {
        
    }
    
    public Login(Users user, String password, String salt){
        this.user = user;
        this.salt = salt;
        this.password = password;
    }
    
    // Getters & Setters
    public Long getLoginId() {
        return loginId;
    }

    public void setLoginId(Long loginId) {
        this.loginId = loginId;
    }
    
     public Users getUser() {
         return user;
     }
     
     public void setUser(Users user) {
         this.user = user;
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
        hash += (loginId != null ? loginId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the loginId fields are not set
        if (!(object instanceof Login)) {
            return false;
        }
        Login other = (Login) object;
        if ((this.loginId == null && other.loginId != null) || (this.loginId != null && !this.loginId.equals(other.loginId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cquclientbookingsystem.ejb.Login[ id=" + loginId + " ]";
    }
    
}
