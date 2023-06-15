package eeapp.servlets;

import eeapp.dao.WebsiteDao;
import eeapp.models.Website;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * The WebsiteServelet class implements a servlet that
 * response to the request of the website.
 */
@WebServlet( urlPatterns = {"/websites", "/websites/del"}, name = "WebsiteServlet")
public class WebsiteServlet extends HttpServlet {

    /**
     * Get request to get all the websites or add website page
     * @param request the request from the client
     * @param response the response from the server
     * @throws ServletException if the servlet encounters difficulty
     * @throws IOException if the servlet encounters difficulty
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();
        String userId = request.getParameter("userId");
        if(path == null) {
            try {
                List<Website> websites = WebsiteDao.findByUserId(userId);
                request.setAttribute("websites", websites);
                request.setAttribute("userId", userId);
                request.getRequestDispatcher("/user_websites.jsp").forward(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (path.equals("/add")) {
            request.setAttribute("userId", userId);
            request.getRequestDispatcher("/addwebsite.jsp").forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    /**
     * Post request to add or update or delete the website
     * @param request the request from the client
     * @param response the response from the server
     * @throws IOException if the servlet encounters difficulty
     * @throws ServletException if the servlet encounters difficulty
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String path = request.getPathInfo();
        String userId = request.getParameter("userId");
        if (path == null) {
            try {
                updateWebsite(request);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (path.equals("/add")) {
            try {
                System.out.println("add website" + userId);
                addWebsite(request);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (path.equals("/del")){
            // Get the user ID from the request
            String id = request.getParameter("id");
            try {
                // Delete the user from the database
                WebsiteDao.delete(id);
            } catch (SQLException e) {
                throw new ServletException("Error deleting user", e);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        // Redirect the user back to the user list
        response.sendRedirect(request.getContextPath() + "/websites?userId=" + userId);
    }

    /**
     * Add a new website
     * @param request the request from the client
     * @throws SQLException if the doa encounters difficulty
     */
    private void addWebsite(HttpServletRequest request) throws SQLException {
        // Get the data from the request
        String userId = request.getParameter("userId");
        String name = request.getParameter("displayName");
        String url = request.getParameter("redirectUrl");

        // Create a new website
        WebsiteDao.create(name, url, userId);
    }

    /**
     * Update the website
     * @param request the request from the client
     * @throws SQLException if the doa encounters difficulty
     */
    private void updateWebsite(HttpServletRequest request) throws SQLException{
        // Get the data from the request
        String id = request.getParameter("id");
        String name = request.getParameter("displayName");
        String url = request.getParameter("redirectUrl");

        // Update website
        WebsiteDao.update(id, name, url);
    }

}
