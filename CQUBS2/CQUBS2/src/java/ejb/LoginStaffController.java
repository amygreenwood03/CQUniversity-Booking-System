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
 * This controller fetches input data from the staff login page.
 * String username as email address of an account, String password as raw password String input
 * SHA512 Encryption is used for password hash. Salt data is saved on database as a Hexadecimal String.
 */

@Named(value = "loginController")
@SessionScoped
public class LoginStaffController implements Serializable {
    @EJB
    private UsersEJB usersEJB;
    private final String PAGE_NAME = "Staff Login";
    private String username,password = "";
    public LoginStaffController() {
        
    }
    
    public String login() throws NoSuchAlgorithmException {
        String navResult = "";
        FacesContext ctx = FacesContext.getCurrentInstance();
        
        //Does the user with the email address exist?
        Staff staffAccount = usersEJB.findStaffByEmail(username);

        if (staffAccount == null){
            //No user found, wrong login 
            navResult = "login_staff.faces";
        }
        else {
            //Check if it is Staff account or volunteer account
            String passwordSalt, passwordHashStored = "";
            passwordSalt = staffAccount.getSalt();
            passwordHashStored = staffAccount.getPassword();
            //Import salt data from DB
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            byte[] byteSalt = new byte[passwordSalt.length()/2];
            for(int i=0; i<byteSalt.length; i++) {
                int index = i*2;
                int j = Integer.parseInt(passwordSalt.substring(index, index + 2), 16);
                byteSalt[i] = (byte) j;
            }
            digest.update(byteSalt);
            
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

                ctx.getExternalContext().getSessionMap().put("user", staffAccount);

                navResult = "index.faces";
            }
            else {
                //password doesn't match, return to login page
                username = "";
                password = "";
                navResult = "login_staff.faces";
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