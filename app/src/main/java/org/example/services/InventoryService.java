package org.example.services;

import java.sql.SQLException;

import org.example.database.DAOResult;
import org.example.database.InventoryDAO;
import org.example.database.ItemDAO;
import org.example.model.Inventory;
import org.example.model.Item;

public class InventoryService {
    // Initialize all the needed DAOs

    // Build the constructor with said DAOs

    /*
     * No createInventory method is implemented here because we wouldn't create
     * an inventory from nothing, we do that operation when creating a new user
     * or group but not outside of it so rightfully those operations are andled
     * in the corresponding services aka UserService for user inv and GroupService
     * for group inv
     */

    // EXAMPLE
    public ServiceResult getItemsInInvetory(int invenotryId) {
        try {
            // Implement the logic here
            System.err.println("logica da implementare");

            return new ServiceResult(true, "");

        // } catch (SQLException e) {
        //     return new ServiceResult(false, "Error: " + e.getMessage());
        } catch (Exception e) {
            return new ServiceResult(false, "Unexpected error: " + e.getMessage());
        }
    }

}
