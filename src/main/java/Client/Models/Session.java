package Client.Models;

import java.sql.Timestamp;

public class Session {
    String username;
    Timestamp loginTime, logoutTime;
    int usersChattedCount, groupsChattedCount;
}
