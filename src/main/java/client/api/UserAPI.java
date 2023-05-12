package client.api;

import java.io.*;
import java.nio.file.*;

/**
 * UserAPI class for the SSO Management Application (Client)
 */
public class UserAPI {
    public String endpoint;
    public final String COOKIE_FILE = "cookie.txt";

    /**
     * Login the user with the given credentials
     * 
     * @param email    - the user's email
     * @param password - the user's password
     * @return true if the login was successful, false otherwise
     */
    public boolean login(String email, String password) {
        try {
            // Make a request to the server to login the user
            // and get the cookie token

            String cookieToken = "some_cookie_token";
            Files.write(Paths.get(COOKIE_FILE), cookieToken.getBytes());
            return true;
        } catch (IOException e) {
            System.out.println("Failed to save the cookie token.");
            return false;
        }
    }

    /**
     * Logout the user
     * 
     * @return true if the logout was successful, false otherwise
     */
    public boolean logout() {
        try {
            Files.deleteIfExists(Paths.get(COOKIE_FILE));
            System.out.println("Logged out successfully.");
            return true;
        } catch (IOException e) {
            System.out.println("Failed to clear the cookie token.");
        }
        return false;
    }

    /**
     * UserAPI Constructor with a default endpoint
     */
    public UserAPI() {
        this.endpoint = "http://localhost:8080";
    }

    /**
     * UserAPI Constructor with a custom endpoint.
     * 
     * @param endpoint - the endpoint to use
     */
    public UserAPI(String endpoint) {
        this.endpoint = endpoint;
    }
}
