package web.filter;

import entity.Comment;
import entity.User;
import service.CommentService;
import storage.DbStorage.DbCommentStorage;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebFilter(servletNames = "UpdateCommentServlet")
public class UpdateCommentFilter extends HttpFilter {
    CommentService commentService = new CommentService(new DbCommentStorage());

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        User user = (User) req.getSession().getAttribute("user");
        String id = req.getParameter("id");
        String newText = req.getParameter("newText");

        if(user==null || id==null || newText==null || newText.isBlank()) return;
        Optional<Comment> comment = commentService.getComment(Integer.parseInt(id));
        if(comment.isEmpty()) return;
        if(!comment.get().getUser().equals(user)) return;

        chain.doFilter(req,res);
    }
}
