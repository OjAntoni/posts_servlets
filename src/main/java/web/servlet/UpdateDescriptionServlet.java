package web.servlet;

import service.PostService;
import storage.DbStorage.DbPostStorage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UpdateDescriptionServlet", urlPatterns = "/updateDescription")
public class UpdateDescriptionServlet extends HttpServlet {
    PostService postService = new PostService(new DbPostStorage());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String description = req.getParameter("description");
        postService.updatePost(id, description);
    }
}
