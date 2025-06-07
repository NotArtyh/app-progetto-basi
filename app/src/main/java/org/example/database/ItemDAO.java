package org.example.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.example.model.Item;

public class ItemDAO {

    public void createItem(Item item) throws SQLException {
        String sql = "INSERT INTO ITEM_INVENTARIO (Condizioni, Note, Data_acquisizione) VALUES (?, ?, ?)";
        System.out.println(item.getData_acquisizione());
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, item.getCondizioni());
            stmt.setString(2, item.getNote());
            stmt.setTimestamp(3, java.sql.Timestamp.valueOf(item.getData_acquisizione()));
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        item.setInventoryId(rs.getInt(1));
                    }
                }
            }
        }
    }
}


