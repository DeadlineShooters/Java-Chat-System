package Client.Repository;

import Client.Admin.ConnectionManager;
import Client.Models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;

public class UserRepository {
    private Connection con;

    public UserRepository() {
        this.con = ConnectionManager.getConnection();
    }

    public ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM User");
            while (rs.next()) {
                users.add(User.fromResultSet(rs));
            }
        } catch (SQLException ex) {
            System.out.println("Failed to get users.");
        }
        return users;
    }

    public Date getOldestDate() {
        Date oldestDate = null;
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT MIN(created_at) AS oldest_date FROM User");
            // move to the next row to convert to DATE because DatePicker needs DATE
            if (rs.next()) {
                Timestamp timestamp = rs.getTimestamp("oldest_date");
                if (timestamp != null) {
                    oldestDate = new Date(timestamp.getTime());
                }
            }
        } catch (SQLException ex) {
            System.out.println("Failed to get the oldest date.");
        }
        return oldestDate;
    }

    public ArrayList<User> getUsersByDateRange(Date startDate, Date endDate) {
        ArrayList<User> userList = new ArrayList<>();

        String sql = "SELECT * FROM User WHERE created_at BETWEEN ? AND ?";

        try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {

            // Set the start and end dates in the prepared statement
            preparedStatement.setDate(1, new java.sql.Date(startDate.getTime()));
            preparedStatement.setDate(2, new java.sql.Date(endDate.getTime()));

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                User user = User.fromResultSet(resultSet);

                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception appropriately
        }

        return userList;
    }

    public int getOldestYear() {
        Date oldestDate = getOldestDate();
        if (oldestDate != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(oldestDate);
            return calendar.get(Calendar.YEAR);
        }
        return -1; // return -1 or throw an exception if the date is null
    }


    public void close() {
        ConnectionManager.closeConnection();
    }
}
