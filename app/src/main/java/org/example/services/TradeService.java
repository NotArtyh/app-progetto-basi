package org.example.services;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import org.example.SessionManager;
import org.example.database.DAOResult;
import org.example.database.InventoryDAO;
import org.example.database.ItemDAO;
import org.example.database.TradeDAO;
import org.example.database.UserDAO;
import org.example.model.Item;
import org.example.model.Trade;

public class TradeService {
    TradeDAO tradeDAO;
    InventoryDAO inventoryDAO;
    ItemDAO itemDAO;
    UserDAO userDAO;

    public ServiceResult registerTradeRequest(List<Item> offeredItems, List<Item> receiverItems,
            String receiverUsername) {
        try {
            if (!SessionManager.getInstance().isUserLoggedIn()) {
                return new ServiceResult(false, "User not logged in.");
            }
            int currentUserId = SessionManager.getInstance().getCurrenUser().getUserId();
            int receiverUserId = userDAO.getUserByUsername(receiverUsername).getUserId();

            // check if offered items are in the current user inventory
            List<Item> allCurrenUserItems = itemDAO.getItemsByUserId(currentUserId);
            Boolean validOffer = allCurrenUserItems.contains(offeredItems);
            if (!validOffer) {
                return new ServiceResult(false, "The current user doesn't have the proposed items.");
            }

            // check if receiver items are in his inventory
            List<Item> allReceiverUserItems = itemDAO.getItemsByUserId(receiverUserId);
            boolean validReceive = allReceiverUserItems.contains(receiverItems);
            if (!validReceive) {
                return new ServiceResult(false, "The receiver user doesn't have the proposed items.");
            }
            
            // Create the trade - each trade has the current time and date and is set to
            // Pending by default
            LocalDateTime dateCurrent = LocalDateTime.now();
            DAOResult tradeResult = tradeDAO
                    .createTrade(new Trade(receiverUserId, currentUserId, dateCurrent, "Pending"));

            // return the success of said trade
            return new ServiceResult(true, "Trade request sent.");

        } catch (SQLException e) {
            return new ServiceResult(false, "Error creating item: " + e.getMessage());
        } catch (Exception e) {
            return new ServiceResult(false, "Unexpected error: " + e.getMessage());
        }
    }
}
