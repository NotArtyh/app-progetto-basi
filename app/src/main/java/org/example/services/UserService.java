package org.example.services;

import java.sql.SQLException;

import org.example.SessionManager;
import org.example.database.DAOResult;
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

    public UserService(UserDAO userDAO, PersonalDataDAO personalDataDAO, InventoryDAO inventoryDAO) {
        this.userDAO = userDAO;
        this.personalDataDAO = personalDataDAO;
        this.inventoryDAO = inventoryDAO;
    }

    /*
     * Create a new user
     * set session user to the new created user
     */
    public ServiceResult registerUser(String name, String surname, String sesso, String telefono, String statoResidenza,
            String provincia, String cap, String via, String civico, String username, String email,
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

            // We send the querries via the DAOs and based on the result we either give an
            // error or go trough
            DAOResult inventoryResult = inventoryDAO.createInventory(new Inventory(true, true));
            if (!inventoryResult.isSuccess()) {
                return new ServiceResult(false, "Failed to create the inventory.");
            }

            DAOResult personalDataResult = personalDataDAO.createPersonalData(
                    new PersonalData(name, surname, sesso, telefono, provincia, statoResidenza, cap, via, civico));
            if (!personalDataResult.isSuccess()) {
                return new ServiceResult(false, "Failed to create the personal data.");
            }

            // Set the id from the two results - if we get here it means the querry went
            // trough
            int inventoryId = inventoryResult.getId();
            int personaId = personalDataResult.getId();

            // We finally create the user
            // userResult contains the userId - use it for tracking the current user
            DAOResult userResult = userDAO.createUser(new User(personaId, inventoryId, 1, username, password, email));
            // could put a check here if the creation went trough but we asume it does

            // Set the sessionUser via userResult userId and SessionManager
            boolean sessionResult = setCurrentUserByUserId(userResult.getId());
            if (!sessionResult) {
                // Handle failure to setting the current session User
                return new ServiceResult(false, "Failed to set current session user.");
            }

            return new ServiceResult(true, "New user registration Succeeded.");

        } catch (SQLException e) {
            return new ServiceResult(false, "Error creating user: " + e.getMessage());
        } catch (Exception e) {
            return new ServiceResult(false, "Unexpected error: " + e.getMessage());
        }
    }

    /*
     * Authenticate an existing User on the platform
     * set session user to auth user 
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
            DAOResult authResult = userDAO.authenticateUser(username, password);

            // Set the sessionUser via userResult userId and SessionManager
            boolean sessionResult = setCurrentUserByUserId(authResult.getId());
            if (!sessionResult) {
                // Handle failure to setting the current session User
                return new ServiceResult(false, "Failed to set current session user.");
            }

            return new ServiceResult(authResult.isSuccess(), "");

        } catch (SQLException e) {
            return new ServiceResult(false, "Error during authentication: " + e.getMessage());
        } catch (Exception e) {
            return new ServiceResult(false, "Unexpected error: " + e.getMessage());
        }
    }
    
    /**
     * Set current session user via a provided userId 
     * 
     * @param userId
     * @return
     * @throws SQLException
     */
    private boolean setCurrentUserByUserId(int userId) throws SQLException {
        User currentUser = userDAO.getUserById(userId);
        SessionManager.getInstance().setCurrentUser(currentUser);
        return currentUser != null;
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
