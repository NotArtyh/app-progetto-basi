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
    private PersonalInventoryPanel personalInventoryPanel; //va rimosso
    private final SessionManager sessionManager;

    public AppController(ViewManager viewManager, UserController userController, ItemController itemController, SessionManager sessionManager) {
        this.viewManager = viewManager;
        this.userController = userController;
        this.itemController = itemController;
        this.sessionManager = sessionManager;
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
                // Inizializza o aggiorna il PersonalInventoryPanel prima di mostrarlo
                initializePersonalInventory();
                viewManager.show("inventory");
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

        // Il PersonalInventoryPanel verrà inizializzato quando necessario
        // perché richiede l'ID dell'utente corrente che non è disponibile all'avvio

        // Register all the pannels
        viewManager.registerPanel("signin", signInPanel);
        viewManager.registerPanel("login", logInPanel);
        viewManager.registerPanel("registration", registrationPanel);
        viewManager.registerPanel("home", homePanel);
        viewManager.registerPanel("additem", addItemPanel);
        // Il pannello inventory verrà registrato dinamicamente

        // start the app on the signIn pannel
        viewManager.show("signin");
    }

    /**
     * Inizializza il PersonalInventoryPanel con l'ID dell'utente corrente
     * Questo metodo deve essere chiamato dopo che l'utente ha fatto login
     */
    private void initializePersonalInventory() {
        // Ottieni l'ID dell'utente corrente 
        int currentUserId = sessionManager.getCurrenUserId() ;
        
        if (currentUserId > 0) {
            // Se non esiste ancora o se l'utente è cambiato, crea un nuovo pannello
            if (personalInventoryPanel == null) {
                personalInventoryPanel = new PersonalInventoryPanel(currentUserId);
                viewManager.registerPanel("inventory", personalInventoryPanel);
            } else {
                // Se il pannello esiste già, aggiorna i dati
                personalInventoryPanel.refreshData();
            }
        } else {
            // Se non c'è un utente loggato, torna al login
            viewManager.show("signin");
        }
    }

    /**
     * Metodo pubblico per inizializzare l'inventario quando l'utente fa login
     * Deve essere chiamato dal UserController dopo un login riuscito
     */
    public void onUserLoggedIn() {
        initializePersonalInventory();
    }

    /**
     * Metodo per pulire i dati quando l'utente fa logout
     */
    public void onUserLoggedOut() {
        
    }
}