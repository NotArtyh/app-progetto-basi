package org.example.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import org.example.model.Item;

public class ItemDAO {

    public DAOResult createItem(Item item) throws SQLException {
        String sql = "INSERT INTO ITEM_INVENTARIO (Media_id, Inventory_id, Condizioni, Note, Data_acquisizione) VALUES (?, ?, ?, ?, ?)";
        System.out.println(item.getData_acquisizione());
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, item.getMediaId());
            stmt.setInt(2, item.getInventoryId());
            stmt.setString(3, item.getCondizioni());
            stmt.setString(4, item.getNote());
            stmt.setTimestamp(5, Timestamp.valueOf(item.getData_acquisizione()));
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        item.setItemId(rs.getInt(1));
                    }
                }
                return new DAOResult(true, item.getItemId());
            } else
                return new DAOResult(false);
        }
    }
}
