package org.example.services;

import java.sql.SQLException;

import org.example.database.InventoryDAO;
import org.example.database.PersonalDataDAO;
import org.example.database.UserDAO;
import org.example.model.Inventory;
import org.example.model.PersonalData;
import org.example.model.User;

public class UserService {
    private UserDAO userDAO;
    private PersonalDataDAO personalDataDAO;
    private InventoryDAO inventoryDAO;

    /*
     * Create a new user
     */
    public ServiceResult createUser(String name, String surname, String sesso, String telefono, String provincia,
            String statoResidenza, String cap, String via, String civico, String username, String email,
            String password) {
        try {
            if (userDAO.usernameExists(username)) {
                return new ServiceResult(false, "Username is already existing");
            }

            if (username == null || username.trim().isEmpty()) {
                return new ServiceResult(false, "Username cannot be empty.");
            }

            if (email == null || email.trim().isEmpty() || !isValidEmail(email)) {
                return new ServiceResult(false, "Please provide a valid email address.");
            }

            if (password == null || password.trim().isEmpty()) {
                return new ServiceResult(false, "Password cannot be empty.");
            }

            int inventoryId = inventoryDAO.createInventory(new Inventory(true, true));
            if (inventoryId == -1) {
                return new ServiceResult(false, "Failed to create the inventory.");
            }

            int personaId = personalDataDAO.createPersonalData(
                    new PersonalData(name, surname, sesso, telefono, provincia, statoResidenza, cap, via, civico));
            if (personaId == -1) {
                return new ServiceResult(false, "Failed to create the personal data.");
            }

            // Could also return the userId if we want to track say the last created user,
            // or if need to get the current session userId this would be a good place
            userDAO.createUser(new User(personaId, inventoryId, 1, username, password, email));

            return new ServiceResult(true, "New user registration Succeeded.");

        } catch (SQLException e) {
            return new ServiceResult(false, "Error creating user: " + e.getMessage());
        } catch (Exception e) {
            return new ServiceResult(false, "Unexpected error: " + e.getMessage());
        }
    }

    /*
     * Authenticate an existing User on the platform
     */
    public ServiceResult authenticateUser(String username, String password) {
        try {
            if (username == null || username.trim().isEmpty()) {
                return new ServiceResult(false, "Username cannot be empty.");
            }

            if (password == null || password.trim().isEmpty()) {
                return new ServiceResult(false, "Password cannot be empty.");
            }

            // We aren't getting an userId from this single operation right here but we
            // could get it some other way to track the current session user
            boolean isAuthenticated = userDAO.authenticateUser(username, password);
            return new ServiceResult(isAuthenticated, "");

        } catch (SQLException e) {
            return new ServiceResult(false, "Error during authentication: " + e.getMessage());
        } catch (Exception e) {
            return new ServiceResult(false, "Unexpected error: " + e.getMessage());
        }
    }

    /**
     * Validate email format using a simple regex
     * 
     * @param email Email address to validate
     * @return true if email is valid, false otherwise
     */
    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }
}
