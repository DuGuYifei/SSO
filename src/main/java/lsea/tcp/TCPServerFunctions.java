package lsea.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.time.Instant;
import java.util.List;
import lsea.entity.Log;
import lsea.entity.User;
import lsea.errors.GenericForbiddenError;
import lsea.service.LogService;
import org.springframework.stereotype.Component;

/**
 * This class contains all the functions used by the TCP server.
 */
@Component
public class TCPServerFunctions {

  /**
   * The log service.
   */
  private static LogService logService;

  /**
   * Constructor of the TCPServerFunctions class.
   *
   * @param logService - LogService
   */
  public TCPServerFunctions(LogService logService) {
    TCPServerFunctions.logService = logService;
  }

  /**
   * Handles the communication with a client.
   *
   * @param clientSocket the client socket
   * @throws IOException if an I/O error occurs when creating the input stream, the socket is closed, the
   * @throws InterruptedException if the current thread is interrupted by another thread while it is waiting
   */
  public static void handleClient(Socket clientSocket)
    throws IOException, InterruptedException {
    // Get the input stream from the client socket
    InputStream inputStream = clientSocket.getInputStream();
    ObjectOutputStream outputStream = new ObjectOutputStream(
      clientSocket.getOutputStream()
    );

    // Create a byte array to store the incoming data
    byte[] buffer = new byte[1024];
    int bytesRead;

    // Read the first message from the client and verify the token
    if ((bytesRead = inputStream.read(buffer)) != -1) {
      String clientMessage = new String(buffer, 0, bytesRead);

      try {
        User.verifyToken(clientMessage);
      } catch (GenericForbiddenError e) {
        clientSocket.close();
        System.out.println(
          "Invalid token. Client disconnected: " +
          clientSocket.getInetAddress().getHostAddress()
        );
      }
    }

    // Read data from the client until the input stream is closed
    while ((bytesRead = inputStream.read(buffer)) != -1) {
      // Convert the received bytes to a string
      String clientMessage = new String(buffer, 0, bytesRead);
      System.out.println("Received message from client: " + clientMessage);

      int limit = 0;
      int offset = 0;

      if (clientMessage.equals("get-log")) {
        if ((bytesRead = inputStream.read(buffer)) != -1) {
          String paramsString = new String(buffer, 0, bytesRead);
          String[] parsedData = paramsString.split(",");
          offset = Integer.parseInt(parsedData[0]);
          limit = Integer.parseInt(parsedData[1]);
        }

        // Get the logs from the database
        List<Log> logs;
        logs = logService.findLogs(offset, limit);
        System.out.println("Logs: " + logs.size());
        outputStream.writeInt(logs.size());

        // Send the serialized logs
        for (Log log : logs) {
          outputStream.writeObject(log);
        }
        outputStream.flush();
      }

      if (clientMessage.equals("live-get-log")) {
        Timestamp instant = Timestamp.from(Instant.now());
        boolean liveCapture = true;
        while (liveCapture) {
          List<Log> logs;
          logs = logService.findLiveLogs(instant);
          System.out.println(logs.size());
          outputStream.writeInt(logs.size());

          for (Log log : logs) {
            outputStream.writeObject(log);
          }
          outputStream.flush();

          instant = Timestamp.from(Instant.now());
          // Wait for 1 second before checking for new logs again
          Thread.sleep(1000);

          if ((bytesRead = inputStream.read(buffer)) != -1) {
            String msg = new String(buffer, 0, bytesRead);
            System.out.println(msg);
            if (msg.equals("live-stop")) {
              liveCapture = false;
            }
          }
        }
      }
    }

    clientSocket.close();
    System.out.println(
      "Client disconnected: " + clientSocket.getInetAddress().getHostAddress()
    );
  }

  /**
   * Starts the server
   */
  public static void startServer() {
    int port = 3000;
    try (ServerSocket serverSocket = new ServerSocket(port)) {
      System.out.println("Server listening on port " + port);

      while (true) {
        // Accept incoming connections
        Socket clientSocket = serverSocket.accept();
        System.out.println(
          "Client connected: " + clientSocket.getInetAddress().getHostAddress()
        );

        // Handle the client connection in a separate thread
        Thread clientThread = new Thread(() -> {
          try {
            handleClient(clientSocket);
          } catch (IOException e) {
            throw new RuntimeException(e);
          } catch (InterruptedException e) {
            throw new RuntimeException(e);
          }
        });
        clientThread.start();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
