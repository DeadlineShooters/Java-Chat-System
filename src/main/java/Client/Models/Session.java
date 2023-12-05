package Client.Models;
import java.sql.*;

public record Session(String username, Timestamp login_time, Timestamp logout_time, int users_chat_count, int groups_chat_count) {}
