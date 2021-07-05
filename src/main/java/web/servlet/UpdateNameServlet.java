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

@WebServlet(name = "UpdateNameServlet", urlPatterns = "/updateName")
public class UpdateNameServlet extends HttpServlet {
    UserService userService = new UserService(new DbUserStorage());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        String newName = req.getParameter("newName");
        userService.updateUsername(user,newName);
    }
}
