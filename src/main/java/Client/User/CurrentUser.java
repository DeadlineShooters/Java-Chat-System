package Client.User;

import Client.Models.User;
import Client.User.Repositories.ChatRoomRepo;
import Client.User.Repositories.FriendRepo;
import Client.User.Repositories.UserRepo;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CurrentUser {
    private PrintWriter printWriter;
    private User user;
    static CurrentUser currentUser;
    private Map<String, Boolean> friends = new HashMap<>(); // HashMap<username, isOnline>
    private Map<String, String> chatRooms = new HashMap<>(); // HashMap<chatRoomId, username or group name>
    private CurrentUser() {

    }
    public static CurrentUser getInstance() {
        if (currentUser == null)
            currentUser = new CurrentUser();
        return currentUser;
    }
    public void setUser(User user) {

        this.user = user;
        chatRooms = ChatRoomRepo.getAllChatRooms(user.username());
        friends = FriendRepo.getAllFriends(user.username());
    }
    public User getUser() { return user; }
    public Map<String, String> getChatRooms() { return chatRooms; }
    public ArrayList<String> getFriends() { return new ArrayList<String>(friends.keySet()); }
    public void addFriend(String username) {
        friends.put(username, UserRepo.isOnline(username));
    }
    public void removeFriend(String username) {
        friends.remove(username);
    }
    public void setPrintWriter(PrintWriter printWriter) {
        this.printWriter = printWriter;
    }
    public void sendMessage(String message) {
        System.out.println(message);
        printWriter.println(message);
    }
    public boolean isFriend(String username) {
        return friends.containsKey(username);
    }
}
