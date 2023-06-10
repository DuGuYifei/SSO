package eeapp.servlets;

import eeapp.dao.UserDao;
import eeapp.models.User;

import java.io.Console;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * The UserServlet class implements a servlet that
 * response to the request of the user.
 */
@WebServlet(urlPatterns = "/users", name = "UserServlet")
public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // TODO: delete these comment when write view. If we want to specify the request path, uncomment this line.
            // String pathInfo = request.getPathInfo();

            List<User> beans = UserDao.findAll();

            // Set the beans as a request attribute and forward to the JSP page
            request.setAttribute("beans", beans);
            System.out.println("xxx");
            request.getRequestDispatcher("/users.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}