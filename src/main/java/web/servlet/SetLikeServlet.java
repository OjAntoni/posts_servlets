package web.servlet;

import entity.Like;
import entity.User;
import service.LikeService;
import storage.DbStorage.DbLikeStorage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "SetLikeServlet", urlPatterns = "/setLike")
public class SetLikeServlet extends HttpServlet {
    LikeService likeService = new LikeService(new DbLikeStorage());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        String id = req.getParameter("id");
        if(!likeService.exists(user,Integer.parseInt(id)))
            likeService.add(new Like(user,0), Integer.parseInt(id));
    }
}
