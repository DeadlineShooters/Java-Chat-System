package Client.User.Repositories;

import Client.Models.User;

import java.sql.*;
import java.util.ArrayList;

public class UserController {
//    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
//    // Provide your actual database name in the URL
//    static final String DB_URL = "jdbc:mysql://localhost/tuan8";
//    // Database credentials
//    static final String USER = "root";
//    static final String PASS = "mysql";
//    Connection conn;
    Statement stmt;
    UserController() {

    }

    ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        try {
            ResultSet resultSet = stmt.executeQuery("select * from user");
            while (resultSet.next()) {
                String username = resultSet.getString("username");
                String name = resultSet.getString("name");
                String address = resultSet.getString("address");
                Date dob = resultSet.getDate("dob");
                char gender = resultSet.getString("gender").charAt(0);
                String email = resultSet.getString("email");
                Timestamp createdAt = resultSet.getTimestamp("createdAt");
                boolean isLocked = resultSet.getBoolean("isLocked");

                User user = new User(username, name, email, address, gender, dob, createdAt, isLocked);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }
}
