package org.example.controller;

import java.util.List;

import org.example.model.Item;
import org.example.model.User;
import org.example.services.InventoryService;
import org.example.services.ServiceResult;
import org.example.services.TradeService;
import org.example.services.UsersInventoryService;
import org.example.view.DynamicPanelManager;
import org.example.view.ViewManager;
import org.example.view.panels.PersonalInventoryPanel;
import org.example.view.panels.UsersInventoryPanel;
import org.example.view.panels.TradePanel;

/**
 * Inventory Controller Class
 * Handles business logic and coordinates between Model and View
 */
public class InventoryController {
    private InventoryService inventoryService;
    private ViewManager viewManager;
    private DynamicPanelManager dynamicPanelManager;
    private UsersInventoryService usersInventoryService;
    private TradeService tradeService;

    public InventoryController(InventoryService inventoryService, ViewManager viewManager,
            DynamicPanelManager dynamicPanelManager, UsersInventoryService usersInventoryService,
            TradeService tradeService) {
        this.inventoryService = inventoryService;
        this.viewManager = viewManager;
        this.dynamicPanelManager = dynamicPanelManager;
        this.usersInventoryService = usersInventoryService;
        this.tradeService = tradeService;
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

            handleUsersInventoryUpdate();
        } catch (Exception e) {
            System.out.println("Fatal error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void handleUsersInventoryUpdate() {
        try {
            ServiceResult result = inventoryService.getCurrentUserItems();

            // Validate if the service returned any items
            if (!result.isSuccess()) {
                System.out.println(result.getMessage());
            }

            // Pass the list of items to the view so that it can update via a special
            // constructor that handles the updates.
            // Reset the action listerners for the new pannel
            UsersInventoryPanel updatedUsersInventoryPanel = new UsersInventoryPanel();
            updatedUsersInventoryPanel.setTradeRequestListener(new UsersInventoryPanel.TradeRequestListener() {
                public void onTradeRequest(User targetUser) {
                    handleTradeRequestUpdate(targetUser);
                    viewManager.show("trade");
                }
            });

            dynamicPanelManager.setUsersInventoryPanel(updatedUsersInventoryPanel);
            dynamicPanelManager.updateHomePanel();

        } catch (Exception e) {
            System.out.println("Fatal error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // call this method inside the handler for the view of the users inventories
    // when the trade button is sent for that user
    public void handleTradeRequestUpdate(User targetUser) {
        try {
            ServiceResult currentUserResult = inventoryService.getCurrentUserItems();
            if (!currentUserResult.isSuccess()) {
                System.out.println(currentUserResult.getMessage());
            }

            ServiceResult receiverUserData = usersInventoryService.getUserInventoryById(targetUser.getUserId());

            // the service result passed to this handle should already have the info about
            // the user we want to trade with, we simply have to pass it to the view which
            // will handle the two types of result

            TradePanel updatedTradePanel = new TradePanel(currentUserResult, receiverUserData, targetUser);
            updatedTradePanel.setActionListener(new TradePanel.UserActionListener() {
                public void onTrade(List<Item> offeredItems, List<Item> wantedItems) {
                    handleTradeProposalRegistration(offeredItems, wantedItems, targetUser);
                    viewManager.show("home");
                }

                public void onExit() {
                    viewManager.show("home");
                }
            });

            dynamicPanelManager.setTradePanel(updatedTradePanel);
            dynamicPanelManager.updateTradePanel();

        } catch (Exception e) {
            System.out.println("Fatal error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void handleTradeProposalRegistration(List<Item> offeredItems, List<Item> wantedItems, User targetUser) {
        try {
            ServiceResult result = tradeService.registerTradeRequest(offeredItems, wantedItems, targetUser);

            if (result.isSuccess()) {
                System.out.println(result.getMessage());
                // maybe show minipanel with the success of the proposal
                viewManager.show("home");
            } else {
                // View displays an error and doesn't go forwards
                System.out.println(result.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Fatal error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
