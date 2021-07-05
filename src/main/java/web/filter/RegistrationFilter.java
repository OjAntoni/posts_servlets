package web.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;

@WebFilter(servletNames = "RegistrationServlet")
public class RegistrationFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String name = req.getParameter("name");
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if(name == null || name.trim().isBlank()){
            res.getWriter().println("Nothing in name");
            return;
        }
        if(name.length()<3 || name.length()>16){
            res.getWriter().println("Invalid name");
            return;
        }
        if(username == null || username.length()>16 || username.length() < 2 || username.trim().isBlank()){
            res.getWriter().println("Invalid username");
            return;
        }
        if(password==null || !password.trim().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$") ||
                password.length()<3 || password.length()>16){
            res.getWriter().println("Invalid password");
            return;
        }

        chain.doFilter(req,res);
    }
}
