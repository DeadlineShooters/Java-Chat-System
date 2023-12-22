package Client;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private static String url = "jdbc:mysql://localhost:3306/javachatsystem";
    private static String username = "root";
    private static String password = "Resil4skul";
    private static Connection con;

    // Private constructor to prevent instantiation from outside
    private ConnectionManager() {
    }

    // Double-checked locking for thread safety
    public static Connection getConnection() {
        if (con == null) {
            synchronized (ConnectionManager.class) {
                if (con == null) {
                    try {
                        con = DriverManager.getConnection(url, username, password);
                    } catch (SQLException ex) {
                        System.out.println("Failed to create the database connection.");
                    }
                }
            }
        }
        return con;
    }

    // Close the connection
    public static void closeConnection() {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (SQLException ex) {
            System.out.println("Failed to close the database connection.");
        }
    }
}
