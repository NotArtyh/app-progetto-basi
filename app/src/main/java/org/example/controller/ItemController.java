package org.example.controller;

import java.sql.SQLException;

import org.example.services.ServiceResult;
import org.example.services.ItemService;
import org.example.view.ViewManager;

public class ItemController {
    private ItemService itemService;
    private ViewManager viewManager;

    public ItemController(ItemService itemService, ViewManager viewManager) {
        this.itemService = itemService;
        this.viewManager = viewManager;
    }

    public void handleItemRegistration(String title, String condition, String note) {
        try {
            ServiceResult result = itemService.registerItem(title, condition, note);

            if (result.isSuccess()) {
                // View goes forward - Ok
                System.out.println(result.getMessage());
                viewManager.show("home"); // We return to the homepage
            } else {
                // View displays an error and doesn't go forwards
                System.out.println(result.getMessage());
            }
        } catch (Exception e) {
            // Display the fatal error on the view
        }
    }
}
