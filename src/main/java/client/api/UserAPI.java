package client.api;

import java.io.*;
import java.net.URI;
import java.net.http.*;
import java.nio.file.*;

/**
 * UserAPI class for the SSO Management Application (Client)
 */
public class UserAPI {
    /**
     * The endpoint to use for the API
     */
    public String endpoint;

    /**
     * The file to store the cookie token in
     */
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
            // Set up the request
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endpoint + "/api/v1/users/authorize"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers
                            .ofString("{\"email\": \"" + email + "\", \"password\": \"" + password + "\"}"))
                    .build();

            // Send the request and get the response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Check if the response was successful
            if (response.statusCode() == 200) {
                String body = response.body();
                String[] _splitted = body.split("\"data\":\"");
                String[] splitted = _splitted[1].split("\"");
                String token = splitted[0];

                if (token != null) {
                    Files.write(Paths.get(COOKIE_FILE), token.getBytes());
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to save the cookie token.");
        } catch (Exception e) {
            System.out.println("Failed to login: " + e.getMessage());
        }
        return false;
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
        this.endpoint = "http://localhost:8081";
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
