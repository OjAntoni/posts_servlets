package web.servlet;

import Entity.Role;
import Entity.User;
import Service.UserService;
import Storage.DbStorage.DbUserStorage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RegistrationServlet", urlPatterns = "/registration")
public class RegistrationServlet extends HttpServlet {
    UserService userService = new UserService(new DbUserStorage());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        if(userService.existsByUsername(username)){
            resp.getWriter().println("There is already existing user with this name");
            return;
        }
        User user = new User(0,name,username,password, Role.USER);
        userService.save(user);
        req.getSession().setAttribute("userId", user.getId());
        resp.getWriter().println("ok");
    }
}
