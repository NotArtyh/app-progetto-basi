package org.example.controller;

import javax.swing.JPanel;

import org.example.services.ServiceResult;
import org.example.services.UserService;
import org.example.view.HomePanelManager;
import org.example.view.ViewManager;
import org.example.view.components.UserBar;
import org.example.view.panels.HomePanel;

/**
 * User Controller Class
 * coordinates between Model and View
 */
public class UserController {
    private UserService userService;
    private ViewManager viewManager;
    private HomePanelManager homePanelManager;

    public UserController(UserService userService, ViewManager viewManager, HomePanelManager homePanelManager) {
        this.userService = userService;
        this.viewManager = viewManager;
        this.homePanelManager = homePanelManager;
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
                viewManager.show("home");
            } else {
                // View displays an error and doesn't go forwards
                System.out.println(result.getMessage());
            }
        } catch (Exception e) {
            // Display the fatal error on the view
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
            // Display the fatal error on the view
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
            // Display the fatal error on the view
        }
    }

    public void handleUserInfoUpdate() {
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

        homePanelManager.setUserBar(updatedUserBar);
        homePanelManager.updateHomePanel();
    }
}