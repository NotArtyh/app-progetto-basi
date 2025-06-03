package org.example.database;

import java.sql.*;

public class DatabaseManager {
    
    private static final String URL = "jdbc:mysql://100.66.102.13:3306/BEL";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "cazzineri";

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