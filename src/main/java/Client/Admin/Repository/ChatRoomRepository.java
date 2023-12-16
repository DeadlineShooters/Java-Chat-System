package Client.Admin.Repository;

import java.sql.*;
import java.util.ArrayList;

import Client.ConnectionManager;
import Client.Models.ChatRoom;

public class ChatRoomRepository {
    private Connection con;

    public ChatRoomRepository() {
        this.con = ConnectionManager.getConnection();
    }

    public ArrayList<ChatRoom> getChatRooms() {
        ArrayList<ChatRoom> chatRooms = new ArrayList<ChatRoom>();
        String sql = "select * from chatroom";
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
