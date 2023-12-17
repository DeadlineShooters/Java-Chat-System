package Client.User.Repositories;

import Client.ConnectionManager;
import Client.User.Views.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class ChatRoomRepo {
    private static Connection conn;
    static {
        // Static block to initialize the connection when the class is loaded
        conn = ConnectionManager.getConnection();
    }
    public static boolean createPrivateChat(String user1, String user2) {
        String chatRoomId = Util.createUUID();
        String sql = "insert into chatroom (chatRoomId, createdAt) values (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, chatRoomId);
            ps.setTimestamp(2, Util.getCurrentTimestamp());
            ps.execute();

            ChatMemberRepo.addChatMember(chatRoomId, user1);
            ChatMemberRepo.addChatMember(chatRoomId, user2);

            System.out.println("Chat room created successfully");
            return true;
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
        return false;
    }
    public static HashMap<String, String> getAllChatRooms(String username) {
        // HashMap<chatRoomId, username or group name>
        HashMap<String, String> res = new HashMap<>();
        String sql = "SELECT cr.chatRoomId, " +
                "CASE WHEN cr.name IS NULL THEN " +
                "(SELECT cm2.username FROM chatmember cm2 WHERE cm2.chatRoomId = cr.chatRoomId AND cm2.username != ? LIMIT 1) " +
                "ELSE cr.name END AS chatPartner " +
                "FROM chatroom cr JOIN chatmember cm ON cr.chatRoomId = cm.chatRoomId " +
                "WHERE cm.username = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, username);

            ResultSet rs = preparedStatement.executeQuery();

            // Process the results
            while (rs.next()) {
                res.computeIfAbsent(rs.getString("chatRoomId"), k -> {
                    try {
                        return rs.getString("chatPartner");
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
    public static boolean isGroupChat(String chatRoomId) {
        String sql = "select name from chatroom where chatroomid = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, chatRoomId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
            return false;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
