package org.example.controller;

import org.example.services.ServiceResult;
import org.example.services.UserService;
import org.example.view.DynamicPanelManager;
import org.example.view.ViewManager;
import org.example.view.components.UserBar;
/**
 * User Controller Class
 * coordinates between Model and View
 */
public class UserController {
    private UserService userService;
    private ViewManager viewManager;
    private DynamicPanelManager dynamicPanelManager;
    private InventoryController inventoryController;


    public UserController(UserService userService, ViewManager viewManager, DynamicPanelManager dynamicPanelManager, 
            InventoryController inventoryController) {
        this.userService = userService;
        this.viewManager = viewManager;
        this.dynamicPanelManager = dynamicPanelManager;
        this.inventoryController = inventoryController;
    }

    public void handleUserRegistration(String name, String surname, String sex, String phoneNumber,
            String stateResidency, String province, String cap, String street, String streetCode, String username,
            String email, String password) {
        try {
            // Get the data from the view - arguments of this method

            // Call service for creating a user
            ServiceResult result = userService.registerUser(name, surname, sex, phoneNumber, stateResidency, province,
                    cap, street, streetCode, username, email, password);

            // Update the view based on result
            if (result.isSuccess()) {
                // View goes forward - Ok
                System.out.println(result.getMessage());
                handleUserAuthentication(username, password);
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

    public void handleUserAuthentication(String username, String password) {
        try {
            // Call service for user auth
            ServiceResult result = userService.authenticateUser(username, password);

            if (result.isSuccess()) {
                // View goes forward - ok show dashboard with inventory view etc;
                System.out.println(result.getMessage());
                handleUserInfoUpdate();
                viewManager.show("home");
            } else {
                // View displays an error and doesn't go forward
                System.out.println(result.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Fatal error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void handleUserLogOut() {
        try {
            ServiceResult result = userService.logOutUser();

            if (result.isSuccess()) {
                System.out.println(result.getMessage());
                viewManager.show("signIn");
            } else {
                // View displays an error and doesn't go forward
                System.out.println(result.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Fatal error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void handleUserInfoUpdate() {
        try {
            ServiceResult result = userService.getCurrentUserData();

            if (!result.isSuccess()) {
                System.out.println(result.getMessage());
                return;
            }

            UserBar updatedUserBar = new UserBar(result);
            updatedUserBar.setActionListener(new UserBar.UserActionListener() {
                public void onLogOut() {
                    handleUserLogOut();
                    viewManager.show("SignIn");
                }
            });

            dynamicPanelManager.setUserBar(updatedUserBar);
            inventoryController.handlePersonalInventoryUpdate();
            dynamicPanelManager.updateHomePanel();

        } catch (Exception e) {
            System.out.println("Fatal error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}