package org.example.controller;

import org.example.view.*;
import org.example.view.panels.*;

public class AppController {
    private final ViewManager viewManager;
    private final UserController userController;

    public AppController(ViewManager viewManager, UserController userController) {
        this.viewManager = viewManager;
        this.userController = userController;
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

        // Register all the pannels
        viewManager.registerPanel("signIn", signInPanel);
        viewManager.registerPanel("login", logInPanel);
        viewManager.registerPanel("registration", registrationPanel);

        // start the app on the signIn pannel
        // here i should had the logic for displaying the two pannels side by side maybe
        viewManager.show("signIn");
    }
}
