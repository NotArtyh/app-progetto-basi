package org.example.controller;

import org.example.view.*;
import org.example.view.components.UserBar;
import org.example.view.panels.*;

public class AppController {
    private final ViewManager viewManager;
    private final UserController userController;
    private final ItemController itemController;

    public AppController(ViewManager viewManager, UserController userController, ItemController itemController) {
        this.viewManager = viewManager;
        this.userController = userController;
        this.itemController = itemController;
        setupViews();
    }

    // prepares the action listeners for the signInPannel
    // add new listeners as needed for each pannel
    private void setupViews() {

        // SignIn panel
        SignInPanel signInPanel = new SignInPanel();
        signInPanel.setActionListener(new SignInPanel.UserActionListener() {
            public void onRegistration() {
                viewManager.show("registration");
            }

            public void onLogin() {
                viewManager.show("login");
            }

            public void onExit() {
                System.exit(0);
            }
        });

        // LogIn panel
        LogInPanel logInPanel = new LogInPanel();
        logInPanel.setActionListener(new LogInPanel.UserActionListener() {
            public void onLoginSubmit(String username, String password) {
                // Call the UserController for handling the auth
                userController.handleUserAuthentication(username, password);
            }

            public void onLoginCancel() {
                viewManager.show("signIn"); // Go back to sign in
            }
        });

        // Register panel
        RegistrationPanel registrationPanel = new RegistrationPanel();
        registrationPanel.setActionListener(new RegistrationPanel.UserActionListener() {
            public void onRegisterSubmit(String name, String surname, String sex, String phoneNumber,
                    String stateResidency,
                    String province, String cap, String street, String streetCode, String username, String email,
                    String password) {
                // Call the UserCOntroller for handling the registration
                userController.handleUserRegistration(name, surname, sex, phoneNumber, stateResidency, province, cap,
                        street, streetCode, username, email, password);
            }

            public void onRegisterCancel() {
                viewManager.show("signIn"); // Go back to sign in
            }
        });

        UserBar userBar = new UserBar();
        userBar.setActionListener(new UserBar.UserActionListener() {
            // have to impl listeners
        });

        UsersInventoryPanel usersInventoryPanel = new UsersInventoryPanel();
        usersInventoryPanel.setActionListener(new UsersInventoryPanel.UserActionListener() {
            // have to impl listeners
        });


        OperationsPanel operationsPanel = new OperationsPanel();
        operationsPanel.setActionListener(new OperationsPanel.UserActionListener() {
            public void onAddItem() {
                // change view to Add item panel
            }

            public void onViewInventory() {
                // change view to inventory view panel
            }

            public void onTradeItem() {
                // change view to trade panel
            }
        });

        HomePanel homePanel = new HomePanel(userBar, usersInventoryPanel, operationsPanel);
        // this pannel has no listeners.


        // Register all the pannels
        viewManager.registerPanel("signIn", signInPanel);
        viewManager.registerPanel("login", logInPanel);
        viewManager.registerPanel("registration", registrationPanel);
        viewManager.registerPanel("home", homePanel);

        // start the app on the signIn pannel
        // here i should had the logic for displaying the two pannels side by side maybe
        viewManager.show("signIn");
    }
}
