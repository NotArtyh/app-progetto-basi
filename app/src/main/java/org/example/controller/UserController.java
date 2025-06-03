package controller;

import dao.UserDAO;
import model.User;
import view.UserView;
import java.sql.SQLException;
import java.util.List;

/**
 * User Controller Class
 * Handles business logic and coordinates between Model and View
 */
public class UserController {
    private User userDAO;
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
     * @param personaId Persona ID
     * @param inventoryId Inventory ID
     * @param livello Level
     * @param username Username
     * @param password Password
     * @param email Email
     */
    public void createUser(int personaId, int inventoryId, int livello, String username, String password, String email) {
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
            
            User user = new User(personaId, inventoryId, livello, username, password, email);
            userDAO.createUser(user);
            userView.displayMessage("User created successfully with ID: " + user.getUserId());
            
        } catch (SQLException e) {
            userView.displayError("Error creating user: " + e.getMessage());
        }
    }
    
    /**
     * Display user by ID
     * @param userId User ID to search for
     */
    public void displayUser(int userId) {
        try {
            User user = userDAO.getUserById(userId);
            if (user != null) {
                userView.displayUser(user);
            } else {
                userView.displayWarning("User not found with ID: " + userId);
            }
        } catch (SQLException e) {
            userView.displayError("Error retrieving user: " + e.getMessage());
        }
    }
    
    /**
     * Display user by username
     * @param username Username to search for
     */
    public void displayUserByUsername(String username) {
        try {
            User user = userDAO.getUserByUsername(username);
            if (user != null) {
                userView.displayUser(user);
            } else {
                userView.displayWarning("User not found with username: " + username);
            }
        } catch (SQLException e) {
            userView.displayError("Error retrieving user: " + e.getMessage());
        }
    }
    
    /**
     * Display all users
     */
    public void displayAllUsers() {
        try {
            List<User> users = userDAO.getAllUsers();
            userView.displayUsers(users);
        } catch (SQLException e) {
            userView.displayError("Error retrieving users: " + e.getMessage());
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
        }
    }
    
    /**
     * Display users by level
     * @param livello Level to filter by
     */
    public void displayUsersByLevel(int livello) {
        try {
            List<User> users = userDAO.getUsersByLevel(livello);
            if (users.isEmpty()) {
                userView.displayWarning("No users found at level " + livello);
            } else {
                userView.displayMessage("Users at level " + livello + ":");
                userView.displayUsers(users);
            }
        } catch (SQLException e) {
            userView.displayError("Error retrieving users by level: " + e.getMessage());
        }
    }
    
    /**
     * Update user information
     * @param userId User ID
     * @param personaId New Persona ID
     * @param inventoryId New Inventory ID
     * @param livello New Level
     * @param username New Username
     * @param password New Password
     * @param email New Email
     */
    public void updateUser(int userId, int personaId, int inventoryId, int livello, String username, String password, String email) {
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
            
            User user = new User(userId, personaId, inventoryId, livello, username, password, email);
            userDAO.updateUser(user);
            userView.displayMessage("User updated successfully");
            
        } catch (SQLException e) {
            userView.displayError("Error updating user: " + e.getMessage());
        }
    }
    
    /**
     * Level up a user (increase level by 1)
     * @param userId User ID
     */
    public void levelUpUser(int userId) {
        try {
            User user = userDAO.getUserById(userId);
            if (user != null) {
                int oldLevel = user.getLivello();
                int newLevel = oldLevel + 1;
                
                userDAO.updateUserLevel(userId, newLevel);
                userView.displayLevelUp(user.getUsername(), oldLevel, newLevel);
                
            } else {
                userView.displayWarning("User not found with ID: " + userId);
            }
        } catch (SQLException e) {
            userView.displayError("Error updating user level: " + e.getMessage());
        }
    }
    
    /**
     * Delete a user
     * @param userId User ID to delete
     */
    public void deleteUser(int userId) {
        try {
            // Check if user exists before deleting
            User user = userDAO.getUserById(userId);
            if (user == null) {
                userView.displayWarning("User not found with ID: " + userId);
                return;
            }
            
            userDAO.deleteUser(userId);
            userView.displayMessage("User '" + user.getUsername() + "' deleted successfully");
            
        } catch (SQLException e) {
            userView.displayError("Error deleting user: " + e.getMessage());
        }
    }
    
    /**
     * Authenticate user
     * @param username Username
     * @param password Password
     */
    public void authenticateUser(String username, String password) {
        try {
            boolean isAuthenticated = userDAO.authenticateUser(username, password);
            userView.displayAuthenticationResult(username, isAuthenticated);
            
        } catch (SQLException e) {
            userView.displayError("Error during authentication: " + e.getMessage());
        }
    }
    
    /**
     * Get user statistics
     */
    public void displayUserStatistics() {
        try {
            List<User> allUsers = userDAO.getAllUsers();
            
            if (allUsers.isEmpty()) {
                userView.displayMessage("No users in the system.");
                return;
            }
            
            // Calculate statistics
            int totalUsers = allUsers.size();
            int maxLevel = allUsers.stream().mapToInt(User::getLivello).max().orElse(0);
            int minLevel = allUsers.stream().mapToInt(User::getLivello).min().orElse(0);
            double avgLevel = allUsers.stream().mapToInt(User::getLivello).average().orElse(0.0);
            
            System.out.println("\n" + "═".repeat(40));
            System.out.println("           USER STATISTICS");
            System.out.println("═".repeat(40));
            System.out.println("Total Users: " + totalUsers);
            System.out.println("Highest Level: " + maxLevel);
            System.out.println("Lowest Level: " + minLevel);
            System.out.printf("Average Level: %.2f%n", avgLevel);
            System.out.println("═".repeat(40));
            
        } catch (SQLException e) {
            userView.displayError("Error retrieving user statistics");
        }
    }
}