package Server;

import java.util.HashMap;
import java.util.Map;

public class ChatRoom {
    private String roomId;
    private Map<String, User> users = new HashMap();

    ChatRoom(String roomId) {
        this.roomId = roomId;
    }

    public synchronized void join(User user) {
        users.computeIfAbsent(user.getUsername(), k -> user);
        broadcastMessage("Server", user.getUsername() + " joined the chatroom.");
    }
    public synchronized void broadcastMessage(String sender, String msg) {
//        messageHistory.add(formattedMessage);

        for (User user : users.values()) {
            if (user.getUsername().equals(sender))
                continue;
            user.sendMessage(msg);
        }
    }
    public synchronized  User getUser(String username) {
        return users.get(username);
    }
}
