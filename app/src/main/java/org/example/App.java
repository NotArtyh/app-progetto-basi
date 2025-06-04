package org.example;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.example.controller.UserController;
import org.example.controller.PersonalDataController;
import org.example.database.UserDAO;
import org.example.database.PersonalDataDAO;
import org.example.view.UserView;

/**
 * Main Application with Swing GUI
 * Updated to use the new Swing-based user interface
 */
public class App {

    private UserDAO userDAO;
    private UserView UserView;
    private PersonalDataDAO personalDataDAO;

    private UserController userController;
    private PersonalDataController personalDataController;

    public App() {
        // Initialize components
        userDAO = new UserDAO();
        personalDataDAO = new PersonalDataDAO();
        UserView = new UserView();
        userController = new UserController(userDAO, UserView);
        personalDataController = new PersonalDataController(personalDataDAO, UserView);

        // Set up the action listener to handle button clicks
        UserView.setActionListener(new UserView.UserActionListener() {

            @Override
            public void onRegisterUser() {
                handleRegisterUser();
            }

            @Override
            public void onViewAllUsersTable() {
                handleViewAllUsersTable();
            }

            @Override
            public void onAuthenticateUser() {
                handleAuthenticateUser();
            }

            @Override
            public void onExit() {
                handleExit();
            }
        });

        // Show the GUI
        UserView.show();
    }

    /**
     * Handle user registration
     */
    private void handleRegisterUser() {
        try {

            String nome = UserView.getUserInput("Enter Nome:");
            if (nome == null || nome.trim().isEmpty())
                return;

            String cognome = UserView.getUserInput("Enter Cognome:");
            if (cognome == null || cognome.trim().isEmpty())
                return;

            String sesso = UserView.getUserInput("Enter Sesso (M/F):");
            if (sesso == null || sesso.trim().isEmpty())
                return;

            String telefono = UserView.getUserInput("Enter Numero di Telefono:");
            if (telefono == null || telefono.trim().isEmpty())
                return;

            String stato_residenza = UserView.getUserInput("Enter Stato di Residenza:");
            if (stato_residenza == null || stato_residenza.trim().isEmpty())
                return;

            String provincia = UserView.getUserInput("Enter Provincia (es. PU):");
            if (provincia == null || provincia.trim().isEmpty())
                return;

            String cap = UserView.getUserInput("Enter CAP:");
            if (cap == null || cap.trim().isEmpty())
                return;

            String via = UserView.getUserInput("Enter Via:");
            if (via == null || via.trim().isEmpty())
                return;

            String civico = UserView.getUserInput("Enter Numero civico:");
            if (civico == null || civico.trim().isEmpty())
                return;

            String username = UserView.getUserInput("Enter Username:");
            if (username == null || username.trim().isEmpty())
                return;

            String email = UserView.getUserInput("Enter Email:");
            if (email == null || email.trim().isEmpty())
                return;

            String password = UserView.getUserInput("Enter Password:");
            if (password == null || password.trim().isEmpty())
                return;

            // Call controller method
            personalDataController.createPersonalData(nome, cognome, sesso, telefono, stato_residenza, provincia, cap,
                    via, civico);
            // userController.createUser(username, email, password);

        } catch (NumberFormatException e) {
            UserView.displayError("Invalid number format. Please enter valid integers for ID fields.");
        } catch (Exception e) {
            UserView.displayError("Error during user registration: " + e.getMessage());
        }
    }

    /**
     * Handle view all users (table format)
     */
    private void handleViewAllUsersTable() {
        try {
            userController.displayAllUsersTable();
        } catch (Exception e) {
            UserView.displayError("Error displaying users table: " + e.getMessage());
        }
    }

    /**
     * Handle user authentication
     */
    private void handleAuthenticateUser() {
        try {
            String username = UserView.getUserInput("Enter Username:");
            if (username == null || username.trim().isEmpty())
                return;

            String password = UserView.getUserInput("Enter Password:");
            if (password == null || password.trim().isEmpty())
                return;

            userController.authenticateUser(username, password);

        } catch (Exception e) {
            UserView.displayError("Error during authentication: " + e.getMessage());
        }
    }

    /**
     * Handle application exit
     */
    private void handleExit() {
        boolean confirmed = UserView.showConfirmation("Are you sure you want to exit?");

        if (confirmed) {
            UserView.displayGoodbye();
            // Give time for goodbye message to be seen
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            UserView.close();
        }
    }

    /**
     * Main method - Entry point of the application
     */
    public static void main(String[] args) {
        // Set system look and feel for better appearance
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Could not set system look and feel: " + e.getMessage());
        }

        // Run the GUI application on the Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new App();
                } catch (Exception e) {
                    System.err.println("Error starting application: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }
}