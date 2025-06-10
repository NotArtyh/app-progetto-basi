package org.example;

import javax.swing.SwingUtilities;

import org.example.controller.AppController;
import org.example.controller.ItemController;
import org.example.controller.UserController;
import org.example.database.InventoryDAO;
import org.example.database.ItemDAO;
import org.example.database.MediaDAO;
import org.example.database.PersonalDataDAO;
import org.example.database.UserDAO;
import org.example.services.ItemService;
import org.example.services.UserService;
import org.example.view.MainFrame;
import org.example.view.ViewManager;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            // Create the frame and the manager
            MainFrame mainFrame = new MainFrame();
            ViewManager viewManager = new ViewManager(mainFrame);

            // Create Services
            UserDAO userDAO = new UserDAO();
            PersonalDataDAO personalDataDAO = new PersonalDataDAO();
            InventoryDAO inventoryDAO = new InventoryDAO();
            UserService userService = new UserService(userDAO, personalDataDAO, inventoryDAO);

            ItemDAO itemDAO = new ItemDAO();
            MediaDAO mediaDAO = new MediaDAO();
            ItemService itemService = new ItemService(itemDAO, mediaDAO);

            // create the controllers
            UserController userController = new UserController(userService, viewManager);

            ItemController itemController = new ItemController(itemService, viewManager);

            // create session manager
            SessionManager sessionManager = SessionManager.getInstance();

            // Create the final App controller that manages everything
            // could have a return for validation but ok for now
            AppController appController = new AppController(viewManager, userController, itemController, sessionManager);

            mainFrame.setVisible(true);
        });
    }
}