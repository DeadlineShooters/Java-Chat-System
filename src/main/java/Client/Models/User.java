package Client.Models;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;

public class User {
    String username, name, email, address;
    private String password;
    char gender;
    Date dob;
    Timestamp createdAt;
    boolean status, isLocked;
    HashSet<User> friends = new HashSet<>();
    HashSet<User> blockedUsers = new HashSet<>();

    User(String email, String username, String password) { // for creating new account
        this.email = email;
        this.username = username;
        this.password = encodePassword(password);
        createdAt = new Timestamp(System.currentTimeMillis());
    }

    public User(String username, String name, String email, String address, char gender, Date dob, Timestamp createdAt, boolean isLocked) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.address = address;
        this.gender = gender;
        this.dob = dob;
        this.createdAt = createdAt;
        this.isLocked = isLocked;
    }

    String encodePassword(String password) {
        String encodedPwd = password;
        return encodedPwd;
    }
    String decodePassword(String password) {
        return password;
    }

    void setPassword(String password) { this.password = password; }
    String getPassword() { return password; }
}
