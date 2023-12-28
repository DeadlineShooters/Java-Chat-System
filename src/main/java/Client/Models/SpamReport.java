package Client.Models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public record SpamReport(Timestamp reportTime, String reportedUser, String sender) {
    public static SpamReport fromResultSet(ResultSet resultSet) {
        try {
            return new SpamReport(
                    resultSet.getTimestamp("report_time"),
                    resultSet.getString("reported_user"),
                    resultSet.getString("sender"));
        } catch (SQLException ex) {
            System.out.println("Failed to create spam report.");
            return null; 
        }
    }
}
