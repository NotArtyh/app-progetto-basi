package org.example.controller;

import org.example.view.HomePanelManager;
import org.example.view.ViewManager;
import org.example.view.panels.AddItemPanel;
import org.example.view.panels.LogInPanel;
import org.example.view.panels.OperationsPanel;
import org.example.view.panels.PersonalInventoryPanel;
import org.example.view.panels.RegistrationPanel;
import org.example.view.panels.SignInPanel;

public class AppController {
    private final ViewManager viewManager;
    private final HomePanelManager homePanelManager;
    private final UserController userController;
    private final ItemController itemController;
    private final InventoryController inventoryController;

    public AppController(ViewManager viewManager, UserController userController, ItemController itemController,
            InventoryController inventoryController, HomePanelManager homePanelManager) {
        this.viewManager = viewManager;
        this.homePanelManager = homePanelManager;
        this.userController = userController;
        this.itemController = itemController;
        this.inventoryController = inventoryController;
        setupViews();
    }

    // prepares the action listeners for the static panels that arent user dependant
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

        // home panel - Side view with user operations
        OperationsPanel operationsPanel = new OperationsPanel();
        operationsPanel.setActionListener(new OperationsPanel.UserActionListener() {
            public void onAddItem() {
                // change view to Add item panel
                viewManager.show("addItem");
            }

            public void onViewInventory() {
                // The pannel is created on demand
                inventoryController.handlePersonalInventoryUpdate();
                viewManager.show("inventory");

                // inventoryController.handleInventoryDisplay(SessionManager.getInstance().getCurrenUser().getUserId());
            }

            public void onTradeItem() {
                // change view to trade panel
            }
        });

        // set just the static pannel, the update of the other is hanled in the
        // controllers that interest it, nominally the UserController in the HandleLogin
        // method
        homePanelManager.setOperationsPanel(operationsPanel);

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

        // Personal Inventory - view what you got
        PersonalInventoryPanel personalInventoryPanel = new PersonalInventoryPanel();

        // Register all the pannels - naming is the same as for java variables
        viewManager.registerPanel("signIn", signInPanel);
        viewManager.registerPanel("logIn", logInPanel);
        viewManager.registerPanel("registration", registrationPanel);
        viewManager.registerPanel("addItem", addItemPanel);
        viewManager.registerPanel("inventory", personalInventoryPanel);

        // start the app on the signIn pannel - can be used for debugging
        viewManager.show("signIn");
    }

    /*
     * we have two main options for handling this dynamic pannel generation - now on
     * refered to as "on demand pannels"
     * 
     * 1. we handle this pannels initialization in their respective controllers. so
     * Invenotry would stand in the inventory controller, user logout and inso in
     * user controller etc. - i prefer to keep everything here
     * 
     * 2. we keep everything here we could create a special "on deman init" method
     * to handle this type of pannels all in one place and provide everything
     * by simply calling this private method that handles all of them in one go
     * - i'd prefer doing it this way, its mutch more coesive and compact, even
     * if all the on demand methods are done togheter, like initializing the
     * user inventory for the view, or initializing the user to be displayed
     * and the likes, it makes sense to call all of them toghether as they all
     * should happen if the user is logged in, not otherwise and we could handle
     * the action listeners here.
     * 
     * IMPORTANT - here we should also integrate with the controllers to handle
     * the methods for refrescing the data for the view.
     * as of now that is handled by a public method in the view of sai pannel
     * but it isn't a great solution in and off itself, because calling
     * the dao directly inside the view isn't advised for the structure we've
     * planned.
     * 
     * CONSIDER - maybe we shoudln't be handling those pannels this way, maybe
     * we should just compose the base pannel, we create one pannel containing
     * the buttons that will need static action listeners, like a logout button
     * or a exit button for the inv view, and inside of each controller we
     * recompose said pannel based on the new one after the service has passed
     * it the needed data for the view, maybe a list of model objects with the
     * wanted data inside. in order to do this we could add a new method to the
     * view manager which handles updating already existing pannles by re mapping
     * them to a existing name, like an updatePannel method which we would call
     * every time we want to recompose the pannel, like the home pannel which is
     * simply the composition of subpannels but in this case we have the logic
     * for updating.
     * 
     * FINAL - I've added a HomePannelManager, each controller implements its update
     * function to handle when a given pannel should refresh but if its
     */
}