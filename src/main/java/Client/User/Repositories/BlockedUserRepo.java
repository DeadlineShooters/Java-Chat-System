package Client.User.Repositories;

import Client.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BlockedUserRepo {
    private static Connection conn;

    static {
        // Static block to initialize the connection when the class is loaded
        conn = ConnectionManager.getConnection();
    }
    public static void blockUser(String username, String blockedUser) {
        String sql = "insert into blockeduser (username, blockedUser) values (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, blockedUser);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void unblockUser(String username, String blockedUser) {
        String sql = "delete from blockeduser where username = ? and blockeduser = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, blockedUser);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static Boolean isBlocked(String username, String blockedUser) {
        String sql = "select * from blockeduser where username = ? and blockeduser = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, blockedUser);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();  // If there is a result, the user is blocked; otherwise, not blocked
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
