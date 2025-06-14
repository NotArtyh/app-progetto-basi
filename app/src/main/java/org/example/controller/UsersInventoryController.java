package org.example.controller;

import org.example.services.InventoryService;
import org.example.services.UsersInventoryService;
import org.example.view.DynamicPanelManager;
import org.example.view.ViewManager;
import org.example.view.panels.UsersInventoryPanel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.example.SessionManager;
import org.example.database.UserDAO;
/**
 * Inventory Controller Class
 * Handles business logic and coordinates between Model and View
 */
import org.example.model.User;
public class UsersInventoryController {
    private InventoryService inventoryService;
    private ViewManager viewManager;
    private DynamicPanelManager dynamicPanelManager;
    private UsersInventoryService usersInventoryService;
    private UserDAO userDAO;
    private List<User> allUsers;

    public UsersInventoryController(UsersInventoryService usersInventoryService, ViewManager viewManager,
            DynamicPanelManager dynamicPanelManager) {
        this.usersInventoryService = usersInventoryService;
        this.viewManager = viewManager;
        this.dynamicPanelManager = dynamicPanelManager;
    }

    public void handleUsersInventoryUpdate() {

        try {

            this.allUsers = userDAO.getAllUsersExceptCurrent(SessionManager.getInstance().getCurrenUser().getUserId());
            System.out.println("Caricati " + allUsers.size() + " utenti");
        } catch (SQLException e) {
            System.err.println("Errore SQL: " + e.getMessage());
            e.printStackTrace();
            
            this.allUsers = new ArrayList<>();

        } catch (Exception e) {
            System.err.println("Errore generico: " + e.getMessage());
            e.printStackTrace();
            
            this.allUsers = new ArrayList<>();  
        }
    

            /*  Validate if the service returned any items
            if (!result.isSuccess()) {
                System.out.println(result.getMessage());
            }*/

            // Pass the list of items to the view so that it can update via a special
            // constructor that handles the updates.
            // Reset the action listerners for the new pannel
            UsersInventoryPanel updatedUsersInventoryPanel = new UsersInventoryPanel();
           
            /* 
            updatedUsersInventoryPanel.setActionListener(new UsersInventoryPanel.UserActionListener() {
                public void onTrade() {
                    viewManager.show("trade"); va al panel di trade
                }
            });
            */
            
            dynamicPanelManager.setusersInventoryPanel(updatedUsersInventoryPanel);
            dynamicPanelManager.updateUsersInventoryPanel();

       
        }
    }




