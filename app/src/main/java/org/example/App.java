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
        result.close();
    }
}