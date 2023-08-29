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
 *
 * @author Amy
 */

@Named(value = "loginController")
@SessionScoped
public class LoginController implements Serializable
{
    @EJB
    private UsersEJB usersEJB;
    
    @EJB
    private LoginEJB loginEJB;
    
    private final String PAGE_NAME = "Volunteer Login";
    
    private String username,password = "";
    
    public LoginController() 
    {
    }
    
    public String login()
    {
        String navResult = "";
        
        FacesContext ctx = FacesContext.getCurrentInstance();
        
        Login userAccount = loginEJB.findLoginByEmail(username);
        
        if(userAccount == null)
            navResult = "login.faces";
        else
        {
            try
            {
                MessageDigest digest = MessageDigest.getInstance("SHA-512");
                digest.update(userAccount.getSalt().getBytes(StandardCharsets.UTF_8));
                digest.update(password.getBytes(StandardCharsets.UTF_8));
                
                byte[] hash = digest.digest();
                
                String passwordHash = bytesToHex(hash);
                
                if(passwordHash.equals(userAccount.getPassword()))
                {   
                    Users user = usersEJB.findVolByEmail(username);
                    
                    username = "";
                    password = "";
                    
                    ctx.getExternalContext().getSessionMap().put("user", user);
                    
                    navResult = "index.faces";
                }
                else
                {
                    username = "";
                    password = "";
                    navResult = "login.faces";
                }
            }
            catch(NoSuchAlgorithmException e)
            {

            }
        }
        
        return navResult;
    }
    
    private static String bytesToHex(byte[] hash)
    {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        
        for(int i = 0; i < hash.length; i++)
        {
            String hex = Integer.toHexString(0xff & hash[i]);
            
            if(hex.length() == 1)
                hexString.append('0');
            
            hexString.append(hex);
        }
        
        return hexString.toString();
    }

    public String getUsername() 
    {
        return username;
    }

    public void setUsername(String username) 
    {
        this.username = username;
    }

    public String getPassword() 
    {
        return password;
    }

    public void setPassword(String password) 
    {
        this.password = password;
    }

    public String getPAGE_NAME() 
    {
        return PAGE_NAME;
    }
}
