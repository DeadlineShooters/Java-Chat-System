package Client.Models;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatRoom {
    String id, name;
    Timestamp createdAt;
    Map<String, Boolean> members = new HashMap<>(); // true means the member is an ADMIN
    List<Message> messages = new ArrayList<>();
}
