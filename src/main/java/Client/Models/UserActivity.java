package Client.Models;

import java.sql.*;

public record UserActivity(String username, int app_opens, int users_chat_count, int groups_chat_count) {
}
