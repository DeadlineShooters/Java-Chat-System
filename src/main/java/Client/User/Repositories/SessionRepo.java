package Client.User.Repositories;

import Client.ConnectionManager;
import Client.Models.UserSession;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SessionRepo {
    private static Connection conn;
    static {
        // Static block to initialize the connection when the class is loaded
        conn = ConnectionManager.getConnection();
    }
    public static void addSession(UserSession userSession) {
        String sql = "INSERT INTO session (username, loginTime, logoutTime, usersChattedCount, groupsChattedCount) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userSession.username);
            ps.setTimestamp(2, userSession.loginTime);
            ps.setTimestamp(3, userSession.logoutTime);
            ps.setInt(4, userSession.usersChattedCount);
            ps.setInt(5, userSession.groupsChattedCount);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
