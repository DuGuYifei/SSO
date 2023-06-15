package eeapp;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * The ApiClient class implements a client that can be used to
 * make requests to the API from previous tasks.
 */
public class ApiClient {
    public static void addUser(String username, String password, String email) throws IOException {
        // Build the request body
        String json = String.format("{\"username\": \"%s\", \"password\": \"%s\", \"email\": \"%s\"}", username, password, email);
        byte[] body = json.getBytes(StandardCharsets.UTF_8);

        // Build the request
        URL url = new URL("http://localhost:8081/api/v1/users");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; utf-8");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);

        // Send the request body
        try (OutputStream os = conn.getOutputStream()) {
            os.write(body, 0, body.length);
        }

        // Check the response code
        int responseCode = conn.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new IOException("Failed to add user: " + responseCode);
        }

        // Close the connection
        conn.disconnect();
    }
}