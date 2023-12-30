package Client.User.Repositories;

import Client.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

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
    public static Boolean isChatMember(String chatRoomId, String username) {
        String sql = "select * from chatmember where chatroomid = ? and username = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, chatRoomId);
            ps.setString(2, username);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();  // If there is a result, the user is member; otherwise, not member
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static HashSet<String> getChatMembers(String chatRoomId) {
        HashSet<String> members = new HashSet<>();
        String sql = "select username from chatmember where chatroomid = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, chatRoomId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                members.add(rs.getString("username"));
            }
            return members;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static HashSet<String> getAdmins(String chatRoomId) {
        HashSet<String> admins = new HashSet<>();
        String sql = "select username from chatmember where chatroomid = ? and isAdmin = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, chatRoomId);
            ps.setBoolean(2, true);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                admins.add(rs.getString("username"));
            }
            return admins;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void setIsAdmin(String chatRoomId, String username, Boolean isAdmin) {
        String sql = "update chatmember set isAdmin = ? where chatroomid = ? and username = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, isAdmin);
            ps.setString(2, chatRoomId);
            ps.setString(3, username);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static Boolean isAdmin(String chatRoomId, String username) {
        String sql = "select * from chatmember where chatroomid = ? and username = ? and isadmin = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, chatRoomId);
            ps.setString(2, username);
            ps.setBoolean(3, true);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();  // If there is a result, the user is admin; otherwise, not admin
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void removeFromGroup(String chatRoomId, String username) {
        String sql = "delete from chatmember where chatroomid = ? and username = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, chatRoomId);
            ps.setString(2, username);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
