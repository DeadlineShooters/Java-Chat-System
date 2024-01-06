package Client.Admin.Repository;

import Client.ConnectionManager;
import Client.Models.ChatRoom;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ChatRoomRepository {
    private Connection con;

    public ChatRoomRepository() {
        this.con = ConnectionManager.getConnection();
    }

    public ArrayList<ChatRoom> getChatRooms() {
        ArrayList<ChatRoom> chatRooms = new ArrayList<ChatRoom>();
        String sql = "select * from chatroom where name != \"\"";
        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                chatRooms.add(ChatRoom.fromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chatRooms;
    }
}
