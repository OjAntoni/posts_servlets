package web.servlet;

import Entity.Post;
import Entity.User;
import Service.PostService;
import Storage.DbStorage.DbPostStorage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CreatePostServlet", urlPatterns = "/createPost")
public class CreatePostServlet extends HttpServlet {
    PostService postservice = new PostService(new DbPostStorage());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String description = req.getParameter("description");
        String title = req.getParameter("title");
        User author = (User) req.getSession().getAttribute("user");
        Post newPost = new Post(0,title,description,author,null,null);
        postservice.save(newPost);
    }
}
