package Client.Models;

public record Friend(String user1, String user2) {

    // Overloaded constructor to swap user1 and user2
}

// USE javachatsystem;
// DROP TABLE IF EXISTS friend;
// CREATE TABLE friend (
//     user1 VARCHAR(255),
//     user2 VARCHAR(255),
//     PRIMARY KEY (user1, user2)
// );
// INSERT INTO Friend (user1, user2)
// VALUES ('user1', 'user2'), ('user2', 'user1'), ('user3', 'user4'), ('user4', 'user3');