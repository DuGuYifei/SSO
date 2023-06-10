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
 * Created By IDEA
 * Author: s188026 Yifei Liu
 * Date: 2023/6/10 12:26
 * Description:
 */

public class UserDao {

    public static List<User> findAll () throws SQLException {
        // Get a connection from the data source
        Connection connection = DatabaseConnection.getConnection();


        // Execute a query
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM my_table");
        ResultSet resultSet = statement.executeQuery();

        // Process the results
        List<User> beans = new ArrayList<>();
        while (resultSet.next()) {
            User bean = new User();
            bean.setId(UUID.fromString(resultSet.getString("id")));
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
}
