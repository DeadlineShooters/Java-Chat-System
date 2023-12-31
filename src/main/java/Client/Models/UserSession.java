package Client.Models;

import java.sql.Timestamp;

public class UserSession {
    public String username;
    public Timestamp loginTime, logoutTime;
    public int usersChattedCount, groupsChattedCount;

    public UserSession(String username, Timestamp loginTime, Timestamp logoutTime, int usersChattedCount, int groupsChattedCount) {
        this.username = username;
        this.loginTime = loginTime;
        this.logoutTime = logoutTime;
        this.usersChattedCount = usersChattedCount;
        this.groupsChattedCount = groupsChattedCount;
    }
}
