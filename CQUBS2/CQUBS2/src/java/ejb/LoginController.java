package ejb;

import jakarta.ejb.EJB;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;

/**
 * This controller fetches input data from the volunteer login page.
 * String username as email address of an account, String password as raw password String input
 * SHA512 Encryption is used for password hash. Salt data is saved on database as a Hexadecimal String.
 * User email and password hash/salt will soon be fetched from users class instead.
 */

@Named(value = "loginController")
@SessionScoped
public class LoginController implements Serializable {
    @EJB
    private UsersEJB usersEJB;
    private final String PAGE_NAME = "Volunteer Login";
    private String username,password = "";
    public LoginController() {
        
    }
    
    public String login() throws NoSuchAlgorithmException {
        String navResult = "";
        FacesContext ctx = FacesContext.getCurrentInstance();
        
        //Does the user with the email address exist?
        Volunteer volunteerAccount = usersEJB.findVolByEmail(username);
        Staff staffAccount = usersEJB.findStaffByEmail(username);
        if(volunteerAccount == null && staffAccount == null) {
            //No user found, wrong login 
            navResult = "login.faces";
        }
        else {
            //Check if it is Staff account or volunteer account
            String passwordSalt, passwordHashStored, userClass = "";
            if(staffAccount != null) {
                userClass = "staff";
                passwordSalt = staffAccount.getSalt();
                passwordHashStored = staffAccount.getPassword();
            }
            else {
                userClass = "volunteer";
                passwordSalt = volunteerAccount.getSalt();
                passwordHashStored = volunteerAccount.getPassword();
            }
            //Import salt data from DB
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            byte[] byteSalt = new byte[passwordSalt.length()/2];
            for(int i=0; i<byteSalt.length; i++) {
                int index = i*2;
                int j = Integer.parseInt(passwordSalt.substring(index, index + 2), 16);
                byteSalt[i] = (byte) j;
            }
            
            //Convert input String to byte array and encrypt with salt
            byte[] pwDigest = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for(int i=0; i<pwDigest.length; i++) {
                sb.append(Integer.toString((pwDigest[i] & 0xff) + 0x100, 16).substring(1));
            }
            String passwordHash = sb.toString();
            
            //Check if password hash matches
            if(passwordHash.equals(passwordHashStored)) {
                username = "";
                password = "";
                if(userClass.equals("staff")) {
                    ctx.getExternalContext().getSessionMap().put("user", staffAccount);
                }
                else {
                    ctx.getExternalContext().getSessionMap().put("user", volunteerAccount);
                }
                navResult = "index.faces";
            }
            else {
                //password doesn't match, return to login page
                username = "";
                password = "";
                navResult = "login.faces";
            }
        }
        return navResult;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPAGE_NAME() {
        return PAGE_NAME;
    }
}
