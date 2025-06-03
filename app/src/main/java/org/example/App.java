package org.example;

import org.example.database.DatabaseManager;
import org.example.model.Model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class App {
    public static void main(String[] args) throws SQLException {
        // Basic sql querry
        String sql = "select * from DATI_ANAGRAFICI";

        // We first test if we can connect to the db
        if (!DatabaseManager.testConnection()) {
            System.out.println("Exiting due to connection failure");
            return;
        }
        
        Connection connection = DatabaseManager.getConnection();

        // We should actually prepare it to avoid sql injection
        // [todo] add to DatabaseManager method for preparing a statement
        Statement statement = connection.createStatement(); 
        ResultSet result = statement.executeQuery(sql);

        ResultSetMetaData metaData = result.getMetaData();
        int columnCount = metaData.getColumnCount();

        // We loop over the result to print the outcome of said sql querry
        while (result.next()) {
            StringBuilder row = new StringBuilder();
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i);
                String columnValue = result.getString(i);
                row.append(columnName).append(": ").append(columnValue);
                if (i < columnCount)
                    row.append(", ");
            }
            System.out.println(row.toString());
        }
    }
}




// Main Application
import java.util.Scanner;

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
                        System.out.print("Enter name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter email: ");
                        String email = scanner.nextLine();
                        System.out.print("Enter age: ");
                        int age = scanner.nextInt();
                        userController.createUser(name, email, age);
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
                        System.out.print("Enter user ID to update: ");
                        int updateId = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Enter new name: ");
                        String newName = scanner.nextLine();
                        System.out.print("Enter new email: ");
                        String newEmail = scanner.nextLine();
                        System.out.print("Enter new age: ");
                        int newAge = scanner.nextInt();
                        userController.updateUser(updateId, newName, newEmail, newAge);
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