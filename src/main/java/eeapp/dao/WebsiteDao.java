package eeapp.dao;


import eeapp.models.Website;
import lsea.utils.RandomBase64Generator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class WebsiteDao {

    /**
     * The find method get websites by user id.
     * @param userId the id of the user
     * @return a list of website
     * @throws SQLException if there is an error accessing the database
     */
    public static List<Website> findByUserId(String userId) throws SQLException {
        List<Website> websites = new ArrayList<>();

        // Get a connection from the data source
        Connection connection = DatabaseConnection.getConnection();

        // Execute a query
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM websites WHERE created_by_id = ?");
        UUID userIdUUID = UUID.fromString(userId);
        statement.setBytes(1, DatabaseConnection.writeUUID(userIdUUID));
        ResultSet resultSet = statement.executeQuery();

        // Iterate over the result set and create Website objects
        while (resultSet.next()) {
            Website website = new Website();
            website.setId(DatabaseConnection.readUUID(resultSet.getBytes("id")));
            website.setDisplayName(resultSet.getString("display_name"));
            website.setCreatedById(DatabaseConnection.readUUID(resultSet.getBytes("created_by_id")));
            website.setCreatedAt(resultSet.getDate("created_at"));
            website.setRedirectUrl(resultSet.getString("redirect_url"));
            website.setPrivateKey(resultSet.getString("private_key"));
            website.setIsActive(resultSet.getBoolean("is_active"));
            website.setUpdatedAt(resultSet.getDate("updated_at"));
            websites.add(website);
        }

        // Close the connection
        connection.close();

        return websites;
    }

    /**
     * Create a website
     * @param displayName the display name of the website
     * @param redirectUrl the redirect url of the website
     * @param userID the id of the user
     * @throws SQLException if there is an error accessing the database
     */
    public static void create(String displayName, String redirectUrl, String userID) throws SQLException {
        // Get a connection from the data source
        Connection connection = DatabaseConnection.getConnection();

        // Execute an insert
        PreparedStatement statement = connection.prepareStatement("INSERT INTO websites (id, display_name, created_by_id, created_at, redirect_url, private_key, is_active, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
        statement.setBytes(1, DatabaseConnection.writeUUID(UUID.randomUUID()));
        statement.setString(2, displayName);
        statement.setBytes(3, DatabaseConnection.writeUUID(UUID.fromString(userID)));
        statement.setDate(4, new java.sql.Date(new Date().getTime()));
        statement.setString(5, redirectUrl);
        statement.setString(6, RandomBase64Generator.generateLong());
        statement.setBoolean(7, false);
        statement.setDate(8, new java.sql.Date(new Date().getTime()));
        statement.executeUpdate();

        // Close the connection
        connection.close();
    }

    /**
     * Update a website
     * @param id the id of the website
     * @param displayName the display name of the website
     * @param redirectUrl the redirect url of the website
     * @throws SQLException if there is an error accessing the database
     */
    public static void update(String id, String displayName, String redirectUrl) throws SQLException {
        // Get a connection from the data source
        Connection connection = DatabaseConnection.getConnection();

        // Execute an update
        PreparedStatement statement = connection.prepareStatement("UPDATE websites SET display_name = ?, redirect_url = ?, updated_at = ? WHERE id = ?");
        statement.setString(1, displayName);
        statement.setString(2, redirectUrl);
        statement.setDate(3, new java.sql.Date(new Date().getTime()));
        statement.setBytes(4, DatabaseConnection.writeUUID(UUID.fromString(id)));
        statement.executeUpdate();

        // Close the connection
        connection.close();
    }

    /**
     * Delete a website
     * @param id the id of the website
     * @throws SQLException if there is an error accessing the database
     */
    public static void delete(String id) throws SQLException {
        // Get a connection from the data source
        Connection connection = DatabaseConnection.getConnection();

        // Execute a delete
        PreparedStatement statement = connection.prepareStatement("DELETE FROM websites WHERE id = ?");
        statement.setBytes(1, DatabaseConnection.writeUUID(UUID.fromString(id)));
        statement.executeUpdate();

        // Close the connection
        connection.close();
    }
}