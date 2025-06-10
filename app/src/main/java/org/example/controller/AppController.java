package org.example.controller;

import org.example.view.ViewManager;
import org.example.view.components.UserBar;
import org.example.view.panels.AddItemPanel;
import org.example.view.panels.HomePanel;
import org.example.view.panels.LogInPanel;
import org.example.view.panels.OperationsPanel;
import org.example.view.panels.RegistrationPanel;
import org.example.view.panels.SignInPanel;
import org.example.view.panels.UsersInventoryPanel;

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
                viewManager.show("signin"); // Go back to sign in
            }
        });

        // Register panel
        RegistrationPanel registrationPanel = new RegistrationPanel();
        registrationPanel.setActionListener(new RegistrationPanel.UserActionListener() {
            public void onRegisterSubmit(String name, String surname, String sex, String phoneNumber,
                    String stateResidency,
                    String province, String cap, String street, String streetCode, String username, String email,
                    String password) {
                // Call the UserController for handling the registration
                userController.handleUserRegistration(name, surname, sex, phoneNumber, stateResidency, province, cap,
                        street, streetCode, username, email, password);
            }

            public void onRegisterCancel() {
                viewManager.show("signin"); // Go back to sign in
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
                viewManager.show("additem");
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

        AddItemPanel addItemPanel = new AddItemPanel();
        addItemPanel.setActionListener(new AddItemPanel.UserActionListener() {
            public void onRegisterItemSubmit(String title, String condition, String note) {
                itemController.handleItemRegistration(title, condition, note);
            }

            public void onRegisterItemCancel() {
                viewManager.show("home"); // go back to home view
            }
        });

        // Register all the pannels
        viewManager.registerPanel("signin", signInPanel);
        viewManager.registerPanel("login", logInPanel);
        viewManager.registerPanel("registration", registrationPanel);
        viewManager.registerPanel("home", homePanel);
        viewManager.registerPanel("additem", addItemPanel);

        // start the app on the signIn pannel
        // here i should had the logic for displaying the two pannels side by side maybe
        viewManager.show("signin");
    }
}
