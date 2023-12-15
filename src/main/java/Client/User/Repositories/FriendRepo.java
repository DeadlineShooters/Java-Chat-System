package Client.User.Repositories;

import Client.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

            ChatRoomRepo.createPrivateChat(user1, user2);

            System.out.println("Add friend successful");
            return true;
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
        return false;
    }
}
