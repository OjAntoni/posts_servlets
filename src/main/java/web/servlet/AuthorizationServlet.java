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
import java.util.Optional;

@WebServlet(name = "AuthorizationServlet", urlPatterns = "/authorization")
public class AuthorizationServlet extends HttpServlet {
UserService userService = new UserService(new DbUserStorage());
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        Optional<User> userByUsername = userService.getUserByUsername(username);
        if(userByUsername.isEmpty() || !password.equals(userByUsername.get().getPassword())){
            resp.getWriter().println("invalid username or password(");
            return;
        }
        req.getSession().setAttribute("user",userByUsername.get());
    }
}
