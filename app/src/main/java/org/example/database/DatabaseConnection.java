package org.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://";  // server url - localhost if local server
    private static final String USERNAME = "";          // user for the database
    private static final String PASSWORD = "";          // password for that user

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            System.out.println("✅ Connected to database successfully!");
            System.out.println("Database: " + conn.getCatalog());
            return true;
        } catch (SQLException e) {
            System.out.println("❌ Connection failed: " + e.getMessage());
            return false;
        }
    }
}
