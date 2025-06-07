package org.example.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.example.model.Item;

public class ItemDAO {

    public void createItem(Item item) throws SQLException {
        String sql = "INSERT INTO ITEM_INVENTARIO (Media_id, Inventory_id, Item_id, Condizioni, Note, Data_acquisizione) VALUES (?, ?, ?, ?, ?, ?)";
        System.out.println(item.getData_acquisizione());
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, item.getMediaId());
            stmt.setInt(2, item.getInventoryId());
            stmt.setInt(3, item.getItemId());
            stmt.setString(4, item.getCondizioni());
            stmt.setString(5, item.getNote());
            stmt.setTimestamp(6, java.sql.Timestamp.valueOf(item.getData_acquisizione()));
            stmt.executeUpdate();
        }
    }
}
