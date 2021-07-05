package web.filter;

import Entity.Role;
import Entity.User;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(servletNames = {"ApproveCommentServlet","ApprovePostServlet"})
public class ApproveFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        User user = (User) req.getSession().getAttribute("user");
        String id = req.getParameter("id");
        if(user.getRole()!= Role.ADMIN || id==null || id.isBlank()) return;
        chain.doFilter(req,res);
    }
}
