package eeapp.dao;

import eeapp.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The UserDao class implements a data access object that
 * provides access to the user table in the database.
 */
public class UserDao {

    /**
     * The findAll method finds all users.
     * @return a list of user
     * @throws SQLException if there is an error accessing the database
     */
    public static List<User> findAll () throws SQLException {
        // Get a connection from the data source
        Connection connection = DatabaseConnection.getConnection();


        // Execute a query
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM users");
        ResultSet resultSet = statement.executeQuery();

        // Process the results
        List<User> beans = new ArrayList<>();
        while (resultSet.next()) {
            User bean = new User();
            bean.setId(DatabaseConnection.readUUID(resultSet.getBytes("id")));
            bean.setUsername(resultSet.getString("username"));
            bean.setPassword(resultSet.getString("password"));
            bean.setCreatedAt(resultSet.getDate("created_at"));
            bean.setUpdatedAt(resultSet.getDate("updated_at"));
            bean.setEmail(resultSet.getString("email"));
            bean.setConfirmationCode(resultSet.getString("confirmation_code"));
            bean.setConfirmedAt(resultSet.getDate("confirmed_at"));
            bean.setGlobalPermission(resultSet.getInt("global_permission"));
            beans.add(bean);
        }

        // Close the connection
        connection.close();

        return beans;
    }

    /**
     * Update a user
     * @param id the id of the user
     * @param username the username of the user
     * @param email the email of the user
     * @throws SQLException if there is an error accessing the database
     */
    public static void updateUser(String id, String username, String email) throws SQLException {
        // Get a connection from the data source
        Connection connection = DatabaseConnection.getConnection();

        // Execute an update
        PreparedStatement statement = connection.prepareStatement("UPDATE users SET username = ?, email = ? WHERE id = ?");
        statement.setString(1, username);
        statement.setString(2, email);
        statement.setBytes(3, DatabaseConnection.writeUUID(UUID.fromString(id)));
        statement.executeUpdate();

        // Close the connection
        connection.close();
    }

    /**
     * The delete method to delete a user by id.
     * @param id the id of the user
     * @throws SQLException if there is an error accessing the database
     */
    public static void deleteUser(String id) throws SQLException {
        // Get a connection from the data source
        Connection connection = DatabaseConnection.getConnection();

        // Execute a delete
        PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE id = ?");
        statement.setBytes(1, DatabaseConnection.writeUUID(UUID.fromString(id)));
        statement.executeUpdate();

        // Close the connection
        connection.close();
    }
}
