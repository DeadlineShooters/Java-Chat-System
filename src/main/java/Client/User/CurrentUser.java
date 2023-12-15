package Client.User;

import Client.Models.User;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CurrentUser {
    private PrintWriter printWriter;
    private User user;
    static CurrentUser currentUser;
    public Map<String, String> friends = new HashMap<>(); // HashMap<username, chatRoomId>
    private CurrentUser() {}
    public static CurrentUser getInstance() {
        if (currentUser == null)
            currentUser = new CurrentUser();
        return currentUser;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public User getUser() { return user; }
    private void setFriends() {

    }
    public ArrayList<String> getFriends() { return new ArrayList<String>(friends.keySet()); }
    public void addFriend(String username, String chatRoomId) {
        friends.computeIfAbsent(username, k -> chatRoomId);
    }
    public void removeFriend(String username) {
        friends.remove(username);
    }
    public void setPrintWriter(PrintWriter printWriter) {
        this.printWriter = printWriter;
    }
    public void sendMessage(String message) {
        printWriter.println(message);
    }
    public boolean isFriend(String username) {
        return friends.containsKey(username);
    }
}
