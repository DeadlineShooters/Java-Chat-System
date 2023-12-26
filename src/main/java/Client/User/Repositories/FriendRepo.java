package Client.User.Repositories;

import Client.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class FriendRepo {
    private static Connection conn;

    static {
        // Static block to initialize the connection when the class is loaded
        conn = ConnectionManager.getConnection();
    }

    public static boolean addFriend(String user1, String user2) {
        String sql = "insert into friend (user1, user2) values (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user1);
            ps.setString(2, user2);
            ps.execute();

            ps.setString(2, user1);
            ps.setString(1, user2);
            ps.execute();

//            ChatRoomRepo.createPrivateChat(user1, user2);

            System.out.println("Add friend successful");
            return true;
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
        return false;
    }
    public static HashMap<String, Boolean> getAllFriends(String username) {
        HashMap<String, Boolean> friends = new HashMap<>();
        String sql = "SELECT f.user2, u.status FROM friend f, user u WHERE f.user1 = ? and f.user2 = u.username";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                // Check for null status before using getBoolean
                boolean isOnline = rs.getBoolean("status");
                friends.put(rs.getString("user2"), isOnline);
                System.out.println(rs.getString("user2")+"::: "+isOnline);
            }

        } catch (SQLException exc) {
            exc.printStackTrace();
        }
        return friends;
    }
    public static boolean isFriend(String user1, String user2) {
        String sql = "SELECT * FROM friend WHERE user1 = ? and user2 = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user1);
            ps.setString(2, user2);

            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return true;

        } catch (SQLException exc) {
            exc.printStackTrace();
        }
        return false;
    }
    public static void unfriend(String user1, String user2) {
        String sql = "delete FROM friend WHERE (user1 = ? and user2 = ?) or (user1 = ? and user2 = ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user1);
            ps.setString(2, user2);
            ps.setString(3, user2);
            ps.setString(4, user1);

            ps.executeUpdate();
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
    }



}
