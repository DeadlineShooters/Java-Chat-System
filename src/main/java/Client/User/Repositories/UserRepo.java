package Client.User.Repositories;

import Client.Models.User;
import Client.User.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;

public class UserRepo {
    private static Connection conn;
    private static Statement stmt;
    public UserRepo() {
        conn = ConnectionManager.getConnection();
        try {
            stmt = conn.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static boolean add(String username, String email, String password) {
        try {
            ResultSet resultSet = stmt.executeQuery("select * from user where username=\""+username+"\"");
            if (resultSet.next()) {
                return false;
            }
            Timestamp createdAt = new Timestamp(System.currentTimeMillis());
            String sql = "insert into user (username, password, email, created_at) values (?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, username);
                stmt.setString(2, password);
                stmt.setString(3, email);
                stmt.setTimestamp(4, createdAt);
                stmt.execute();
//                stmt.close();
                System.out.println("success");
            } catch (SQLException exc) {
                exc.printStackTrace();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
    public static boolean authen(String username, String password) {
        try {
            ResultSet resultSet = stmt.executeQuery("select * from user where username=\""+username+"\"");
            if (!resultSet.next()) {
                return false;
            }

            User user = User.fromResultSet(resultSet);
            assert user != null;
            if (!username.equals(user.username()))
                return false;
            if (!User.encryptPassword(password).equals(user.password()))
                return false;
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static User getOne(String username) {
        try {
            ResultSet resultSet = stmt.executeQuery("select * from user where username=\""+username+"\"");
            resultSet.next();
            return User.fromResultSet(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static ArrayList<String> findUsers(String prompt) {
        ArrayList<String> usernames = new ArrayList<>();
        try {
            ResultSet rs = stmt.executeQuery("SELECT username FROM user where username like \""+prompt+"\"");
            while (rs.next()) {
                usernames.add(rs.getString("username"));
            }
        } catch (SQLException ex) {
            System.out.println("Failed to get users.");
        }
        return usernames;
    }


}
