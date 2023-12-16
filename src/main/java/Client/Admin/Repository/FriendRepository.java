package Client.Admin.Repository;

import Client.ConnectionManager;
import Client.Models.Friend;
import Client.Models.User;
import Client.Models.Session;

import java.sql.*;
import java.util.ArrayList;

public class FriendRepository {
    private Connection con;

    public FriendRepository() {
        this.con = ConnectionManager.getConnection();
    }

    public ArrayList<User> getFriendList(String username) {
        ArrayList<User> friends = new ArrayList<User>();
        String sql = "select * from user as u2 join friend as fr on fr.user2 = u2.username where fr.user1 = '" + username + "'";
        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                friends.add(User.fromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return friends;
    }
}
