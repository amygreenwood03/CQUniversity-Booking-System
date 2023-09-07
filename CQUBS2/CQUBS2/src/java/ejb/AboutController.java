package ejb;

import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 * This controller controls "About Us" page.
 */

@Named(value = "aboutController")
@SessionScoped
public class AboutController implements Serializable {
    private final String PAGE_NAME = "About Us";
    private final String HEADING_1 = "CQUniversity Public Services";
    private final String CONTENT_1 = "With our students, CQUniversity provides a variety of public services at campuses in Australia-wide multiple locations. "
            + "You can participate to the services provided by CQUniversity at the campuses in your regions by registering via this website.";
    
    private final String HEADING_2 = "Heading 2";
    private final String CONTENT_2 = "Lorem Ipsum something, please add some details here or leave both this paragraph and headings blank.";
    
    private String h1 = HEADING_1.toUpperCase();
    private String h2 = HEADING_2.toUpperCase();
    
    public AboutController() {
        
    }

    public String getPAGE_NAME() {
        return PAGE_NAME;
    }

    public String getHEADING_1() {
        return HEADING_1;
    }

    public String getCONTENT_1() {
        return CONTENT_1;
    }

    public String getHEADING_2() {
        return HEADING_2;
    }

    public String getCONTENT_2() {
        return CONTENT_2;
    }

    public String getH1() {
        return h1;
    }

    public String getH2() {
        return h2;
    }
}
