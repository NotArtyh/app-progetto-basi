package org.example.controller;

import java.sql.SQLException;
import java.time.LocalDate;

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

    public int createItem(int inventoryId, int mediaId, String condition, String note, LocalDate data_acquisizione) {
        try {
            Item item = new Item(mediaId, inventoryId, condition, note, data_acquisizione);

            itemDAO.createItem(item);
            itemView.displayMessage("User created successfully with ID: " + item.getItemId());
            return item.getItemId();
        } catch (SQLException e) {
            itemView.displayError("Error creating user: " + e.getMessage());
            return -1;
        } catch (Exception e) {
            itemView.displayError("Unexpected error: " + e.getMessage());
            return -1;
        }

    }
}
