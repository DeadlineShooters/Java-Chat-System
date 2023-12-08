package Client;

import java.sql.*;

public class ConnectionManager {
    private static String url = "jdbc:mysql://localhost:3306/javachatsystem";
    private static String username = "root";
    private static String password = "mysql";
    private static Connection con;

    public static Connection getConnection() {

        try {
            con = DriverManager.getConnection(url, username, password);
        } catch (SQLException ex) {
            System.out.println("Failed to create the database connection.");
        }

        return con;
    }

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