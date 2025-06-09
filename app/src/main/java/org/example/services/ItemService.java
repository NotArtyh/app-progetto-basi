package org.example.services;

import java.sql.SQLException;
import java.time.LocalDateTime;

import org.example.SessionManager;
import org.example.database.DAOResult;
import org.example.database.ItemDAO;
import org.example.database.MediaDAO;
import org.example.model.Item;

public class ItemService {
    private ItemDAO itemDAO;
    private MediaDAO mediaDAO;

    public ServiceResult registerItem(String title, String condition, String note) {
        try {
            // Need to implement all the logic
            DAOResult mediaResult = mediaDAO.getIdTitle(title);
            if (!mediaResult.isSuccess()) {
                return new ServiceResult(false, "Failed to retrieve the mediaId via its title");
            }

            int mediaId = mediaResult.getId(); // should be the one that matches the corresponding title
            int inventoryId = SessionManager.getInstance().getCurrenUser().getInventoryId(); // should be the one that matches the current session user

            // Usign the current date and time for the acquistion date
            LocalDateTime dateCurrent = LocalDateTime.now();

            DAOResult itemResult = itemDAO.createItem(new Item(mediaId, inventoryId, condition, note, dateCurrent));

            return new ServiceResult(true, "New item registration Succeeded");

        } catch (SQLException e) {
            return new ServiceResult(false, "Error creating item: " + e.getMessage());
        } catch (Exception e) {
            return new ServiceResult(false, "Unexpected error: " + e.getMessage());
        }
    }
}
