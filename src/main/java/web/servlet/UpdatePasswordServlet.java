package web.servlet;

import Entity.User;
import Service.UserService;
import Storage.DbStorage.DbUserStorage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="UpdatePasswordServlet",urlPatterns = "/updatePassword")
public class UpdatePasswordServlet extends HttpServlet {
    UserService userService = new UserService(new DbUserStorage());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String oldPassword = req.getParameter("oldPassword");
        String newPassword = req.getParameter("newPassword");
        User user = (User) req.getSession().getAttribute("user");
        if(!oldPassword.equals(user.getPassword())){
            resp.getWriter().println("invalid password");
            return;
        }
        userService.updateUserPassword(user,newPassword);
    }
}
