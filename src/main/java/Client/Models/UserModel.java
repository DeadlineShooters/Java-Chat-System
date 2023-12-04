package Client.Models;

import Client.Admin.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserModel {
    private Connection con;

    public UserModel() {
        this.con = ConnectionManager.getConnection();
    }

    public ResultSet getUsers() {
        ResultSet rs = null;
        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM User");
        } catch (SQLException ex) {
            System.out.println("Failed to get users.");
        }
        return rs;
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
                User user = new User(
                        resultSet.getString("username"),
                        resultSet.getString("full_name"),
                        resultSet.getString("address"),
                        resultSet.getDate("birth_date"),
                        resultSet.getString("gender").charAt(0),
                        resultSet.getString("email"),
                        resultSet.getBoolean("status"),
                        resultSet.getString("password"),
                        resultSet.getTimestamp("created_at"),
                        resultSet.getBoolean("is_locked")
                );

                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception appropriately
        }

        return userList;
    }

    public void close() {
        ConnectionManager.closeConnection();
    }
}
