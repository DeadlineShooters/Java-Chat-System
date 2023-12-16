package Client.Admin.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.*;

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
}
