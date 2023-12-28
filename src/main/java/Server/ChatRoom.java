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
        if (users.containsKey(user.getUsername()))
            return;
        users.put(user.getUsername(), user);
        System.out.println("at ChatRoom: "+ user.getUsername() + " joined the chatroom.");
    }
    public synchronized void remove(String username) {
        users.remove(username);
    }
    public synchronized void broadcastMessage(String sender, String msg) {
//        messageHistory.add(formattedMessage);
        System.out.println("at ChatRoom, "+sender + " broadcast: "+msg);
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
