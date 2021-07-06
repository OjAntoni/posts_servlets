package web.filter;

import entity.User;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(servletNames = "CreateCommentServlet")
public class CreateCommentFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String id = req.getParameter("id");
        String text = req.getParameter("text");
        User user = (User) req.getSession().getAttribute("user");
        if(user==null||id==null||text==null||id.isBlank()||text.isBlank()) return;
        chain.doFilter(req,res);
    }
}
