package Client.User.Repositories;

import Client.ConnectionManager;
import Client.Models.UserSession;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class SessionRepo {
    private static Connection conn;
    static {
        // Static block to initialize the connection when the class is loaded
        conn = ConnectionManager.getConnection();
    }

    public static void addSession(UserSession userSession) {
        String sql = "INSERT INTO session (username, logintime, logouttime, userschatcount, groupschatcount) " +
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

    public static void updateSession(UserSession userSession) {
        String sql = "UPDATE session SET logouttime = ?, userschatcount = ?, groupschatcount = ? where logintime = ? and username = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setTimestamp(1, userSession.logoutTime);
            ps.setInt(2, userSession.usersChattedCount);
            ps.setInt(3, userSession.groupsChattedCount);
            Instant loginInstant = userSession.loginTime.toInstant();
            Instant roundedLoginInstant = loginInstant.plusMillis(500).truncatedTo(ChronoUnit.SECONDS);
            Timestamp roundedLoginTime = Timestamp.from(roundedLoginInstant);
            ps.setTimestamp(4, roundedLoginTime);
            ps.setString(5, userSession.username);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
