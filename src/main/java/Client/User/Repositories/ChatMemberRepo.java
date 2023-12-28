package Client.User.Repositories;

import Client.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ChatMemberRepo {
    private static Connection conn;
    static {
        // Static block to initialize the connection when the class is loaded
        conn = ConnectionManager.getConnection();
    }
    public static boolean addChatMember(String chatRoomId, String username) {
        String sql =  "insert into chatmember (chatRoomId, username) values (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, chatRoomId);
            ps.setString(2, username);
            ps.execute();

            System.out.println("Chat member created successfully");
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
