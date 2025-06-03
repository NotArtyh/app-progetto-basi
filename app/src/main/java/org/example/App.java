package org.example;

import java.util.Scanner;

import org.example.controller.UserController;
import org.example.database.UserDAO;
import org.example.view.UserView;


// Main Application

public class App {
    
    public static void main(String[] args) {
        // Initialize components
        UserDAO userDAO = new UserDAO();
        UserView userView = new UserView();
        UserController userController = new UserController(userDAO, userView);
        
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        
        while (running) {
            userView.displayMenu();
            
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline
                
                switch (choice) {
                    case 1:
                        System.out.print("Enter first int parameter: ");
                        int param1 = scanner.nextInt();
                        System.out.print("Enter second int parameter: ");
                        int param2 = scanner.nextInt();
                        System.out.print("Enter third int parameter: ");
                        int param3 = scanner.nextInt();
                        scanner.nextLine(); // consume newline
                        System.out.print("Enter first string parameter: ");
                        String param4 = scanner.nextLine();
                        System.out.print("Enter second string parameter: ");
                        String param5 = scanner.nextLine();
                        System.out.print("Enter third string parameter: ");
                        String param6 = scanner.nextLine();
                        userController.createUser(param1, param2, param3, param4, param5, param6);
                        break;
                        
                    case 2:
                        System.out.print("Enter user ID: ");
                        int id = scanner.nextInt();
                        userController.displayUser(id);
                        break;
                        
                    case 3:
                        userController.displayAllUsers();
                        break;
                        
                    case 4:
                        System.out.print("Enter first int parameter (user ID to update): ");
                        int updateId = scanner.nextInt();
                        System.out.print("Enter second int parameter: ");
                        int param21 = scanner.nextInt();
                        System.out.print("Enter third int parameter: ");
                        int param31 = scanner.nextInt();
                        scanner.nextLine(); // consume newline
                        System.out.print("Enter first string parameter: ");
                        String param41 = scanner.nextLine();
                        System.out.print("Enter second string parameter: ");
                        String param51 = scanner.nextLine();
                        System.out.print("Enter third string parameter: ");
                        String param61 = scanner.nextLine();
                        System.out.print("Enter fourth int parameter: ");
                        int param11 = scanner.nextInt();
                        scanner.nextLine(); // consume newline
                        userController.updateUser(updateId, param21, param31, param11, param41, param51, param61);
                        break;
                        
                    case 5:
                        System.out.print("Enter user ID to delete: ");
                        int deleteId = scanner.nextInt();
                        userController.deleteUser(deleteId);
                        break;
                        
                    case 6:
                        running = false;
                        System.out.println("Goodbye!");
                        break;
                        
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
                
            } catch (Exception e) {
                System.err.println("Invalid input. Please try again.");
                scanner.nextLine(); // clear invalid input
            }
        }
        
        scanner.close();
    }
}