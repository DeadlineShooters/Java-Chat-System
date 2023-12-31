package Client.Admin.Repository;

import java.util.ArrayList;
import java.util.Calendar;
import java.sql.*;
import java.time.LocalDateTime;

import Client.ConnectionManager;
import Client.Models.SpamReport;
import Client.Models.User;

public class SpamReportRepository {
    private Connection con;

    public SpamReportRepository() {
        this.con = ConnectionManager.getConnection();
    }

    public ArrayList<SpamReport> getSpamReports() {
        ArrayList<SpamReport> spamReports = new ArrayList<SpamReport>();
        String sql = "select * from spamReport";
        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                spamReports.add(SpamReport.fromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return spamReports;
    }

    public ArrayList<SpamReport> getSpamReportsByDateRange(Date startDate, Date endDate) {
        ArrayList<SpamReport> spamReports = new ArrayList<SpamReport>();

        String sql = "SELECT * FROM spamReport WHERE reporttime BETWEEN ? AND ? ORDER BY sender";

        try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {

            // Set the start and end dates in the prepared statement
            preparedStatement.setDate(1, new java.sql.Date(startDate.getTime()));
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(endDate);
            calendar.add(Calendar.DATE, 1);
            java.util.Date utilDate = calendar.getTime();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

            preparedStatement.setDate(2, sqlDate);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                spamReports.add(SpamReport.fromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return spamReports;
    }

    public void insert(String sender, String reportedUser) {
        String sql = "insert into spamreport (sender, reporteduser, reporttime) values (?, ?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, sender);
            stmt.setString(2, reportedUser);
            stmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            stmt.execute();
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
    }

    public void remove(String sender, Timestamp createAt) {
        String sql = "delete from spamreport where sender = ? and reporttime = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, sender);
            stmt.setTimestamp(2, createAt);
            stmt.execute();
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
    }
}
