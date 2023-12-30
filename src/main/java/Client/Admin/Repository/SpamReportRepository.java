package Client.Admin.Repository;

import java.util.ArrayList;
import java.sql.*;
import java.time.LocalDateTime;

import Client.ConnectionManager;
import Client.Models.SpamReport;

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
