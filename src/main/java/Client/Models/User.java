package Client.Models;

import java.sql.*;

public record User(
        String username,
        String name,
        String address,
        Date dob,
        char gender,
        String email,
        boolean status,
        String password,
        Timestamp createdAt,
        boolean isLocked
) {
    public static User fromResultSet(ResultSet resultSet) {
        try {
            return new User(
                    resultSet.getString("username"),
                    resultSet.getString("full_name"),
                    resultSet.getString("address"),
                    resultSet.getDate("birth_date"),
                    resultSet.getString("gender").charAt(0),
                    resultSet.getString("email"),
                    resultSet.getBoolean("status"),
                    resultSet.getString("password"),
                    resultSet.getTimestamp("created_at"),
                    resultSet.getBoolean("is_locked")
            );
        } catch (SQLException ex) {
            System.out.println("Failed to create user.");
            return null; // You should handle this case appropriately in your code
        }
    }

}