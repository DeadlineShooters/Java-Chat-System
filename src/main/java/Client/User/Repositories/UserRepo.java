package Client.User.Repositories;

import Client.ConnectionManager;
import Client.Models.User;

import java.sql.*;
import java.util.HashMap;

public class UserRepo {
    private static Connection conn;

    static {
        // Static block to initialize the connection when the class is loaded
        conn = ConnectionManager.getConnection();
    }
    public static Boolean usernameExisted(String username) {
        String sql = "select * from user where username = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static Boolean emailExisted(String email) {
        String sql = "select * from user where email = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean add(String username, String email, String password) {
        try {
            Statement stmt = conn.createStatement();
//            ResultSet resultSet = stmt.executeQuery("select * from user where username=\"" + username + "\"");
//            if (resultSet.next()) {
//                return false;
//            }
            Timestamp createdAt = new Timestamp(System.currentTimeMillis());
            String sql = "insert into user (username, password, email, createdat) values (?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, username);
                ps.setString(2, password);
                ps.setString(3, email);
                ps.setTimestamp(4, createdAt);
                ps.execute();
                // stmt.close();
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
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery("select * from user where username=\"" + username + "\"");
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
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery("select * from user where username=\"" + username + "\"");
            resultSet.next();
            return User.fromResultSet(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static HashMap<String, Boolean> findUsers(String currentUsername, String prompt) {
        HashMap<String, Boolean> users = new HashMap<>();
        String sql = "SELECT username, status FROM user where username like ? and username != ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + prompt + "%");
            ps.setString(2, currentUsername);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                // Check for null status before using getBoolean
                boolean isOnline = rs.getBoolean("status");
                users.put(rs.getString("username"), isOnline);
            }

        } catch (SQLException ex) {
            System.out.println("Failed to get users.");
        }
        return users;
    }

    public static Boolean isOnline(String username) {
        String sql = "select status from user where username = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getBoolean("status");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setStatus(String username, Boolean status) {
        String sql = "update user set status = ? where username = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, status);
            ps.setString(2, username);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static User findByEmail(String email) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery("select * from user where email = '" + email + "'");
            if (resultSet.next()) {
                return User.fromResultSet(resultSet);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updatePassword(String username, String password) {
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("update user set password = '" + User.encryptPassword(password) + "' where username = '" + username + "'");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static Boolean isLocked(String username) {
        String sql = "select isLocked from user where username = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getBoolean("islocked");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
