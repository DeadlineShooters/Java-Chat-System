package Client.Admin.Repository;

import Client.ConnectionManager;
import Client.Models.User;
import Client.Models.UserActivity;

import java.sql.*;
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
        String sql = "SELECT SUM(users_chat_count) FROM session WHERE username = ?";
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
        String sql = "SELECT SUM(groups_chat_count) FROM session WHERE username = ?;";
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
                "COUNT(s.login_time) AS app_opens, " +
                "COALESCE(SUM(s.users_chat_count), 0) AS number_of_people_chatted, " +
                "COALESCE(SUM(s.groups_chat_count), 0) AS number_of_groups_chatted " +
                "FROM user u " +
                "LEFT JOIN session s ON u.username = s.username " +
                "WHERE s.login_time BETWEEN ? AND ?" +
                "GROUP BY u.username " +
                "ORDER BY u.username";


        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setTimestamp(1, startDatetime);
            preparedStatement.setTimestamp(2, endDatetime);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String username = resultSet.getString("username");
                int appOpens = resultSet.getInt("app_opens");
                int peopleChatted = resultSet.getInt("number_of_people_chatted");
                int groupsChatted = resultSet.getInt("number_of_groups_chatted");

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

    public List<Map<String, Object>> getSessionsForYear(int year) {
        List<Map<String, Object>> sessions = new ArrayList<>();
        String query = "SELECT * FROM session WHERE YEAR(login_time) <= ? AND (YEAR(logout_time) >= ? OR logout_time IS NULL)";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setInt(1, year);
            preparedStatement.setInt(2, year);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Map<String, Object> session = Map.of(
                        "login_time", resultSet.getTimestamp("login_time"),
                        "logout_time", resultSet.getTimestamp("logout_time"),
                        "username", resultSet.getString("username")
                );
                sessions.add(session);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sessions;
    }


}


