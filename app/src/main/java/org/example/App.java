package org.example;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.example.controller.UserController;
import org.example.database.UserDAO;
import org.example.view.UserView;

/**
 * Main Application with Swing GUI
 * Updated to use the new Swing-based user interface
 */
public class App {
    
    private UserDAO userDAO;
    private UserView UserView;
    private UserController userController;
    
    public App() {
        // Initialize components
        userDAO = new UserDAO();
        UserView = new UserView();
        userController = new UserController(userDAO, UserView);
        
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
            // Get user input through dialogs
            String userIdStr = UserView.getUserInput("Enter User ID (integer):");
            if (userIdStr == null || userIdStr.trim().isEmpty()) return;
            
            String personaIdStr = UserView.getUserInput("Enter Persona ID (integer):");
            if (personaIdStr == null || personaIdStr.trim().isEmpty()) return;
            
            String inventoryIdStr = UserView.getUserInput("Enter Inventory ID (integer):");
            if (inventoryIdStr == null || inventoryIdStr.trim().isEmpty()) return;
            
            String username = UserView.getUserInput("Enter Username:");
            if (username == null || username.trim().isEmpty()) return;
            
            String email = UserView.getUserInput("Enter Email:");
            if (email == null || email.trim().isEmpty()) return;
            
            String password = UserView.getUserInput("Enter Password:");
            if (password == null || password.trim().isEmpty()) return;
            
            // Parse integers
            int userId = Integer.parseInt(userIdStr.trim());
            int personaId = Integer.parseInt(personaIdStr.trim());
            int inventoryId = Integer.parseInt(inventoryIdStr.trim());
            
            // Call controller method
            userController.createUser(userId, personaId, inventoryId, username, email, password);
            
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
            if (username == null || username.trim().isEmpty()) return;
            
            String password = UserView.getUserInput("Enter Password:");
            if (password == null || password.trim().isEmpty()) return;
            
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