package view;

import model.User;
import java.util.List;

/**
 * User View Class
 * Handles all user interface operations and display logic
 */
public class UserView {
    
    /**
     * Display a single user's information
     * @param user User object to display
     */
    public void displayUser(User user) {
        System.out.println("User Details:");
        System.out.println("─────────────────────────────");
        System.out.println("User ID: " + user.getUserId());
        System.out.println("Persona ID: " + user.getPersonaId());
        System.out.println("Inventory ID: " + user.getInventoryId());
        System.out.println("Level: " + user.getLivello());
        System.out.println("Username: " + user.getUsername());
        System.out.println("Email: " + user.getEmail());
        System.out.println("─────────────────────────────");
    }
    
    /**
     * Display a list of users
     * @param users List of users to display
     */
    public void displayUsers(List<User> users) {
        System.out.println("\n" + "═".repeat(50));
        System.out.println("              USER LIST");
        System.out.println("═".repeat(50));
        
        if (users.isEmpty()) {
            System.out.println("No users found.");
        } else {
            System.out.println("Total users: " + users.size());
            System.out.println();
            
            for (User user : users) {
                displayUser(user);
                System.out.println();
            }
        }
    }
    
    /**
     * Display users in table format
     * @param users List of users to display
     */
    public void displayUsersTable(List<User> users) {
        System.out.println("\n" + "═".repeat(100));
        System.out.println("                                    USER TABLE");
        System.out.println("═".repeat(100));
        
        if (users.isEmpty()) {
            System.out.println("No users found.");
            return;
        }
        
        // Header
        System.out.printf("%-8s %-12s %-12s %-8s %-15s %-25s%n", 
                         "USER_ID", "PERSONA_ID", "INVENTORY_ID", "LEVEL", "USERNAME", "EMAIL");
        System.out.println("─".repeat(100));
        
        // Data rows
        for (User user : users) {
            System.out.printf("%-8d %-12d %-12d %-8d %-15s %-25s%n",
                             user.getUserId(),
                             user.getPersonaId(),
                             user.getInventoryId(),
                             user.getLivello(),
                             user.getUsername(),
                             user.getEmail());
        }
        System.out.println("─".repeat(100));
        System.out.println("Total users: " + users.size());
    }
    
    /**
     * Display success message
     * @param message Message to display
     */
    public void displayMessage(String message) {
        System.out.println("✓ INFO: " + message);
    }
    
    /**
     * Display error message
     * @param error Error message to display
     */
    public void displayError(String error) {
        System.err.println("✗ ERROR: " + error);
    }
    
    /**
     * Display warning message
     * @param warning Warning message to display
     */
    public void displayWarning(String warning) {
        System.out.println("⚠ WARNING: " + warning);
    }
    
    /**
     * Display main menu
     */
    public void displayMenu() {
        System.out.println("\n" + "═".repeat(40));
        System.out.println("    USER MANAGEMENT SYSTEM");
        System.out.println("═".repeat(40));
        System.out.println("1.  Create User");
        System.out.println("2.  View User by ID");
        System.out.println("3.  View User by Username");
        System.out.println("4.  View All Users");
        System.out.println("5.  View All Users (Table)");
        System.out.println("6.  View Users by Level");
        System.out.println("7.  Update User");
        System.out.println("8.  Level Up User");
        System.out.println("9.  Delete User");
        System.out.println("10. Authenticate User");
        System.out.println("11. Exit");
        System.out.println("═".repeat(40));
        System.out.print("Choose an option (1-11): ");
    }
    
    /**
     * Display welcome message
     */
    public void displayWelcome() {
        System.out.println("═".repeat(50));
        System.out.println("    WELCOME TO USER MANAGEMENT SYSTEM");
        System.out.println("═".repeat(50));
    }
    
    /**
     * Display goodbye message
     */
    public void displayGoodbye() {
        System.out.println("\n" + "═".repeat(40));
        System.out.println("    Thank you for using the system!");
        System.out.println("           Goodbye! 👋");
        System.out.println("═".repeat(40));
    }
    
    /**
     * Display input prompt
     * @param prompt Prompt message
     */
    public void displayPrompt(String prompt) {
        System.out.print("➤ " + prompt + ": ");
    }
    
    /**
     * Display separator line
     */
    public void displaySeparator() {
        System.out.println("─".repeat(50));
    }
    
    /**
     * Display authentication result
     * @param username Username that was authenticated
     * @param success Whether authentication was successful
     */
    public void displayAuthenticationResult(String username, boolean success) {
        if (success) {
            System.out.println("✓ Authentication successful!");
            System.out.println("  Welcome back, " + username + "!");
        } else {
            System.out.println("✗ Authentication failed!");
            System.out.println("  Invalid username or password.");
        }
    }
    
    /**
     * Display level up notification
     * @param username Username
     * @param oldLevel Previous level
     * @param newLevel New level
     */
    public void displayLevelUp(String username, int oldLevel, int newLevel) {
        System.out.println("🎉 LEVEL UP!");
        System.out.println("   User: " + username);
        System.out.println("   " + oldLevel + " → " + newLevel);
    }
}