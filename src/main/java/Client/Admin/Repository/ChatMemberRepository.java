package Client.Admin.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.*;

import Client.ConnectionManager;
import Client.Models.User;

public class ChatMemberRepository {
    private Connection con;

    public ChatMemberRepository() {
        this.con = ConnectionManager.getConnection();
    }

    public ArrayList<User> getChatMembers(String chatRoomId) {
        ArrayList<User> chatMembers = new ArrayList<User>();
        String sql = "select * from chatMember as crm join user as us on crm.username = us.username where crm.chat_room_id = '" + chatRoomId + "'";
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
