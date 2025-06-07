package org.example;

import java.time.LocalDateTime;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.example.database.*;
import org.example.view.*;
import org.example.controller.*;
import org.example.model.*;

/**
 * Main Application with Swing GUI
 * Updated to use the new Swing-based user interface with form windows
 */
public class App {

    private UserView UserView;

    private UserDAO userDAO;
    private UserController userController;

    private PersonalDataDAO personalDataDAO;
    private PersonalDataController personalDataController;

    private InventoryDAO inventoryDAO;
    private InventoryController inventoryController;

    private ItemDAO itemDAO;
    private ItemController itemController;

    public App() {
        // Initialize components
        userDAO = new UserDAO();
        personalDataDAO = new PersonalDataDAO();
        UserView = new UserView();
        userController = new UserController(userDAO, UserView);
        personalDataController = new PersonalDataController(personalDataDAO, UserView);
        inventoryDAO = new InventoryDAO();
        inventoryController = new InventoryController(inventoryDAO, UserView);
        itemDAO = new ItemDAO();
        itemController = new ItemController(itemDAO, UserView);

        // Set up the action listener to handle button clicks
        UserView.setActionListener(new UserView.UserActionListener() {

            @Override
            public void onRegisterUser() {
                // This will now show the registration window
                // The actual registration logic is handled in the form listener
            }

            @Override
            public void onAuthenticateUser() {
                // This will now show the login window
                // The actual authentication logic is handled in the form listener
            }

            @Override
            public void onExit() {
                handleExit();
            }
        });

        // Set up the form action listener to handle form submissions
        UserView.setFormActionListener(new UserView.FormActionListener() {

            @Override
            public void onRegisterSubmit(UserView.RegistrationData data) {
                handleRegisterSubmit(data);
            }

            @Override
            public void onLoginSubmit(String username, String password) {
                handleLoginSubmit(username, password);
            }

            @Override
            public void onLogout() {
                handleLogout();
            }

            @Override
            public void onItemSubmit(UserView.ItemData data) {
                handleItemRegistration(data);
            }
        });

        // Show the GUI
        UserView.show();
    }

    /**
     * Handle login form submission
     */
    private void handleLoginSubmit(String username, String password) {
        try {
            UserView.displayMessage("Authenticating user...");
            userController.authenticateUser(username, password);
        } catch (Exception e) {
            UserView.displayError("Error during authentication: " + e.getMessage());
        }
    }

    /**
     * Validate registration data
     */
    private boolean validateRegistrationData(UserView.RegistrationData data) {
        // Validate email format
        if (!isValidEmail(data.email)) {
            UserView.displayError("Invalid email format.");
            return false;
        }

        // Validate phone number (basic check)
        if (!isValidPhoneNumber(data.telefono)) {
            UserView.displayError("Invalid phone number format.");
            return false;
        }

        // Validate sesso field
        if (!data.sesso.toUpperCase().matches("[MF]")) {
            UserView.displayError("Sesso must be 'M' or 'F'.");
            return false;
        }

        // Validate CAP (Italian postal code - 5 digits)
        if (!data.cap.matches("\\d{5}")) {
            UserView.displayError("CAP must be 5 digits.");
            return false;
        }

        // Validate provincia (2 uppercase letters)
        if (!data.provincia.matches("[A-Z]{2}")) {
            UserView.displayError("Provincia must be 2 uppercase letters (e.g., PU).");
            return false;
        }

        // Validate password strength
        if (data.password.length() < 6) {
            UserView.displayError("Password must be at least 6 characters long.");
            return false;
        }

        return true;
    }

    /**
     * Validate email format
     */
    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$");
    }

    /**
     * Validate phone number format
     */
    private boolean isValidPhoneNumber(String phone) {
        // Basic validation for Italian phone numbers
        return phone.matches("^[+]?[0-9\\s\\-\\(\\)]{8,15}$");
    }

    /**
     * Handle logout
     */
    private void handleLogout() {
        UserView.displayMessage("User logged out successfully.");
        // Additional logout logic can be added here if needed
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

    private void handleItemRegistration(UserView.ItemData data) {
        try {
            UserView.displayMessage("Processing adding a new item...");

            LocalDateTime dataAcquisizione = LocalDateTime.now(); 
            
            /*  DOBBIAMO FARE IN MODO CHE INVENTORY ID PRENDA L'ID DELL'INVENTARIO CORRENTE 
            int inventoryId = ;

            if (inventoryId == -1) {
                UserView.displayError("Failed to create new item: Inventory not found.");
                return;
            }
*/
            itemController.createItem(data.mediaId, 1, data.condizioni, data.note, dataAcquisizione);

        } catch (Exception e) {
            UserView.displayError("Error while adding an item: " + e.getMessage());
        }
    }

   /**
     * Handle registration form submission
     */
    private void handleRegisterSubmit(UserView.RegistrationData data) {
        try {
            UserView.displayMessage("Processing registration...");

            // Validate input data
            if (!validateRegistrationData(data)) {
                return;
            }

            // Call controller methods
            int persona_id = personalDataController.createPersonalData(
                    data.nome, data.cognome, data.sesso, data.telefono,
                    data.stato_residenza, data.provincia, data.cap,
                    data.via, data.civico);

            int inventory_id = inventoryController.createInventory();

            if (persona_id == -1 || inventory_id == -1) {
                UserView.displayError("Failed to create personal data or inventory.");
                return;
            }

            userController.createUser(persona_id, inventory_id, data.username, data.email, data.password);

        } catch (Exception e) {
            UserView.displayError("Error during user registration: " + e.getMessage());
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