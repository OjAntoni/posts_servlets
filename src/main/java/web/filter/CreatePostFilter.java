package web.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(servletNames = "CreatePostServlet")
public class CreatePostFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        if(req.getSession().getAttribute("user")==null) return;
        if(req.getParameter("title")==null || req.getParameter("title").isBlank()) return;
        if(req.getParameter("description")==null || req.getParameter("description").isBlank()) return;
        chain.doFilter(req,res);
    }
}
