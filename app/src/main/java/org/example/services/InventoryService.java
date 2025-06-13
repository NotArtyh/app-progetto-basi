package org.example.services;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List; // Maybe remove later used for this impl
import java.util.Map;

import org.example.SessionManager;
import org.example.database.DAOResult;
import org.example.database.InventoryDAO;
import org.example.database.ItemDAO;
import org.example.database.MediaDAO;
import org.example.model.Inventory;
import org.example.model.Item;

public class InventoryService {
    // Initialize all the needed DAOs
    private InventoryDAO inventoryDAO;
    private ItemDAO itemDAO;
    private MediaDAO mediaDAO;

    // Build the constructor with said DAOs
    public InventoryService(InventoryDAO inventoryDAO, ItemDAO itemDAO, MediaDAO mediaDAO) {
        this.inventoryDAO = inventoryDAO;
        this.itemDAO = itemDAO;
        this.mediaDAO = mediaDAO;
    }

    /*
     * No createInventory method is implemented here because we wouldn't create
     * an inventory from nothing, we do that operation when creating a new user
     * or group but not outside of it so rightfully those operations are andled
     * in the corresponding services aka UserService for user inv and GroupService
     * for group inv
     */

    // EXAMPLE
    public ServiceResult getCurrentUserItems() {
        try {
            if (!SessionManager.getInstance().isUserLoggedIn()) {
                return new ServiceResult(false, "User not logged in.");
            }
            int currentUserId = SessionManager.getInstance().getCurrenUser().getUserId();

            List<Item> items = itemDAO.getItemsByUserId(currentUserId);
            if (items.isEmpty()) {
                return new ServiceResult(false, "User has no items in the inventory.");
            }

            // pair the corresponding title to each meadia
            Map<Item, String> titletItemsMap = new LinkedHashMap<>();
            for (Item item : items) {
                String title = mediaDAO.getTitleById(item.getMediaId());
                titletItemsMap.put(item, title);
            }

            // System.out.println(titletItemsMap);

            ServiceResult result = new ServiceResult(true, "View data retrieved.");
            result.setViewDataPayload(titletItemsMap);
            return result;

        } catch (SQLException e) {
            return new ServiceResult(false, "Error: " + e.getMessage());
        } catch (Exception e) {
            return new ServiceResult(false, "Unexpected error: " + e.getMessage());
        }
    }

    public ServiceResult getUserItemsByID() {
        try {

            List<Item> items = itemDAO.getItemsByUserId(userId);
            


        } catch (SQLException e) {
            return new ServiceResult(false, "Error: " + e.getMessage());
        } catch (Exception e) {
            return new ServiceResult(false, "Unexpected error: " + e.getMessage());
        }
    }
}
