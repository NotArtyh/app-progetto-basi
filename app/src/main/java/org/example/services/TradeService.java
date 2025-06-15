package org.example.services;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import org.example.SessionManager;
import org.example.database.DAOResult;
import org.example.database.InventoryDAO;
import org.example.database.ItemDAO;
import org.example.database.ItemInTradeDAO;
import org.example.database.TradeDAO;
import org.example.model.Item;
import org.example.model.ItemInTrade;
import org.example.model.Trade;
import org.example.model.User;

public class TradeService {
    TradeDAO tradeDAO;
    InventoryDAO inventoryDAO;
    ItemDAO itemDAO;
    ItemInTradeDAO itemInTradeDAO;

    public TradeService(TradeDAO tradeDAO, InventoryDAO inventoryDAO, ItemDAO itemDAO, ItemInTradeDAO itemInTradeDAO) {
        this.tradeDAO = tradeDAO;
        this.inventoryDAO = inventoryDAO;
        this.itemDAO = itemDAO;
        this.itemInTradeDAO = itemInTradeDAO;
    }

    public ServiceResult registerTradeRequest(List<Item> offeredItems, List<Item> wantedItems,
            User targetUser) {
        try {
            if (!SessionManager.getInstance().isUserLoggedIn()) {
                return new ServiceResult(false, "User not logged in.");
            }
            User currentUser = SessionManager.getInstance().getCurrenUser();
            int currentUserId = currentUser.getUserId();
            int targetUserId = targetUser.getUserId();

            // check if offered items are in the current user inventory
            // if (!offeredItems.isEmpty()) {
            // List<Item> allCurrenUserItems = itemDAO.getItemsByUserId(currentUserId);
            // Boolean validOffer = allCurrenUserItems.contains(offeredItems);
            // if (!validOffer) {
            // return new ServiceResult(false, "The current user doesn't have the proposed
            // items.");
            // }
            // }

            // check if receiver items are in his inventory
            // if (!wantedItems.isEmpty()) {
            // List<Item> allTargetUserItems = itemDAO.getItemsByUserId(targetUserId);
            // boolean validReceive = allTargetUserItems.contains(wantedItems);
            // if (!validReceive) {
            // return new ServiceResult(false, "The receiver user doesn't have the proposed
            // items.");
            // }
            // }

            // Create the trade - each trade has the current time and date and is set to
            // Pending by default
            LocalDateTime dateCurrent = LocalDateTime.now();
            DAOResult tradeResult = tradeDAO
                    .createTrade(new Trade(targetUser.getInventoryId(), currentUser.getInventoryId(), dateCurrent,
                            "Pending"));
            if (!tradeResult.isSuccess()) {
                return new ServiceResult(false, "Failed to register the trade");
            }

            // Loop over each item of each list and register them as item_in_scambio in the
            // db
            if (!offeredItems.isEmpty()) {
                for (Item item : offeredItems) {
                    DAOResult registerUserItems = itemInTradeDAO
                            .createItem(new ItemInTrade(tradeResult.getId(), targetUser.getInventoryId(),
                                    currentUser.getInventoryId(), item.getItemId(), item.getMediaId(), dateCurrent));

                    if (!registerUserItems.isSuccess()) {
                        return new ServiceResult(false, "Failed to register as in trade the item: " + item.toString());
                    }

                }
            }

            // here the current and target are flipped, check if its right
            if (!wantedItems.isEmpty()) {
                for (Item item : wantedItems) {
                    DAOResult registerUserItems = itemInTradeDAO
                            .createItem(new ItemInTrade(tradeResult.getId(), currentUser.getInventoryId(),
                                    targetUser.getInventoryId(), item.getItemId(), item.getMediaId(), dateCurrent));

                    if (!registerUserItems.isSuccess()) {
                        return new ServiceResult(false, "Failed to register as in trade the item: " + item.toString());
                    }
                }
            }

            return new ServiceResult(true, "Trade request sent.");

        } catch (SQLException e) {
            return new ServiceResult(false, "Error creating item: " + e.getMessage());
        } catch (Exception e) {
            return new ServiceResult(false, "Unexpected error: " + e.getMessage());
        }
    }
}
