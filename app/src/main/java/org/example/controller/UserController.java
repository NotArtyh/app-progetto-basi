package org.example.controller;

import org.example.services.ServiceResult;
import org.example.services.UserService;
import org.example.view.ViewManager;

/**
 * User Controller Class
 * coordinates between Model and View
 */
public class UserController {
    private UserService userService;
    private ViewManager viewManager;

    public UserController(UserService userService, ViewManager viewManager) {
        this.userService = userService;
        this.viewManager = viewManager;
    }

    public void handleUserCreation() {
        try {
            // Get the data from the view - even as an argument for this function could work
            // but like this should be better

            // Call service for creating a user
            ServiceResult result = userService.createUser(null, null, null, null, null, null, null, null, null, null,
                    null, null);

            // Update the view based on result
            if (result.isSuccess()) {
                // View goes forward - Ok
            } else {
                // View displays an error and doesn't go forwards
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
                // View goes forward - ok show dashboard with inventory view etc
                viewManager.show("");
            } else {
                // View displays an error and doesn't go forward
                viewManager.show("");
            }
        } catch (Exception e) {
            // Display the fatal error on the view
        }
    }
}