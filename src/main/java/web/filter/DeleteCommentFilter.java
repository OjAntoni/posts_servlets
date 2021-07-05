package web.filter;

import Entity.Comment;
import Entity.Post;
import Entity.User;
import Service.CommentService;
import Storage.DbStorage.DbCommentStorage;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebFilter(servletNames = "DeleteCommentServlet")
public class DeleteCommentFilter extends HttpFilter {
    CommentService commentService = new CommentService(new DbCommentStorage());

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        User user = (User) req.getSession().getAttribute("user");
        if(user==null || req.getParameter("id")==null || req.getParameter("id").isBlank()) return;

        int userId = user.getId();
        int elementId = Integer.parseInt(req.getParameter("id"));

        Comment comment = null;
        ArrayList<Comment> comments = new ArrayList<>(commentService.getAllComments(elementId));
        for (Comment c : comments) {
            if(c.getId()==elementId){
                comment = c;
                break;
            }
        }

        if (comment==null) return;
        if(comment.getUser().getId()!=userId) return;
        chain.doFilter(req,res);
    }
}
