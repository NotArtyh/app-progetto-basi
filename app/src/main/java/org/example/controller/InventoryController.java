package org.example.controller;

import org.example.services.InventoryService;
import org.example.services.ServiceResult;
import org.example.view.DynamicPanelManager;
import org.example.view.ViewManager;
import org.example.view.panels.PersonalInventoryPanel;

/**
 * Inventory Controller Class
 * Handles business logic and coordinates between Model and View
 */
public class InventoryController {
    private InventoryService inventoryService;
    private ViewManager viewManager;
    private DynamicPanelManager dynamicPanelManager;

    public InventoryController(InventoryService inventoryService, ViewManager viewManager,
            DynamicPanelManager dynamicPanelManager) {
        this.inventoryService = inventoryService;
        this.viewManager = viewManager;
        this.dynamicPanelManager = dynamicPanelManager;
    }

    /**
     * Method that handles the update of a personal inventory pannel
     * It calls the invenotryService method for handling the retrieval of items
     * in the current session user and then passes the positive result to the
     * the new pannel which will repalce the old one.
     */
    public void handlePersonalInventoryUpdate() {
        try {
            ServiceResult result = inventoryService.getCurrentUserItems();

            // Validate if the service returned any items
            if (!result.isSuccess()) {
                System.out.println(result.getMessage());
            }

            // Pass the list of items to the view so that it can update via a special
            // constructor that handles the updates.
            // Reset the action listerners for the new pannel
            PersonalInventoryPanel updatedPersonalInventoryPanel = new PersonalInventoryPanel(result);
            updatedPersonalInventoryPanel.setActionListener(new PersonalInventoryPanel.UserActionListener() {
                public void onExit() {
                    viewManager.show("home"); // go back to home view
                }
            });

            dynamicPanelManager.setPersonalInventoryPanel(updatedPersonalInventoryPanel);
            dynamicPanelManager.updatePersonalInventoryPanel();

        } catch (Exception e) {
            System.out.println("Fatal error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
