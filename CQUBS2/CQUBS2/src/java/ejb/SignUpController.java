package ejb;

import jakarta.ejb.EJB;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
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
    private UsersEJB usersEJB; //UsersEJB instance
    
    private Volunteer user; //stores volunteer account to be added
    private final String PAGE_NAME = "Volunteer Signup"; //page title
    private String firstName, lastName, phoneNumber, emailAddress, password = ""; //details & credentials entered by user
    
    //default constructor
    public SignUpController() {
        
    }
    
    //returns whether form fields are empty
    public boolean checkFields() {
        if(firstName.isBlank() || lastName.isBlank() || phoneNumber.isBlank() || emailAddress.isBlank() || password.isBlank())
            return true;
        
        return false;
    }
    
    //attempts to create new volunteer account with supplied details
    public String signupVolunteer() throws NoSuchAlgorithmException {
        String navResult = "";
        FacesContext ctx = FacesContext.getCurrentInstance();
        FacesMessage signupError = new FacesMessage("", "One or more fields are blank or filled incorrectly. Please check and try again.");
        
        if(checkFields()) {
            ctx.addMessage("signUpForm", signupError);
            navResult = null;
            return navResult;
        }
        
        // Is the email assigned to existing account?
        try {
            user = usersEJB.findVolByEmail(emailAddress);
        } catch (Exception e) {
            user = null;
        }
        
        // No users found with the address
        if(user == null) {
            //Encrypt raw password with SHA-512 hash and salt
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            SecureRandom random = new SecureRandom();
            String saltString = "";
            
            byte[] salt = new byte[16];
            random.nextBytes(salt);
            md.update(salt);
            byte[] bytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for(int i=0;i<bytes.length;i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            String passwordHash = sb.toString();
            
            // Convert salt bytes to HEX String
            for(byte b : salt) {
                String st = String.format("%02X", b);
                saltString += st;
            }
            
            //Everything filled out?
            if(verifyFirstName(firstName) && verifyLastName(lastName) && !phoneNumber.equals("") && isValidEmailAddress(emailAddress)) {
                user = new Volunteer();
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
                navResult = "index.faces?faces-redirect=true";
            }
            else {
                // Entry problem!
                password = "";
                
                ctx.addMessage("signUpForm", signupError);
                navResult = null;
            }
        }
        // User already exists
        else {
            emailAddress = "";
            password = "";
            
            ctx.addMessage("signUpForm", new FacesMessage("", "This email is already in use by another user."));
            navResult = null;
        }
        return navResult;
    }
    
    // Email check function - Default functionality  provided by Jakarta EE Package
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
    
    // First name verification - Only 1 uppercase in front and then lower case only (example - "Thomas", "Miku")
    public static boolean verifyFirstName(String firstName) {
        return firstName.matches("[A-Z][a-z]*");
    }
    
    // Last name verification - English Alphabets only, no numbers (Multiple uppercase allowed | example - "McKenzie", "McLean", "Pearce", "Hatsune")
    public static boolean verifyLastName(String lastName) {
        return lastName.matches("[A-Z]+([a-zA-Z]+)*");
    }
    
    //PAGE_NAME accessor
    public String getPAGE_NAME() {
        return PAGE_NAME;
    }
    
    //firstName accessor
    public String getFirstName() {
        return firstName;
    }
    
    //firstName mutator
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    //lastName accessor
    public String getLastName() {
        return lastName;
    }
    
    //lastName mutator
    public void setLastName(String lastName){
        this.lastName = lastName;
    }
    
    //phoneNumber accessor
    public String getPhoneNumber(){
        return phoneNumber;
    }
    
    //phoneNumber mutator
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    //emailAddress accessor
    public String getEmailAddress() {
        return emailAddress;
    }
    
    //emailAddress mutator
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
    
    //password accessor
    public String getPassword() {
        return password;
    }
    
    //password mutator
    public void setPassword(String password) {
        this.password = password;
    }
}