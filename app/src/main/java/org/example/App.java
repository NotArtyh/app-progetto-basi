package org.example;

import javax.swing.SwingUtilities;

import org.example.controller.AppController;
import org.example.controller.InventoryController;
import org.example.controller.ItemController;
import org.example.controller.UserController;
import org.example.controller.UsersInventoryController;
import org.example.database.InventoryDAO;
import org.example.database.ItemDAO;
import org.example.database.MediaDAO;
import org.example.database.PersonalDataDAO;
import org.example.database.UserDAO;
import org.example.services.InventoryService;
import org.example.services.ItemService;
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

            // Create Services
            UserService userService = new UserService(userDAO, personalDataDAO, inventoryDAO);
            ItemService itemService = new ItemService(itemDAO, mediaDAO);
            InventoryService inventoryService = new InventoryService(inventoryDAO, itemDAO, mediaDAO);
            UsersInventoryService usersInventoryService = new UsersInventoryService(itemDAO, mediaDAO, userDAO);

            // create the controllers
            InventoryController inventoryController = new InventoryController(inventoryService, viewManager,
            dynamicPanelManager, usersInventoryService);
            UserController userController = new UserController(userService, viewManager, dynamicPanelManager, inventoryController);
            ItemController itemController = new ItemController(itemService, viewManager);
            UsersInventoryController usersInventoryController = new UsersInventoryController(usersInventoryService, viewManager, dynamicPanelManager);


            

            // Create the final App controller that manages everything
            // could have a return for validation but ok for now
            AppController appController = new AppController(viewManager, userController, itemController, inventoryController, dynamicPanelManager, usersInventoryController);

            mainFrame.setVisible(true);
        });
    }
}