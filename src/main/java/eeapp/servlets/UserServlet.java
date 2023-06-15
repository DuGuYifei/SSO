package eeapp.servlets;

import eeapp.ApiClient;
import eeapp.dao.UserDao;
import eeapp.models.User;

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
@WebServlet(urlPatterns = {"/users", "/users/*"}, name = "UserServlet")
public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Get request to get all the users or add user page
     * @param request the request from the client
     * @param response the response from the server
     * @throws ServletException if the servlet encounters difficulty
     * @throws IOException if the servlet encounters difficulty
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getPathInfo();
        if (path == null) {
            try {
                List<User> users = UserDao.findAll();
                request.setAttribute("users", users);
                request.getRequestDispatcher("/users.jsp").forward(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (path.equals("/add")) {
            request.getRequestDispatcher("/adduser.jsp").forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String path = request.getPathInfo();
        if (path == null) {
            try {
                updateUser(request);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (path.equals("/add")) {
            addUser(request);
        } else if (path.equals("/del")){
            // Get the user ID from the request
            String id = request.getParameter("id");
            try {
                // Delete the user from the database
                UserDao.deleteUser(id);
            } catch (SQLException e) {
                throw new ServletException("Error deleting user", e);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        // Redirect the user back to the user list
        response.sendRedirect(request.getContextPath() + "/users");
    }

    private void addUser(HttpServletRequest request) throws IOException {
        // Get the user data from the request
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        // Create a new use
        ApiClient.addUser(username, password, email);
    }

    private void updateUser(HttpServletRequest request) throws SQLException {
        // Get the user data from the request
        String id = request.getParameter("id");
        String username = request.getParameter("username");
        String email = request.getParameter("email");

        // Update the user
        UserDao.updateUser(id, username, email);
    }
}