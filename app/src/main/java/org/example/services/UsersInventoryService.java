package org.example.services;

import java.sql.SQLException;
import java.util.List;

import org.example.SessionManager;
import org.example.database.UserDAO;
import org.example.database.ItemDAO;
import org.example.database.InventoryDAO;
import org.example.database.DAOResult;
import org.example.model.User;
import org.example.model.Item;



public class UsersInventoryService {
    private UserDAO userDAO;
    private ItemDAO itemDAO;
    private InventoryDAO inventoryDAO;

    public UsersInventoryService(UserDAO userDAO, ItemDAO itemDAO, InventoryDAO inventoryDAO) {
        this.userDAO = userDAO;
        this.itemDAO = itemDAO;
        this.inventoryDAO = inventoryDAO;
    }

    /**
     * Recupera tutti gli utenti della piattaforma escluso l'utente corrente
     */
    public ServiceResult getAllUsers() {
        try {
            int currentUserId = SessionManager.getInstance().getCurrenUser().getUserId();
            
            // Ottieni tutti gli utenti
            List<User> allUsers = userDAO.getAllUsers();
            if (allUsers == null) {
                return new ServiceResult(false, "Errore nel recupero degli utenti");
            }
            
            // Filtra l'utente corrente dalla lista
            List<User> otherUsers = allUsers.stream()
                .filter(user -> user.getUserId() != currentUserId)
                .toList();

            return new ServiceResult(true, "Utenti recuperati con successo", (List<Object>)(List<?>) otherUsers);
            
        } catch (Exception e) {
            return new ServiceResult(false, "Errore imprevisto nel recupero degli utenti: " + e.getMessage());
        }
    }

    /**
     * Recupera tutti gli item di un utente specifico
     */
    public ServiceResult getUserItems(int userId) {
        try {
            if (userId <= 0) {
                return new ServiceResult(false, "ID utente non valido");
            }

            List<Item> userItems = itemDAO.getItemsByUserId(userId);
            
            return new ServiceResult(true, "Item dell'utente recuperati con successo", (List<Object>)(List<?>)userItems);
            
        } catch (SQLException e) {
            return new ServiceResult(false, "Errore nel recupero degli item dell'utente: " + e.getMessage());
        } catch (Exception e) {
            return new ServiceResult(false, "Errore imprevisto nel recupero degli item: " + e.getMessage());
        }
    }

    /**
     * Recupera le informazioni di un utente specifico
     */
    public ServiceResult getUserById(int userId) {
        try {
            if (userId <= 0) {
                return new ServiceResult(false, "ID utente non valido");
            }

            User user = userDAO.getUserById(userId);
            if (user == null) {
                return new ServiceResult(false, "Errore nel recupero dell'utente");
            }

            return new ServiceResult(user);
            
        } catch (Exception e) {
            return new ServiceResult(false, "Errore imprevisto nel recupero dell'utente: " + e.getMessage());
        }
    }

    /**
     * Inizia una richiesta di scambio con un utente
     * Questo metodo potrebbe essere espanso per gestire la logica di scambio completa
     */
    public ServiceResult initiateTradeRequest(int targetUserId, int itemId) {
        try {
            int currentUserId = SessionManager.getInstance().getCurrenUser().getUserId();
            
            if (targetUserId == currentUserId) {
                return new ServiceResult(false, "Non puoi iniziare uno scambio con te stesso");
            }

            if (targetUserId <= 0 || itemId <= 0) {
                return new ServiceResult(false, "Parametri non validi per la richiesta di scambio");
            }

            // Qui andrebbero implementate le logiche di business per la richiesta di scambio
            // Per ora ritorniamo un successo placeholder
            // In futuro questo potrebbe:
            // 1. Creare una richiesta di scambio nel database
            // 2. Inviare notifiche all'utente target
            // 3. Gestire lo stato della richiesta
            
            return new ServiceResult(true, "Richiesta di scambio inviata con successo all'utente ID: " + targetUserId);
            
        } catch (Exception e) {
            return new ServiceResult(false, "Errore nell'invio della richiesta di scambio: " + e.getMessage());
        }
    }

    /**
     * Verifica se un utente ha item nel suo inventario
     */
    public ServiceResult hasItems(int userId) {
        try {
            List<Item> userItems = itemDAO.getItemsByUserId(userId);
            boolean hasItems = userItems != null && !userItems.isEmpty();

            if (hasItems){
            
            return new ServiceResult(true, "Verifica completata");
            }
            
        } catch (SQLException e) {
            return new ServiceResult(false, "Errore nella verifica degli item: " + e.getMessage());
        } catch (Exception e) {
            return new ServiceResult(false, "Errore imprevisto nella verifica: " + e.getMessage());
        }
    }
}