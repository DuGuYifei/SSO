package client.api;

import java.io.*;
import java.net.Socket;
import java.net.URI;
import java.net.http.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import lsea.entity.Log;

/**
 * UserAPI class for the SSO Management Application (Client)
 */
public class UserAPI {

  /**
   * The endpoint to use for the API
   */
  public static String endpoint;

  /**
   * The endpoint to use for the TCP socket connection
   */
  public static String socketEndpoint;

  /**
   * The port to use for the TCP socket connection
   */
  public static int socketPort;

  /**
   * The file to store the cookie token in
   */
  public static final String COOKIE_FILE = "cookie.txt";

  /**
   * The user's token
   */
  public static Socket socket;

  /**
   * Output stream for the socket
   */
  public static OutputStream outputStream;

  /**
   * Input stream for the socket
   */
  public static ObjectInputStream objectInputStream;

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
      HttpRequest request = HttpRequest
        .newBuilder()
        .uri(URI.create(endpoint + "/api/v1/users/authorize"))
        .header("Content-Type", "application/json")
        .POST(
          HttpRequest.BodyPublishers.ofString(
            "{\"email\": \"" + email + "\", \"password\": \"" + password + "\"}"
          )
        )
        .build();

      // Send the request and get the response
      HttpResponse<String> response = client.send(
        request,
        HttpResponse.BodyHandlers.ofString()
      );

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
      disconnect();
      Files.deleteIfExists(Paths.get(COOKIE_FILE));
      System.out.println("Logged out successfully.");
      return true;
    } catch (IOException e) {
      System.out.println("Failed to clear the cookie token.");
    }
    return false;
  }

  /**
   * Connect to the server using a socket connection
   *
   * @return true if the connection was successful, false otherwise
   */
  public static boolean connect() {
    try {
      System.out.println("Connecting to the server...");
      socket = new Socket("localhost", 3000);
      outputStream = socket.getOutputStream();
      objectInputStream = new ObjectInputStream(socket.getInputStream());
      String token = new String(Files.readAllBytes(Paths.get(COOKIE_FILE)));
      outputStream.write(token.getBytes());
      outputStream.flush();

      return true;
    } catch (IOException e) {
      System.out.println("Failed to connect to the server: " + e.getMessage());
    }
    return false;
  }

  /**
   * Disconnect from the server
   *
   * @return true if the disconnection was successful, false otherwise
   */
  public boolean disconnect() {
    try {
      socket.close();
      return true;
    } catch (IOException e) {
      System.out.println(
        "Failed to disconnect from the server: " + e.getMessage()
      );
    }
    return false;
  }

  /**
   * Retrieve 10 logs from the server
   *
   * @param offset - the offset to start from
   * @param limit  - the number of logs to retrieve
   * @return a list of the retrieved logs
   * @throws IOException - if the request fails
   */
  public List<Log> retrieveLogs(int offset, int limit) throws IOException {
    List<Log> retrievedLogs = new ArrayList<>();
    OutputStream outputStream = socket.getOutputStream();
    String request = "get-log";
    String params = String.valueOf(offset) + "," + String.valueOf(limit);
    outputStream.write(request.getBytes());
    outputStream.flush();
    outputStream.write(params.getBytes());
    outputStream.flush();

    try {
      System.out.println("Receiving logs...");
      int count = objectInputStream.readInt();
      System.out.println("Number of expected logs: " + count);
      while (count > 0) {
        Log log = (Log) objectInputStream.readObject();

        retrievedLogs.add(log);
        count--;
      }
    } catch (ClassNotFoundException e) {
      System.err.println("Error: Invalid object received.");
    } catch (EOFException e) {
      System.out.println("All logs received.");
    }

    return retrievedLogs;
  }

  /**
   * Retrieve new incoming logs from the server
   *
   * @return the new logs list
   * @throws IOException - if the request fails
   */
  public List<Log> liveCaptureLogs() throws IOException {
    List<Log> retrievedLogs = new ArrayList<>();

    String request = "live-get-log";
    outputStream.write(request.getBytes());
    outputStream.flush();
    int count = objectInputStream.readInt();
    System.out.println("Number of expected logs: " + count);

    while (count > 0) {
      try {
        System.out.println("Receiving logs...");
        Log log = (Log) objectInputStream.readObject();

        System.out.println("Received log: " + log);
        retrievedLogs.add(log);
      } catch (ClassNotFoundException e) {
        System.err.println("Error: Invalid object received.");
      } catch (EOFException e) {
        System.out.println("All logs received.");
      }
      count--;
    }
    return retrievedLogs;
  }

  /**
   * Start the live capture
   *
   * @throws IOException - if the request fails
   */
  public void serverStopLiveCapture() throws IOException {
    String request = "live-stop";
    outputStream.write(request.getBytes());
    outputStream.flush();
  }

  /**
   * UserAPI Constructor with a default endpoint
   */
  public UserAPI() {
    UserAPI.endpoint = "http://localhost:8081";
    UserAPI.socketEndpoint = "localhost";
    UserAPI.socketPort = 3000;
  }

  /**
   * User API Constructor with custom endpoints
   *
   * @param endpoint       - the API endpoint
   * @param socketEndpoint - the socket endpoint
   * @param socketPort     - the socket port
   */
  public UserAPI(String endpoint, String socketEndpoint, int socketPort) {
    UserAPI.endpoint = endpoint;
    UserAPI.socketEndpoint = socketEndpoint;
    UserAPI.socketPort = socketPort;
  }
}
