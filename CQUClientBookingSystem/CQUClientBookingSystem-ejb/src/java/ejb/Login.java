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
public class Login implements Serializable {

    // Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long loginId;
    @OneToOne
    @JoinColumns({
        @JoinColumn(name="EMAIL"),
        @JoinColumn(name="PASSWORD"),
        @JoinColumn(name="USER_TYPE")
    })
    private Users user;
    private String salt;

    // Constructors
    public Login() {
        
    }
    
    public Login(Users user, String salt){
        this.user = user;
        this.salt = salt;
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
