package Client.Admin.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.*;

import Client.ConnectionManager;
import Client.Models.User;

public class GroupAdminRepository {
    private Connection con;

    public GroupAdminRepository() {
        this.con = ConnectionManager.getConnection();
    }

    public ArrayList<User> getGroupAdmins(String chatRoomId) {
        ArrayList<User> chatMembers = new ArrayList<User>();
        String sql = "select * from groupAdmin as ga join user as us on ga.username = us.username where ga.chat_room_id = '" + chatRoomId + "'";
        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                chatMembers.add(User.fromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chatMembers;
    }
}
