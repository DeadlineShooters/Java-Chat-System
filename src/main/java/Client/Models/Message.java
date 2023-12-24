package Client.Models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public record Message (
    String chatRoomId, String username, String content, String status,
    int scrollPosition,
    Timestamp sentAt
) {
    public static Message fromResultSet(ResultSet rs) {
        try {
            return new Message(
                    rs.getString("chatroomid"),
                    rs.getString("username"),
                    rs.getString("content"),
                    rs.getString("status"),
                    rs.getInt("scrollPosition"),
                    rs.getTimestamp("sentAt")
                    );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
