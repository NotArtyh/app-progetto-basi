package org.example.controller;

import java.sql.SQLException;
import java.util.List;

import org.example.database.UserDAO;
import org.example.model.User;
import org.example.view.UserView;

/**
 * User Controller Class
 * Handles business logic and coordinates between Model and View
 */
public class UserController {
    private UserDAO userDAO;
    private UserView userView;
    
    /**
     * Constructor
     * @param userDAO Data Access Object for users
     * @param userView View for user interface
     */
    public UserController(UserDAO userDAO, UserView userView) {
        this.userDAO = userDAO;
        this.userView = userView;
    }
    
    /**
     * Create a new user
     * @param userId User ID (if 0, will be auto-generated)
     * @param personaId Persona ID
     * @param inventoryId Inventory ID
     * @param username Username
     * @param email Email
     * @param password Password
     */
    public void createUser(int userId, int personaId, int inventoryId, String username, String email, String password) {
        try {
            // Check if username already exists
            if (userDAO.usernameExists(username)) {
                userView.displayError("Username '" + username + "' already exists. Please choose a different username.");
                return;
            }
            
            // Validate input
            if (username == null || username.trim().isEmpty()) {
                userView.displayError("Username cannot be empty.");
                return;
            }
            
            if (email == null || email.trim().isEmpty() || !isValidEmail(email)) {
                userView.displayError("Please provide a valid email address.");
                return;
            }
            
            if (password == null || password.trim().isEmpty()) {
                userView.displayError("Password cannot be empty.");
                return;
            }
            
            // Create user with default level 1
            User user;
            if (userId == 0) {
                // Auto-generate ID
                user = new User(personaId, inventoryId, 1, username, password, email);
            } else {
                // Use provided ID
                user = new User(userId, personaId, inventoryId, 1, username, password, email);
            }
            
            userDAO.createUser(user);
            userView.displayMessage("User created successfully with ID: " + user.getUserId());
            
        } catch (SQLException e) {
            userView.displayError("Error creating user: " + e.getMessage());
        } catch (Exception e) {
            userView.displayError("Unexpected error: " + e.getMessage());
        }
    }
    
    /**
     * Display all users in table format
     */
    public void displayAllUsersTable() {
        try {
            List<User> users = userDAO.getAllUsers();
            userView.displayUsersTable(users);
        } catch (SQLException e) {
            userView.displayError("Error retrieving users: " + e.getMessage());
        } catch (Exception e) {
            userView.displayError("Unexpected error: " + e.getMessage());
        }
    }
    
    /**
     * Update user information
     * @param userId User ID
     * @param personaId New Persona ID
     * @param inventoryId New Inventory ID
     * @param livello New Level
     * @param username New Username
     * @param email New Email
     * @param password New Password
     */
    public void updateUser(int userId, int personaId, int inventoryId, int livello, String username, String email, String password) {
        try {
            // Check if user exists
            User existingUser = userDAO.getUserById(userId);
            if (existingUser == null) {
                userView.displayError("User not found with ID: " + userId);
                return;
            }
            
            // Check if new username already exists (but not for the same user)
            User userWithSameUsername = userDAO.getUserByUsername(username);
            if (userWithSameUsername != null && userWithSameUsername.getUserId() != userId) {
                userView.displayError("Username '" + username + "' already exists. Please choose a different username.");
                return;
            }
            
            // Validate input
            if (username == null || username.trim().isEmpty()) {
                userView.displayError("Username cannot be empty.");
                return;
            }
            
            if (email == null || email.trim().isEmpty() || !isValidEmail(email)) {
                userView.displayError("Please provide a valid email address.");
                return;
            }
            
            if (password == null || password.trim().isEmpty()) {
                userView.displayError("Password cannot be empty.");
                return;
            }
            
            User user = new User(userId, personaId, inventoryId, livello, username, password, email);
            userDAO.updateUser(user);
            userView.displayMessage("User updated successfully");
            
        } catch (SQLException e) {
            userView.displayError("Error updating user: " + e.getMessage());
        } catch (Exception e) {
            userView.displayError("Unexpected error: " + e.getMessage());
        }
    }

    /**
     * Authenticate user
     * @param username Username
     * @param password Password
     */
    public void authenticateUser(String username, String password) {
        try {
            if (username == null || username.trim().isEmpty()) {
                userView.displayError("Username cannot be empty.");
                return;
            }
            
            if (password == null || password.trim().isEmpty()) {
                userView.displayError("Password cannot be empty.");
                return;
            }
            
            boolean isAuthenticated = userDAO.authenticateUser(username, password);
            userView.displayAuthenticationResult(username, isAuthenticated);
            
        } catch (SQLException e) {
            userView.displayError("Error during authentication: " + e.getMessage());
        } catch (Exception e) {
            userView.displayError("Unexpected error: " + e.getMessage());
        }
    }

    /**
     * Validate email format using a simple regex
     * @param email Email address to validate
     * @return true if email is valid, false otherwise
     */
    private boolean isValidEmail(String email) {
        // Simple regex for demonstration; consider using more robust validation if needed
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }
}