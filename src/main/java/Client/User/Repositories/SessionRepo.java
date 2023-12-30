package Client.User.Repositories;

import Client.ConnectionManager;
import Client.Models.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SessionRepo {
    private static Connection conn;
    static {
        // Static block to initialize the connection when the class is loaded
        conn = ConnectionManager.getConnection();
    }
    public static void addSession(Session session) {
        String sql = "INSERT INTO session (username, loginTime, logoutTime, usersChattedCount, groupsChattedCount) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, session.username);
            ps.setTimestamp(2, session.loginTime);
            ps.setTimestamp(3, session.logoutTime);
            ps.setInt(4, session.usersChattedCount);
            ps.setInt(5, session.groupsChattedCount);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
