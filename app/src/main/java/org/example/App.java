package org.example;

import javax.swing.SwingUtilities;

import org.example.database.*;
import org.example.view.*;
import org.example.controller.*;
import org.example.services.*;

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

            // create the controllers
            UserController userController = new UserController(userService, viewManager);

            // Create the final App controller that manages everything
            // could have a return for validation but ok for now
            AppController appController = new AppController(viewManager, userController);

            mainFrame.setVisible(true);
        });
    }
}