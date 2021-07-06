package web.servlet;

import service.PostService;
import storage.DbStorage.DbPostStorage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DeletePostServlet", urlPatterns = "/deletePost")
public class DeletePostServlet extends HttpServlet {
    PostService postSErvice = new PostService(new DbPostStorage());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String postId = req.getParameter("id");
        postSErvice.delete(Integer.parseInt(postId));
    }
}
