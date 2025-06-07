package org.example.controller;

import java.sql.SQLException;
import java.time.LocalDateTime;

import org.example.database.ItemDAO;
import org.example.model.Item;
import org.example.view.UserView;

public class ItemController {
    private ItemDAO itemDAO;
    private UserView itemView;

    public ItemController(ItemDAO itemDAO, UserView itemView) {
        this.itemDAO = itemDAO;
        this.itemView = itemView;
    }

    public int createItem(int mediaId, int inventoryId, String condition, String note, LocalDateTime dataAcquisizione) {
        try {
            Item item = new Item(mediaId, inventoryId, condition, note, dataAcquisizione);

            itemDAO.createItem(item);
            itemView.displayMessage("Item created successfully with ID: " + item.getItemId());
            return item.getItemId();
        } catch (SQLException e) {
            itemView.displayError("Error creating the item: " + e.getMessage());
            return -1;
        } catch (Exception e) {
            itemView.displayError("Unexpected error: " + e.getMessage());
            return -1;
        }

    }
}

