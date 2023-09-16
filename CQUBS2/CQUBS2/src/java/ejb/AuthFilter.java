package ejb;

import jakarta.faces.application.ResourceHandler;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Authentication Filter makes sure that specific user types can obtain access to certain pages only. 
 * The purpose of this class is to prevent guests or volunteers to access the staff only pages. 
 */

public class AuthFilter implements Filter {
    private FilterConfig config;
    
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession(false);
        
        String url = request.getRequestURI();
        String context = request.getContextPath();
        
        //Staff specialised links
        String staffHome = context + "/index_staff.faces";
        String staffDetails = context + "/service_details_staff.faces";
        String staffServices = context + "/services_staff.faces";
        String staffSAdd = context + "/service_add_staff.faces";
        String staffSEdit = context + "/service_edit_staff.faces";
        String staffNotif = context + "/send_notif_staff.faces";
        String staffProfile = context + "/profile_staff.faces";
        String staffEditProfile = context + "/profile_edit_staff.faces";
        String staffCDetails = context + "/category_details_staff.faces";
        String staffCategories = context + "/categories_staff.faces";
        String staffCAdd = context + "/category_add_staff.faces";
        String staffCEdit = context + "/category_edit_staff.faces";
        
        //Links for volunteers and guests
        String genHome = context + "/index.faces";
        String genDetails = context + "/service_details.faces";
        String genServices = context + "/services.faces";
        String genProfile = context + "/profile.faces";
        String genEditProfile = context + "/profile_edit.faces";
        String about = context + "/about_us.faces";
        
        if(url.startsWith(request.getContextPath() + ResourceHandler.RESOURCE_IDENTIFIER)) {
            chain.doFilter(request, response);
        }
        else if(url.matches(".*login.*\\.faces") || url.matches(".*sign_up.*\\.faces")) {
            if(session != null && session.getAttribute("user") != null && session.getAttribute("user").getClass().getSimpleName().equals("Staff"))
                response.sendRedirect(staffHome);
            else if(session != null && session.getAttribute("user") != null && session.getAttribute("user").getClass().getSimpleName().equals("Volunteer"))
                response.sendRedirect(genHome);
            else
                chain.doFilter(request, response);
        }
        else if(url.equals(about))
            if(session != null && session.getAttribute("user") != null && session.getAttribute("user").getClass().getSimpleName().equals("Staff"))
                response.sendRedirect(staffHome);
            else
                chain.doFilter(request, response);
        else if(url.matches(".*_staff\\.faces")) {
            if(session != null && session.getAttribute("user") != null && session.getAttribute("user").getClass().getSimpleName().equals("Staff"))
                chain.doFilter(request, response);
            else {   
                if(url.equals(staffHome))
                    response.sendRedirect(genHome);
                else if(url.equals(staffDetails))
                    response.sendRedirect(genDetails);
                else if(url.equals(staffServices))
                    response.sendRedirect(genServices);
                else if(url.equals(staffProfile) || url.equals(staffEditProfile)) {
                    if(session != null && session.getAttribute("user") == null)
                        response.sendRedirect(genHome);
                    else
                        response.sendRedirect(genProfile);
                }
                else if(url.equals(staffSAdd) || url.equals(staffSEdit) || url.equals(staffCAdd) || url.equals(staffCEdit) || url.equals(staffCategories) || url.equals(staffCDetails))
                    response.sendRedirect(genServices);
                else if(url.equals(staffNotif))
                    response.sendRedirect(genServices);
            }
        }
        else if(url.matches(".*\\.faces")) {
            if(session != null && session.getAttribute("user") != null && session.getAttribute("user").getClass().getSimpleName().equals("Staff")) {
                if(url.equals(genHome))
                    response.sendRedirect(staffHome);
                else if(url.equals(genDetails))
                    response.sendRedirect(staffDetails);
                else if(url.equals(genServices))
                    response.sendRedirect(staffServices);
                else if(url.equals(genProfile) || url.equals(genEditProfile))
                    response.sendRedirect(staffProfile);
            }
            else
                chain.doFilter(request, response);
        }
        else
            chain.doFilter(request, response);
    }
    
    @Override
    public void init(FilterConfig config) throws ServletException {
        this.config = config;
    }
    
    @Override
    public void destroy() {
        config = null;
    }
}
