package Client.Admin.Repository;

import Client.ConnectionManager;
import Client.Models.Session;
import Client.Models.User;
import Client.Models.UserActivity;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SessionRepository {
    private Connection con;

    public SessionRepository(){
        this.con = ConnectionManager.getConnection();
    }

    public int getAppOpens(String username) {
        String sql = "SELECT COUNT(*) FROM Session WHERE username = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql);) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getPeopleChatted(String username) {
        String sql = "SELECT SUM(userschatcount) FROM session WHERE username = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql);) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getGroupsChatted(String username) {
        String sql = "SELECT SUM(groupschatcount) FROM session WHERE username = ?;";
        try (PreparedStatement stmt = con.prepareStatement(sql);) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public ArrayList<UserActivity> getUsersActivity(Timestamp startDatetime, Timestamp endDatetime) {
        ArrayList<UserActivity>activities = new ArrayList<>();
        String query = "SELECT u.username, " +
                "COUNT(s.logintime) AS appopens, " +
                "COALESCE(SUM(s.userschatcount), 0) AS numberofpeoplechatted, " +
                "COALESCE(SUM(s.groupschatcount), 0) AS numberofgroupschatted " +
                "FROM user u " +
                "LEFT JOIN session s ON u.username = s.username " +
                "WHERE s.logintime BETWEEN ? AND ?" +
                "GROUP BY u.username " +
                "ORDER BY u.username";


        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setTimestamp(1, startDatetime);
            preparedStatement.setTimestamp(2, endDatetime);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String username = resultSet.getString("username");
                int appOpens = resultSet.getInt("appopens");
                int peopleChatted = resultSet.getInt("numberofpeoplechatted");
                int groupsChatted = resultSet.getInt("numberofgroupschatted");

                activities.add(new UserActivity(username, appOpens, peopleChatted, groupsChatted));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return activities;
    }
    public ArrayList<UserActivity> getUsersActivity(ArrayList<User> allUsers) {
        ArrayList<UserActivity>activities = new ArrayList<>();

        for (User user : allUsers){
            activities.add(new UserActivity(user.username(), getAppOpens(user.username()), getPeopleChatted(user.username()), getGroupsChatted(user.username())));
        }

        return activities;
    }


    public ArrayList<Session> getSession(String username) {
        ArrayList<Session> sessions = new ArrayList<Session>();
        String sql = "select * from session where username = '" + username + "'";
        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                sessions.add(Session.fromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sessions;
    }

    public ArrayList<Session> getSessions() {
        ArrayList<Session> sessions = new ArrayList<Session>();
        String sql = "select * from session";
        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                sessions.add(Session.fromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sessions;
    }

    public List<Map<String, Object>> getSessionsForYear(int year) {
        List<Map<String, Object>> sessions = new ArrayList<>();
        String query = "SELECT * FROM session WHERE YEAR(logintime) <= ? AND (YEAR(logouttime) >= ? OR logouttime IS NULL)";

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1, year);
            preparedStatement.setInt(2, year);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Timestamp logoutTime = resultSet.getTimestamp("logouttime");
                if (logoutTime == null) {
                    // Set logouttime to the current datetime if it's null
                    logoutTime = Timestamp.valueOf(LocalDateTime.now());
                }
                Map<String, Object> session = Map.of(

                        "logintime", resultSet.getTimestamp("logintime"),
                        "logouttime", logoutTime,

                        "username", resultSet.getString("username")
                );
                sessions.add(session);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sessions;
    }


    public void startSession(String username) {
        String sql = "insert into session (username, logintime) values (?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            stmt.execute();
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
    }

}


