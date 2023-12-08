package Client.Models;

import java.sql.*;

public record User(
        String username,
        String name,
        String address,
        Date dob,
        String gender,
        String email,
        boolean status,
        String password,
        Timestamp createdAt,
        boolean isLocked
) {
    public User(String email, String username, String password) {
        this(username, null, null, null, null, email, false, password, new Timestamp(System.currentTimeMillis()), false);
    }
    public static User fromResultSet(ResultSet resultSet) {
        try {
            return new User(
                    resultSet.getString("username"),
                    resultSet.getString("full_name"),
                    resultSet.getString("address"),
                    resultSet.getDate("birth_date"),
                    resultSet.getString("gender"),
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
    String encodePwd() {
        return "";
    }
}