package Client.Models;

public record Friend(String user1, String user2) {

    // Overloaded constructor to swap user1 and user2
    public Friend(String user1, String user2) {
        this.user1 = user1;
        this.user2 = user2;

        ChatRoom chat = new ChatRoom("");

    }
}