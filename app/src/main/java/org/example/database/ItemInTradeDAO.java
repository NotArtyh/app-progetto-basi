package org.example.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import org.example.model.ItemInTrade;

public class ItemInTradeDAO {
    public DAOResult createItem(ItemInTrade itemInTrade) throws SQLException {
        String sql = "INSERT INTO ITEM_IN_SCAMBIO (Media_id, Item_id, Scambio_id, Richiedente_inventory_id, Ricevente_inventory_id, DataScambio) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, itemInTrade.getMediaId());
            stmt.setInt(2, itemInTrade.getItemId());
            stmt.setInt(3, itemInTrade.getScambioId());
            stmt.setInt(4, itemInTrade.getRichiedenteInventoryId());
            stmt.setInt(5, itemInTrade.getRiceventeInventoryId());
            stmt.setTimestamp(6, Timestamp.valueOf(itemInTrade.getDataScambio()));

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                return new DAOResult(true);
            } else
                return new DAOResult(false);
        }
    }

}
