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
    public int createInventory() {
        try {
            Inventory inv = new Inventory(true, true);

            invDAO.createInventory(inv);
            invView.displayMessage("Inventory created successfully with ID: " + inv.getInventoryId());
            return inv.getInventoryId();
        } catch (SQLException e) {
            invView.displayError("Error creating user: " + e.getMessage());
            return -1;
        } catch (Exception e) {
            invView.displayError("Unexpected error: " + e.getMessage());
            return -1;
        }
    }
}

/* 
public int getExistingInventoryId() {
        try {
            Inventory inv = invDAO.getExistingInventory();
            if (inv != null) {
                return inv.getInventoryId();
            } else {
                invView.displayError("No existing inventory found.");
                return -1;
            }
        } catch (SQLException e) {
            invView.displayError("Error retrieving existing inventory: " + e.getMessage());
            return -1;
        } catch (Exception e) {
            invView.displayError("Unexpected error: " + e.getMessage());
            return -1;
        }
    }
}


*/
