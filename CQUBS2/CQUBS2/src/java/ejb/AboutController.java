package ejb;

import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 * This controller controls "About Us" page.
 * Change the contents of the strings of this controller to change the contents of About Us page.
 */

@Named(value = "aboutController")
@SessionScoped
public class AboutController implements Serializable {
    //page title
    private final String PAGE_NAME = "About Us";
    //section 1 heading & content
    private final String HEADING_1 = "CQUniversity Public Services";
    private final String CONTENT_1 = "Central Queensland University (CQUniversity) is a public university based in Queensland, Australia. "
            + "With our students, CQUniversity provides a variety of public services at campuses in multiple locations across Australia, "
            + "including Rockhampton, Brisbane, Cairns, Emerald, Gladstone, Mackay, Melbourne, Noosa, Perth, Sydney and Townsville. "
            + "Anyone can participate in the services provided by CQUniversity at the campuses in your regions by registering via this website. ";
    
    //section 2 heading & content
    private final String HEADING_2 = "NEED HELP?";
    private final String CONTENT_2 = "If you have any questions about the planned or ongoing events, "
            + "or if you face any difficulties with logging in or booking for the services, "
            + "please send emails to (E-mail address here) or call (Phone number here) for further assistance. ";
    
    //headings translated to uppercase
    private String h1 = HEADING_1.toUpperCase();
    private String h2 = HEADING_2.toUpperCase();
    
    //default constructor
    public AboutController() {
        
    }

    //PAGE_NAME accessor
    public String getPAGE_NAME() {
        return PAGE_NAME;
    }

    //HEADING_1 accessor
    public String getHEADING_1() {
        return HEADING_1;
    }

    //CONTENT_1 accessor
    public String getCONTENT_1() {
        return CONTENT_1;
    }

    //HEADING_2 accessor
    public String getHEADING_2() {
        return HEADING_2;
    }

    //CONTENT_2 accessor
    public String getCONTENT_2() {
        return CONTENT_2;
    }

    //h1 accessor
    public String getH1() {
        return h1;
    }

    //h2 accessor
    public String getH2() {
        return h2;
    }
}
