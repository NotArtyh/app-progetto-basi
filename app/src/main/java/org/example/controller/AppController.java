package org.example.controller;

import org.example.SessionManager;
import org.example.view.ViewManager;
import org.example.view.components.UserBar;
import org.example.view.panels.AddItemPanel;
import org.example.view.panels.HomePanel;
import org.example.view.panels.LogInPanel;
import org.example.view.panels.OperationsPanel;
import org.example.view.panels.PersonalInventoryPanel;
import org.example.view.panels.RegistrationPanel;
import org.example.view.panels.SignInPanel;
import org.example.view.panels.UsersInventoryPanel;

public class AppController {
    private final ViewManager viewManager;
    private final UserController userController;
    private final ItemController itemController;
    private final InventoryController inventoryController;

    // Dynamically handled pannels that change depending on the current session user
    private PersonalInventoryPanel personalInventoryPanel;

    public AppController(ViewManager viewManager, UserController userController, ItemController itemController,
            InventoryController inventoryController) {
        this.viewManager = viewManager;
        this.userController = userController;
        this.itemController = itemController;
        this.inventoryController = inventoryController;
        setupViews();
    }

    // prepares the action listeners
    private void setupViews() {

        // SignIn panel
        SignInPanel signInPanel = new SignInPanel();
        signInPanel.setActionListener(new SignInPanel.UserActionListener() {
            public void onRegistration() {
                viewManager.show("registration");
            }

            public void onLogin() {
                viewManager.show("logIn");
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
                // Call the UserController for handling the registration
                userController.handleUserRegistration(name, surname, sex, phoneNumber, stateResidency, province, cap,
                        street, streetCode, username, email, password);
            }

            public void onRegisterCancel() {
                viewManager.show("signIn"); // Go back to sign in
            }
        });

        // Home panel - Top user bar with infos and logout
        UserBar userBar = new UserBar();
        userBar.setActionListener(new UserBar.UserActionListener() {
            // have to impl listeners
        });

        // home panel - Global view of all user inventories on the platform
        UsersInventoryPanel usersInventoryPanel = new UsersInventoryPanel();
        usersInventoryPanel.setActionListener(new UsersInventoryPanel.UserActionListener() {
            // have to impl listeners
        });

        // home panel - Side view with user operations
        OperationsPanel operationsPanel = new OperationsPanel();
        operationsPanel.setActionListener(new OperationsPanel.UserActionListener() {
            public void onAddItem() {
                // change view to Add item panel
                viewManager.show("addItem");
            }

            public void onViewInventory() {
                // The pannel is created on demand
                initializePersonalInventory();
                viewManager.show("inventory");

                // inventoryController.handleInventoryDisplay(SessionManager.getInstance().getCurrenUser().getUserId());
            }

            public void onTradeItem() {
                // change view to trade panel
            }
        });

        HomePanel homePanel = new HomePanel(userBar, usersInventoryPanel, operationsPanel);
        // this pannel has no listeners.

        // AddItem panel - add new item to inventory
        AddItemPanel addItemPanel = new AddItemPanel();
        addItemPanel.setActionListener(new AddItemPanel.UserActionListener() {
            public void onRegisterItemSubmit(String title, String condition, String note) {
                itemController.handleItemRegistration(title, condition, note);
            }

            public void onRegisterItemCancel() {
                viewManager.show("home"); // go back to home view
            }
        });

        // Register all the pannels - naming is the same as for java variables
        viewManager.registerPanel("signIn", signInPanel);
        viewManager.registerPanel("logIn", logInPanel);
        viewManager.registerPanel("registration", registrationPanel);
        viewManager.registerPanel("home", homePanel);
        viewManager.registerPanel("addItem", addItemPanel);
        // The inventory pannel is registered dinamically based on the current User

        // start the app on the signIn pannel - can be used for debugging
        viewManager.show("signIn");
    }

    /*
     * We generate the pannel on demand based on the userId in order to get its
     * own inventory, for this we simply check with the session manager
     * if a user is logged in and validate if the pannel for that
     * inventory exists, we then simply regenerate one or generate
     * a new one based on the result
     * 
     * We can setup event listeners in the SetupView method above
     * as if we take for granted that this pannel already exists, in fact
     * the below code is triggered only the first time
     * 
     */
    private void initializePersonalInventory() {
        // Get current user Id via session manager
        int currentUserId = SessionManager.getInstance().getCurrenUser().getUserId();

        if (SessionManager.getInstance().isUserLoggedIn()) {
            if (personalInventoryPanel == null) {
                personalInventoryPanel = new PersonalInventoryPanel(currentUserId);

                /*
                 * PersonalInventoryPanel listeners
                 */
                personalInventoryPanel.setActionListener(new PersonalInventoryPanel.UserActionListener() {
                    public void onExit() {
                        viewManager.show("home"); // go back to home view
                    }
                });

                viewManager.registerPanel("inventory", personalInventoryPanel);
            } else {
                // Se il pannello esiste già, aggiorna i dati
                personalInventoryPanel.refreshData();
            }
        } else
            viewManager.show("signIn");
    }
}