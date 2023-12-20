package Client.User.Repositories;

import Client.ConnectionManager;
import Client.Models.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MessageRepo {
    private static Connection conn;
    static {
        // Static block to initialize the connection when the class is loaded
        conn = ConnectionManager.getConnection();
    }
    public static ArrayList<Message> getAllMessages(String chatRoomId) {
        ArrayList<Message> messages = new ArrayList<>();
        String sql = "select * from message where chatroomid = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, chatRoomId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                messages.add(Message.fromResultSet(rs));
            }
            return messages;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
