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
import java.util.Optional;

@WebServlet(name = "RemoveLikeServlet", urlPatterns = "/removeLike")
public class RemoveLikeServlet extends HttpServlet {
    LikeService likeService = new LikeService(new DbLikeStorage());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        int id = Integer.parseInt(req.getParameter("id"));

        Optional<Like> likeForPost = likeService.getLikeForPost(id, user.getId());
        if(likeForPost.isEmpty()) return;
        likeService.remove(likeForPost.get().getId(),id);
    }
}
