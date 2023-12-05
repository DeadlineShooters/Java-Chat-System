package Client.Admin.Repository;

import java.sql.*;

public class SessionRepository {
    private Connection con;


    public int getAppOpens(Timestamp start, Timestamp end) {
        String sql = "SELECT COUNT(*) FROM Session WHERE login_time BETWEEN ? AND ?";
        try (PreparedStatement stmt = con.prepareStatement(sql);) {
            stmt.setTimestamp(1, start);
            stmt.setTimestamp(2, end);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
