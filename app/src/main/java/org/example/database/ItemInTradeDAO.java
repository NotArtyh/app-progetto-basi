package org.example.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import org.example.model.ItemInTrade;

public class ItemInTradeDAO {
    public DAOResult createItem(ItemInTrade itemInTrade) throws SQLException {
        String sql = "INSERT INTO ITEM_IN_SCAMBIO (Scambio_id, Ricevente_inventory_id, Richiedente_inventory_id, Item_id, Media_id, Data_scambio) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, itemInTrade.getScambioId());
            stmt.setInt(2, itemInTrade.getRiceventeInventoryId());
            stmt.setInt(3, itemInTrade.getRichiedenteInventoryId());
            stmt.setInt(4, itemInTrade.getItemId());
            stmt.setInt(5, itemInTrade.getMediaId());
            stmt.setTimestamp(6, Timestamp.valueOf(itemInTrade.getDataScambio()));

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                return new DAOResult(true);
            } else
                return new DAOResult(false);
        }
    }

}
