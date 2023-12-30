package Client.User;

import Client.Models.Session;
import Client.Models.User;
import Client.User.Repositories.FriendRepo;
import Client.User.Repositories.UserRepo;
import Client.User.Views.Util;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class CurrentUser {
    private PrintWriter printWriter;
    private User user;
    static CurrentUser currentUser;
    private Map<String, Boolean> friends = new HashMap<>(); // HashMap<username, isOnline>
//    private Map<String, String> chatRooms = new HashMap<>(); // HashMap<chatRoomId, username or group name>
    public Session session;
    private CurrentUser() {

    }
    public static CurrentUser getInstance() {
        if (currentUser == null)
            currentUser = new CurrentUser();
        return currentUser;
    }
    public void setUser(User user) {

        this.user = user;
//        chatRooms = ChatRoomRepo.getAllChatRooms(user.username());
        friends = FriendRepo.getAllFriends(user.username());
        session = new Session(user.username(), Util.getCurrentTimestamp(), null, 0, 0);
    }
    public User getUser() { return user; }
//    public Map<String, String> getChatRooms() { return chatRooms; }
    public Map<String, Boolean> getFriends() { return friends; }
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
        System.out.println("at CurrentUser: "+message);
        printWriter.println(message);
    }
    public boolean isFriend(String username) {
        return friends.containsKey(username);
    }
//    public void updateFriendStatus(String username, boolean status) {friends.put(username, status);}
}
