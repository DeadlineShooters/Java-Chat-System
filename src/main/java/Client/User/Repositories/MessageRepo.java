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
    public static void saveMessage(Message message) {
        String sql = "insert into message (chatroomid, username, content, status, scrollPosition, sentAt) values (?,?,?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, message.chatRoomId());
            ps.setString(2, message.username());
            ps.setString(3, message.content());
            ps.setString(4, message.status());
            ps.setInt(5, message.scrollPosition());
            ps.setTimestamp(6, message.sentAt());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void deleteAllPrivateMessages(String chatRoomId) {
        String sql = "delete from message where chatroomid = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, chatRoomId);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
