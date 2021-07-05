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

@WebFilter(servletNames = "UpdateDescriptionServlet")
public class UpdateDescriptionFilter extends HttpFilter {
    PostService postService = new PostService(new DbPostStorage());
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {

        User user = (User) req.getSession().getAttribute("user");
        String description = req.getParameter("description");
        if(user==null || req.getParameter("id")==null || req.getParameter("id").isBlank() ||
        description==null || description.isBlank()) return;

        int userId = user.getId();
        int postId = Integer.parseInt(req.getParameter("id"));

        if(!postService.exists(postId)) return;

        Post post = null;
        ArrayList<Post> posts = new ArrayList<>(postService.getAllPosts());
        for (Post p : posts) {
            if(p.getId()==postId){
                post = p;
                break;
            }
        }

        if (post==null) return;
        if(post.getAuthor().getId()!=userId) return;
        chain.doFilter(req,res);

    }
}
