package Client.Admin.Repository;

import Client.ConnectionManager;
import Client.Models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;

public class UserRepository {
    private Connection con;

    public UserRepository() {
        this.con = ConnectionManager.getConnection();
    }

    public User getUser(String username) {
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM User where username = '" + username +"'");
            rs.next();
            return User.fromResultSet(rs);
        } catch (SQLException ex) {
            System.out.println("Failed to get users.");
        }
        return null;
    }

    public ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM user ORDER BY username;");
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
            ResultSet rs = stmt.executeQuery("SELECT MIN(createdat) AS oldestdate FROM user");
            // move to the next row to convert to DATE because DatePicker needs DATE
            if (rs.next()) {
                Timestamp timestamp = rs.getTimestamp("oldestdate");
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

        String sql = "SELECT * FROM user WHERE createdat BETWEEN ? AND ? ORDER BY username";

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
                User user = User.fromResultSet(resultSet);

                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userList;
    }

    public int fetchNumberOfFriends(String username) {
        String sql = "SELECT COUNT(*) AS friendcount FROM Friend WHERE user1 = ?";
        int numberOfFriends = 0;
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                numberOfFriends = resultSet.getInt("friendcount");
            }
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
        return numberOfFriends;
    }

    public int fetchNumberOfFriendsOfFriends(String username) {
        String sql = "SELECT count(*) as totalfriendsoffriends " +
                "FROM friend f1, friend f2 " +
                "where f1.user1 = ? and f1.user2 = f2.user1 and f2.user2 <> ?";
        int numberOfFriendsOfFriends = 0;

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, username);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                numberOfFriendsOfFriends = resultSet.getInt("totalfriendsoffriends");
            }
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
        return numberOfFriendsOfFriends;
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

    public void insert(String username, String password, String email, Timestamp createdAt) {
        String sql = "insert into user (username, password, email, createdat) values (?, ?, ?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, User.encryptPassword(password));
            stmt.setString(3, email);
            stmt.setTimestamp(4, createdAt);
            stmt.execute();
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
    }

    public void update(String username, String fullName, String address, Timestamp dob, String gender, String email, String password) {
        String sql = !password.equals("") ? "update user set fullname = ?, address = ?, birthdate = ?, gender = ?, email = ?, password = ? where username = ?"
        : "update user set fullname = ?, address = ?, birthdate = ?, gender = ?, email = ? where username = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, fullName);
            stmt.setString(2, address);
            stmt.setTimestamp(3, dob);
            stmt.setString(4, gender);
            stmt.setString(5, email);
            if (password.equals("")) {
                stmt.setString(6, username);
            }
            else {
                stmt.setString(6, User.encryptPassword(password));
                stmt.setString(7, username);
            }
            stmt.executeUpdate();
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
    }

    public void remove(String username) {
        String sql = "delete from user where username = '" + username + "'";
        try (Statement stmt = con.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
    }

    public void lock(String username, boolean isLocked) {
        String sql = "update user set islocked = '" + (isLocked ? 0 : 1) + "' where username = '" + username + "'";
        try (Statement stmt = con.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
    }

    public Date getCreatedDate(String username) {
        Date createdDate = null;

        String sql = "SELECT createdat FROM user WHERE username = ?";

        try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Timestamp timestamp = resultSet.getTimestamp("createdat");
                if (timestamp != null) {
                    createdDate = new Date(timestamp.getTime());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to get the created date for user: " + username);
        }

        return createdDate;
    }

}
