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
                    resultSet.getTimestamp("logintime"),
                    resultSet.getTimestamp("logouttime"),
                    resultSet.getInt("userschatcount"),
                    resultSet.getInt("groupschatcount"));
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
//     logintime TIMESTAMP,
//     logouttime TIMESTAMP,
//     userschatcount INT,
//     groupschatcount INT,
//     PRIMARY KEY (username, logintime)
// );
// INSERT INTO Session (username, logintime, logouttime, userschatcount, groupschatcount)
// VALUES ('user1', '2023-12-16 02:34:16', '2023-12-16 04:34:16', 5, 2),
//        ('user2', '2023-12-15 10:00:00', '2023-12-15 12:00:00', 3, 1);