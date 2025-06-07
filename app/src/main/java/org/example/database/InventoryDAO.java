package org.example.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.example.model.Inventory;

/**
 * Data Access Object for Inventory entity
 * Handles all database operations for INVENTORY table
 */
public class InventoryDAO {

    /**
     * Create a new user in the database
     * 
     * @param user User object to create
     * @throws SQLException if database operation fails
     */
    public void createInventory(Inventory inv) throws SQLException {
        String sql = "INSERT INTO INVENTARIO (Pubblico, Tipo) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setBoolean(1, inv.isPubblico());
            stmt.setBoolean(2, inv.isTipo());

            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        inv.setInventoryId(rs.getInt(1));
                    }
                }
            }
        }
    }
}

    /**
     * Get existing inventory from the database
     * 
     * @return Inventory object if found, null otherwise
     * @throws SQLException if database operation fails
     */

     /* 
    public Inventory getExistingInventory() throws SQLException {
        String sql = "SELECT Inventory_id FROM INVENTARIO WHERE User_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                Inventory inv = new Inventory();
                inv.setInventoryId(rs.getInt("Inventory_id"));
                inv.setPubblico(rs.getBoolean("Pubblico"));
                inv.setTipo(rs.getBoolean("Tipo"));
                return inv;
            }
        }
        return null;

    }*/