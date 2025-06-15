package org.example;

import javax.swing.SwingUtilities;

import org.example.controller.AppController;
import org.example.controller.InventoryController;
import org.example.controller.ItemController;
import org.example.controller.UserController;
import org.example.database.InventoryDAO;
import org.example.database.ItemDAO;
import org.example.database.ItemInTradeDAO;
import org.example.database.MediaDAO;
import org.example.database.PersonalDataDAO;
import org.example.database.TradeDAO;
import org.example.database.UserDAO;
import org.example.model.ItemInTrade;
import org.example.services.InventoryService;
import org.example.services.ItemService;
import org.example.services.TradeService;
import org.example.services.UserService;
import org.example.services.UsersInventoryService;
import org.example.view.DynamicPanelManager;
import org.example.view.MainFrame;
import org.example.view.ViewManager;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            // Create the frame and the manager
            MainFrame mainFrame = new MainFrame();
            ViewManager viewManager = new ViewManager(mainFrame);
            DynamicPanelManager dynamicPanelManager = new DynamicPanelManager(viewManager);

            // Create DAO
            UserDAO userDAO = new UserDAO();
            PersonalDataDAO personalDataDAO = new PersonalDataDAO();
            InventoryDAO inventoryDAO = new InventoryDAO();
            ItemDAO itemDAO = new ItemDAO();
            MediaDAO mediaDAO = new MediaDAO();
            TradeDAO tradeDAO = new TradeDAO();
            ItemInTradeDAO itemInTradeDAO = new ItemInTradeDAO();

            // Create Services
            UserService userService = new UserService(userDAO, personalDataDAO, inventoryDAO);
            ItemService itemService = new ItemService(itemDAO, mediaDAO);
            InventoryService inventoryService = new InventoryService(inventoryDAO, itemDAO, mediaDAO);
            UsersInventoryService usersInventoryService = new UsersInventoryService(itemDAO, mediaDAO, userDAO);
            TradeService tradeService = new TradeService(tradeDAO, inventoryDAO, itemDAO, itemInTradeDAO);

            // create the controllers
            InventoryController inventoryController = new InventoryController(inventoryService, viewManager,
                    dynamicPanelManager, usersInventoryService, tradeService);
            UserController userController = new UserController(userService, viewManager, dynamicPanelManager,
                    inventoryController);
            ItemController itemController = new ItemController(itemService, viewManager);

            // Create the final App controller that manages everything
            // could have a return for validation but ok for now
            AppController appController = new AppController(viewManager, userController, itemController,
                    inventoryController, dynamicPanelManager);

            mainFrame.setVisible(true);
        });
    }
}