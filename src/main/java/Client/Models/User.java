package Client.Models;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
    public static User fromResultSet(ResultSet resultSet) {
        try {
            return new User(
                    resultSet.getString("username"),
                    resultSet.getString("fullname"),
                    resultSet.getString("address"),
                    resultSet.getDate("birthdate"),
                    resultSet.getString("gender"),
                    resultSet.getString("email"),
                    resultSet.getBoolean("status"),
                    resultSet.getString("password"),
                    resultSet.getTimestamp("createdat"),
                    resultSet.getBoolean("islocked")
            );
        } catch (SQLException ex) {
            System.out.println("Failed to create user.");
            return null; // You should handle this case appropriately in your code
        }
    }
    public static String encryptPassword(String passwordToHash) {
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // Add password bytes to digest
            md.update(passwordToHash.getBytes());

            // Get the hash's bytes
            byte[] bytes = md.digest();

            // This bytes[] has bytes in decimal format. Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }

            // Get complete hashed password in hex format
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}