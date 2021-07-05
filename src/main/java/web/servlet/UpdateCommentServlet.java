package web.servlet;

import Service.CommentService;
import Storage.DbStorage.DbCommentStorage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="UpdateCommentServlet",urlPatterns = "/updateComment")
public class UpdateCommentServlet extends HttpServlet {
    CommentService commentService = new CommentService(new DbCommentStorage());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String newText = req.getParameter("newText");
        commentService.updateComment(id, newText);
    }
}
