package Client.User.Repositories;

import Client.ConnectionManager;
import Client.User.Views.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class ChatRoomRepo {
    private static Connection conn;
    static {
        // Static block to initialize the connection when the class is loaded
        conn = ConnectionManager.getConnection();
    }
    public static String createPrivateChat(String user1, String user2) {
        String chatRoomId = Util.createUUID();
        String sql = "insert into chatroom (chatRoomId, name, createdAt) values (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, chatRoomId);
            ps.setString(2, "");
            ps.setTimestamp(3, Util.getCurrentTimestamp());
            ps.execute();

            ChatMemberRepo.addChatMember(chatRoomId, user1);
            ChatMemberRepo.addChatMember(chatRoomId, user2);

            System.out.println("Chat room created successfully");
            return chatRoomId;
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
        return null;
    }
    public static String createGroupChat(String name, String username, HashSet<String> members) {
        String chatRoomId = Util.createUUID();
        if (name == null || name.isEmpty())
            name = getSaltString(5);
        String sql = "insert into chatroom (chatRoomId, name, createdAt) values (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, chatRoomId);
            ps.setString(2, "Group chat "+name);
            ps.setTimestamp(3, Util.getCurrentTimestamp());
            ps.execute();

            ChatMemberRepo.addChatMember(chatRoomId, username);
            for (String member : members) {
                ChatMemberRepo.addChatMember(chatRoomId, member);
            }

            System.out.println("Group chat created successfully");
            return chatRoomId;
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
        return null;
    }
    static String getSaltString(int length) {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < length) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }
    public static HashMap<String, String> getAllChatRooms(String username) {
        // HashMap<chatRoomId, username or group name>
        HashMap<String, String> res = new HashMap<>();
        String sql = "SELECT cr.chatRoomId, " +
                "CASE WHEN cr.name = \"\" THEN " +
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

            rs.next();
            String name = rs.getString("name");
            return !name.isEmpty();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static String findPrivateChatId(String user1, String user2) {
        String sql = "select cr.chatroomid from chatroom cr, chatmember cm1, chatmember cm2 " +
                "where cr.chatroomid = cm1.chatroomid and cr.chatroomid = cm2.chatroomid and cr.name = \"\"" +
                "and cm1.username = ? and cm2.username = ?" ;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user1);
            ps.setString(2, user2);
            ResultSet rs = ps.executeQuery();

            if (rs.next())
                return rs.getString("chatroomid");
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
