package web.filter;

import Entity.Post;
import Entity.User;
import Service.PostService;
import Storage.DbStorage.DbPostStorage;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebFilter(servletNames = {"DeletePostServlet"})
public class DeletePostFilter extends HttpFilter {
    PostService postService = new PostService(new DbPostStorage());
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        User user = (User) req.getSession().getAttribute("user");
        if(user==null || req.getParameter("id")==null || req.getParameter("id").isBlank()) return;

        int userId = user.getId();
        int elementId = Integer.parseInt(req.getParameter("id"));

        Post post = null;
        ArrayList<Post> posts = new ArrayList<>(postService.getAllPosts());
        for (Post p : posts) {
            if(p.getId()==elementId){
                post = p;
                break;
            }
        }

        if (post==null) return;
        if(post.getAuthor().getId()!=userId) return;
        chain.doFilter(req,res);
    }
}
