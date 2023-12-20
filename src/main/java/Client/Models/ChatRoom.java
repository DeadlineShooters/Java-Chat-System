package Client.Models;

import java.sql.Timestamp;
import java.util.UUID;

public class ChatRoom {
    String id, name;
    Timestamp createdAt;
//    Map<String, Boolean> members = new HashMap<>(); // true means the member is an ADMIN
//    List<Message> messages = new ArrayList<>();
    public ChatRoom(String name) {
        this.id = UUID.randomUUID().toString();;
        this.name = name;
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    public String getId() {return id;}
    public String getName() {return name;}
    public Timestamp getCreatedAt() {return createdAt;}
    public void setId(String id) {this.id = id;}
    public void setName(String name) {this.name = name;}
    public void setCreatedAt(Timestamp createdAt) {this.createdAt = createdAt;}
}
