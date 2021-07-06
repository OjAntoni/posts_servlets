package web.servlet;

import service.CommentService;
import storage.DbStorage.DbCommentStorage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ApproveCommentServlet", urlPatterns = "/approveComment")
public class ApproveCommentServlet extends HttpServlet {

    CommentService commentService = new CommentService(new DbCommentStorage());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        commentService.approve(id);
    }
}
