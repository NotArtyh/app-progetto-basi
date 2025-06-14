package org.example.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.example.SessionManager;
import org.example.database.ItemDAO;
import org.example.database.MediaDAO;
import org.example.database.UserDAO;
import org.example.model.Item;
import org.example.model.User;

public class UsersInventoryService {
    // Initialize all the needed DAOs
    private ItemDAO itemDAO;
    private MediaDAO mediaDAO;
    private UserDAO userDAO;

    // Build the constructor with said DAOs
    public UsersInventoryService(ItemDAO itemDAO, MediaDAO mediaDAO, UserDAO userDAO) {
        this.itemDAO = itemDAO;
        this.mediaDAO = mediaDAO;
        this.userDAO = userDAO;
    }

    /**
     * Recupera gli inventari di tutti gli utenti registrati eccetto l'utente
     * corrente
     *
     * @return ServiceResult contenente una mappa con gli utenti e i loro item con
     *         titoli
     */
    public ServiceResult getAllUsersInventories() {
        try {
            if (!SessionManager.getInstance().isUserLoggedIn()) {
                return new ServiceResult(false, "User not logged in.");
            }

            int currentUserId = SessionManager.getInstance().getCurrenUser().getUserId();

            // Recupera tutti gli utenti eccetto quello corrente
            List<User> allUsers = userDAO.getAllUsersExceptCurrent(currentUserId);
            if (allUsers.isEmpty()) {
                return new ServiceResult(false, "No other users found.");
            }

            // Mappa che conterrà per ogni utente i suoi item con titoli
            Map<User, Map<Item, String>> usersInventoriesMap = new LinkedHashMap<>();

            for (User user : allUsers) {
                // Recupera gli item dell'utente corrente
                List<Item> userItems = itemDAO.getItemsByUserId(user.getUserId());

                // Crea la mappa item-titolo per questo utente
                Map<Item, String> itemTitleMap = new LinkedHashMap<>();
                for (Item item : userItems) {
                    String title = mediaDAO.getTitleById(item.getMediaId());
                    itemTitleMap.put(item, title);
                }

                // Aggiungi l'utente e i suoi item alla mappa principale
                usersInventoriesMap.put(user, itemTitleMap);
            }

            ServiceResult result = new ServiceResult(true, "Users inventories data retrieved successfully.");
            result.setViewDataPayload(usersInventoriesMap);
            return result;

        } catch (SQLException e) {
            return new ServiceResult(false, "Database error: " + e.getMessage());
        } catch (Exception e) {
            return new ServiceResult(false, "Unexpected error: " + e.getMessage());
        }
    }

    /**
     * Recupera l'inventario di un utente specifico (escluso l'utente corrente)
     *
     * @param targetUserId ID dell'utente di cui recuperare l'inventario
     * @return ServiceResult contenente gli item dell'utente con titoli
     */
    public ServiceResult getUserInventoryById(int targetUserId) {
        try {
            if (!SessionManager.getInstance().isUserLoggedIn()) {
                return new ServiceResult(false, "User not logged in.");
            }

            int currentUserId = SessionManager.getInstance().getCurrenUser().getUserId();

            // Verifica che non stia tentando di accedere al proprio inventario
            if (currentUserId == targetUserId) {
                return new ServiceResult(false, "Cannot access your own inventory through this service.");
            }

            // Verifica che l'utente target esista
            User targetUser = userDAO.getUserById(targetUserId);
            if (targetUser == null) {
                return new ServiceResult(false, "Target user not found.");
            }

            // Recupera gli item dell'utente target
            List<Item> items = itemDAO.getItemsByUserId(targetUserId);
            if (items.isEmpty()) {
                return new ServiceResult(false, "User has no items in the inventory.");
            }

            // Crea la mappa item-titolo
            Map<Item, String> itemTitleMap = new LinkedHashMap<>();
            for (Item item : items) {
                String title = mediaDAO.getTitleById(item.getMediaId());
                itemTitleMap.put(item, title);
            }

            ServiceResult result = new ServiceResult(true, "User inventory data retrieved successfully.");
            result.setViewDataPayload(itemTitleMap);
            return result;

        } catch (SQLException e) {
            return new ServiceResult(false, "Database error: " + e.getMessage());
        } catch (Exception e) {
            return new ServiceResult(false, "Unexpected error: " + e.getMessage());
        }
    }

    /**
     * Recupera solo gli utenti che hanno almeno un item nel loro inventario
     *
     * @return ServiceResult contenente gli utenti con inventari non vuoti
     */
    public ServiceResult getUsersWithItems() {
        try {
            if (!SessionManager.getInstance().isUserLoggedIn()) {
                return new ServiceResult(false, "User not logged in.");
            }

            int currentUserId = SessionManager.getInstance().getCurrenUser().getUserId();

            // Recupera tutti gli utenti eccetto quello corrente
            List<User> allUsers = userDAO.getAllUsersExceptCurrent(currentUserId);
            if (allUsers.isEmpty()) {
                return new ServiceResult(false, "No other users found.");
            }

            // Lista degli utenti che hanno almeno un item
            List<User> usersWithItems = new ArrayList<>();

            for (User user : allUsers) {
                List<Item> userItems = itemDAO.getItemsByUserId(user.getUserId());
                if (!userItems.isEmpty()) {
                    usersWithItems.add(user);
                }
            }

            if (usersWithItems.isEmpty()) {
                return new ServiceResult(false, "No users with items found.");
            }

            ServiceResult result = new ServiceResult(true, "Users with items retrieved successfully.");
            result.setViewDataPayload(usersWithItems);
            return result;

        } catch (SQLException e) {
            return new ServiceResult(false, "Database error: " + e.getMessage());
        } catch (Exception e) {
            return new ServiceResult(false, "Unexpected error: " + e.getMessage());
        }
    }
}