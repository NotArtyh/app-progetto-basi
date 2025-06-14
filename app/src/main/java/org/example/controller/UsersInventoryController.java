package org.example.controller;

import org.example.services.InventoryService;
import org.example.services.UsersInventoryService;
import org.example.view.DynamicPanelManager;
import org.example.view.ViewManager;

/**
 * Inventory Controller Class
 * Handles business logic and coordinates between Model and View
 */
public class UsersInventoryController {
    private InventoryService inventoryService;
    private ViewManager viewManager;
    private DynamicPanelManager dynamicPanelManager;
    private UsersInventoryService usersInventoryService;

    public UsersInventoryController(UsersInventoryService usersInventoryService, ViewManager viewManager,
            DynamicPanelManager dynamicPanelManager) {
        this.usersInventoryService = usersInventoryService;
        this.viewManager = viewManager;
        this.dynamicPanelManager = dynamicPanelManager;
    }

    public void handleUsersInventoryUpdate() {

    }
}
