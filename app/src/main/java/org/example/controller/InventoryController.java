package org.example.controller;

import java.sql.SQLException;

import org.example.database.InventoryDAO;
import org.example.model.Inventory;
import org.example.view.UserView;

/**
 * Inventory Controller Class
 * Handles business logic and coordinates between Model and View
 */
public class InventoryController {
    private InventoryDAO invDAO;
    private UserView invView;

    public InventoryController(InventoryDAO invDAO, UserView invView) {
        this.invDAO = invDAO;
        this.invView = invView;
    }

    /**
     * Create a new inventory
     */
    public void createInventory() {
        // Create Inventory
        Inventory inv;

        try {
            inv = new Inventory();

            invDAO.createInventory(inv);
            invView.displayMessage("Inventory created successfully with ID: " + inv.getInventoryId());

        } catch (SQLException e) {
            invView.displayError("Error creating user: " + e.getMessage());
        } catch (Exception e) {
            invView.displayError("Unexpected error: " + e.getMessage());
        }
    }
}
