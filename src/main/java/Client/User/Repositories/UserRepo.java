package Client.User.Repositories;

import Client.User.ConnectionManager;

import java.sql.*;

public class UserRepo {
    private Connection conn;
    Statement stmt;
    UserRepo() {
        conn = ConnectionManager.getConnection();
        try {
            stmt = conn.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void insert(String username, String password, String email, Timestamp createdAt) {
        String sql = "insert into user (username, password, email, created_at) values (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, email);
            stmt.setTimestamp(4, createdAt);
            stmt.execute();
            System.out.println("success");
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
    }

}
