package Client.Models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public record Session(
        String username,
        Timestamp loginTime,
        Timestamp logoutTime,
        int usersChattedCount,
        int groupsChattedCount) {

    public static Session fromResultSet(ResultSet resultSet) {
        try {
            return new Session(
                    resultSet.getString("username"),
                    resultSet.getTimestamp("login_time"),
                    resultSet.getTimestamp("logout_time"),
                    resultSet.getInt("users_chat_count"),
                    resultSet.getInt("groups_chat_count"));
        } catch (SQLException ex) {
            System.out.println("Failed to create session.");
            return null; 
        }
    }
}

// USE javachatsystem;
// DROP TABLE IF EXISTS session;
// CREATE TABLE session (
//     username VARCHAR(255),
//     login_time TIMESTAMP,
//     logout_time TIMESTAMP,
//     users_chat_count INT,
//     groups_chat_count INT,
//     PRIMARY KEY (username, login_time)
// );
// INSERT INTO Session (username, login_time, logout_time, users_chat_count, groups_chat_count)
// VALUES ('user1', '2023-12-16 02:34:16', '2023-12-16 04:34:16', 5, 2),
//        ('user2', '2023-12-15 10:00:00', '2023-12-15 12:00:00', 3, 1);