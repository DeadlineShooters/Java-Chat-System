package Client.Admin.Repository;

import Client.ConnectionManager;
import Client.Models.User;
import Client.Models.UserActivity;

import java.sql.*;
import java.util.ArrayList;

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
                "AND s.login_time BETWEEN ? AND ? " +
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

}


