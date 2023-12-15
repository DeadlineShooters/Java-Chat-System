package Client.User.Repositories;

import Client.ConnectionManager;
import Client.User.Views.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ChatRoomRepo {
    private static Connection conn;
    static {
        // Static block to initialize the connection when the class is loaded
        conn = ConnectionManager.getConnection();
    }
    public static boolean createPrivateChat(String user1, String user2) {
        String chatRoomId = Util.createUUID();
        String sql = "insert into chatroom (chatRoomId, name, createdAt) values (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, chatRoomId);
            ps.setString(2, "");
            ps.setTimestamp(3, Util.getCurrentTimestamp());
            ps.execute();
            ps.close();

            ChatMemberRepo.addChatMember(chatRoomId, user1);
            ChatMemberRepo.addChatMember(chatRoomId, user2);

            System.out.println("Chat room created successfully");
            return true;
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
        return false;
    }
}
