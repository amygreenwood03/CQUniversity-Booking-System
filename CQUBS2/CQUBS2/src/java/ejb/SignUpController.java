package ejb;

import jakarta.ejb.EJB;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.MessageDigest;

/**
 * This controller is for making Volunteer account.
 */

@Named(value = "signUpController")
@SessionScoped
public class SignUpController implements Serializable {
    @EJB
    private UsersEJB usersEJB;
    private Volunteer user;
    private final String PAGE_NAME = "Volunteer Signup";
    private String firstName, lastName, phoneNumber, emailAddress, password = ""; 
    
    public SignUpController() {
        
    }
    
    public String signupVolunteer() throws NoSuchAlgorithmException {
        String navResult = "";
        FacesContext ctx = FacesContext.getCurrentInstance();
        
        //Is the email assigned to existing account?
        try {
            user = usersEJB.findVolByEmail(emailAddress);
        } catch (Exception e) {
            user = null;
        }
        
        //No users found with the address
        if(user == null) {
            //Encrypt raw password with hash and salt
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            SecureRandom random = new SecureRandom();
            String saltString = "";
            
            byte[] salt = new byte[16];
            random.nextBytes(salt);
            md.update(salt);
            byte[] bytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for(int i=0;i<bytes.length;i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(i));
            }
            String passwordHash = sb.toString();
            
            //Convert salt bytes to HEX String
            for(byte b : salt) {
                String st = String.format("%02X", b);
                saltString += st;
            }
            
            //Everything filled out?
            if(!firstName.equals("") && !lastName.equals("") && !phoneNumber.equals("") && isValidEmailAddress(emailAddress)) {
                user.setEmail(emailAddress);
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setPhone(phoneNumber);
                user.setPassword(passwordHash);
                user.setSalt(saltString);
                usersEJB.createVolunteer(user);
                ctx.getExternalContext().getSessionMap().put("user", usersEJB.findVolByEmail(emailAddress));
                
                firstName = "";
                lastName = "";
                phoneNumber = "";
                emailAddress = "";
                password = "";
                navResult = "index.faces";
            }
            else {
                navResult = "sign_up.faces";
            }
        }
        //User already exists
        else {
            firstName = "";
            lastName = "";
            phoneNumber = "";
            emailAddress = ""; 
            password = "";
            navResult = "login.faces";
        }
        return navResult;
    }
    
    //Email check function - Default functionality  provided by Jakarta EE Package
    public static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } 
        catch (AddressException ex) {
            result = false;
        }
        return result;
    }
    
    public String getPAGE_NAME() {
        return PAGE_NAME;
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
    
    public void setLastName(String lastName){
        this.lastName = lastName;
    }
    
    public String getPhoneNumber(){
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public String getEmailAddress() {
        return emailAddress;
    }
    
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
}
