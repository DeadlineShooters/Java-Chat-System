package Client.Models;

public record Friend(String user1, String user2) {

    // Overloaded constructor to swap user1 and user2
}

// USE javachatsystem;
// CREATE TABLE Friend (
//     user1 VARCHAR(255),
//     user2 VARCHAR(255)
// );
// INSERT INTO Friend (user1, user2)
// VALUES ('user1', 'user2'),
//        ('user3', 'user4');