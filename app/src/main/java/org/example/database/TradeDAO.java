package org.example.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import org.example.model.Trade;

public class TradeDAO {

    public DAOResult createTrade(Trade trade) throws SQLException {
        String sql = "INSERT INTO SCAMBIO (Ricevente_inventory_id, Richiedente_inventory_id, DataScambio, Stato) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, trade.getRiceventeInventoryId());
            stmt.setInt(2, trade.getRichiedenteInventoryId());
            stmt.setTimestamp(5, Timestamp.valueOf(trade.getDataScambio()));
            stmt.setString(4, trade.getStato());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        trade.setScambioId(rs.getInt(1));
                    }
                }
                return new DAOResult(true, trade.getScambioId());
            } else
                return new DAOResult(false);
        }
    }


}