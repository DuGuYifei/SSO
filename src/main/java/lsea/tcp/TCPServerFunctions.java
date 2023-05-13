package lsea.tcp;

import lsea.entity.Log;
import lsea.entity.User;
import lsea.errors.GenericForbiddenError;
import lsea.utils.LogType;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * This class contains all the functions used by the TCP server.
 */
public class TCPServerFunctions {

    /**
     * Handles the communication with a client.
     *
     * @param clientSocket the client socket
     * @throws RuntimeException when an error occurs
     */
    static void handleClient(Socket clientSocket) {
        UUID userId = null;

        try {
            // Get the input stream from the client socket
            InputStream inputStream = clientSocket.getInputStream();
            ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());

            // Create a byte array to store the incoming data
            byte[] buffer = new byte[1024];
            int bytesRead;

            // Read the first message from the client and verify the token
            if ((bytesRead = inputStream.read(buffer)) != -1) {
                String clientMessage = new String(buffer, 0, bytesRead);

                try {
                    userId = User.verifyToken(clientMessage);
                } catch (GenericForbiddenError e) {
                    clientSocket.close();
                    System.out.println("Invalid token. Client disconnected: " + clientSocket.getInetAddress().getHostAddress());
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

                    List<Log> logs = getLogsFromDatabase(userId, limit, offset);
                    System.out.println(logs.size());
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
                        List<Log> logs = getNewLogsFromDatabase(userId, instant);
                        outputStream.writeInt(logs.size());
                        outputStream.flush();

                        for (Log log : logs) {
                            outputStream.writeObject(log);
                            outputStream.flush();
                        }
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
            System.out.println("Client disconnected: " + clientSocket.getInetAddress().getHostAddress());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets logs from the database.
     *
     * @param userId the user id
     * @param limit  the limit
     * @param offset the offset
     * @return the logs
     */
    static List<Log> getLogsFromDatabase(UUID userId, int limit, int offset) {
        List<Log> logs = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection("jdbc:h2:~/SpringbootLab", "admin", "admin")) {
            String query = "SELECT * FROM logs WHERE id = ? LIMIT ? OFFSET ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, userId.toString().replace("-", ""));
                statement.setInt(2, limit);
                statement.setInt(3, offset);

                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        Log log = new Log();
                        log.setId(getUUIDFromString(resultSet.getString("id")));
                        log.setData(resultSet.getString("data"));
                        log.setLogType(LogType.values()[resultSet.getInt("log_type")]);
                        log.setUserId(getUUIDFromString(resultSet.getString("user_id")));
                        log.setCreatedAt(resultSet.getDate("created_at"));
                        log.setUserCurrentState(resultSet.getString("user_current_state"));
                        logs.add(log);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return logs;
    }

    /**
     * Gets a UUID from a string.
     *
     * @param userId    the string to convert
     * @param timestamp the timestamp
     * @return the UUID
     */
    public static List<Log> getNewLogsFromDatabase(UUID userId, Timestamp timestamp) {
        List<Log> logs = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection("jdbc:h2:~/SpringbootLab", "admin", "admin")) {
            String query = "SELECT * FROM logs WHERE created_at > ? AND user_id = ?";
            System.out.println(timestamp);
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setTimestamp(1, timestamp);
                statement.setString(2, userId.toString().replace("-", ""));
                System.out.println(statement.toString());
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        Log log = new Log();
                        log.setId(getUUIDFromString(resultSet.getString("id")));
                        log.setData(resultSet.getString("data"));
                        log.setLogType(LogType.values()[resultSet.getInt("log_type")]);
                        log.setUserId(getUUIDFromString(resultSet.getString("user_id")));
                        log.setCreatedAt(resultSet.getDate("created_at"));
                        log.setUserCurrentState(resultSet.getString("user_current_state"));
                        logs.add(log);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return logs;
    }

    /**
     * Converts a string to a UUID
     *
     * @param uuid The string to convert
     * @return The UUID
     */
    private static UUID getUUIDFromString(String uuid) {
        String validUuidString = uuid.substring(0, 8) + "-" + uuid.substring(8, 12) + "-" + uuid.substring(12, 16) + "-" + uuid.substring(16, 20) + "-" + uuid.substring(20);
        return UUID.fromString(validUuidString);
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
                System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());

                // Handle the client connection in a separate thread
                Thread clientThread = new Thread(() -> handleClient(clientSocket));
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
