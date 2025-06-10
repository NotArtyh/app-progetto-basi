package org.example.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.example.model.Item;

public class ItemDAO {

    public DAOResult createItem(Item item) throws SQLException {
        String sql = "INSERT INTO ITEM_INVENTARIO (Media_id, Inventory_id, Condizioni, Note, Data_acquisizione) VALUES (?, ?, ?, ?, ?)";
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

    /**
     * Recupera tutti gli item dell'inventario di un utente specifico
     *
     * @param userId ID dell'utente
     * @return Lista di tutti gli item dell'utente
     * @throws SQLException se l'operazione fallisce
     */
    public List<Item> getItemsByUserId(int userId) throws SQLException {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT i.Item_id, i.Media_id, i.Inventory_id, i.Condizioni, i.Note, i.Data_acquisizione " +
                "FROM ITEM_INVENTARIO i, DATI_UTENTE u " +
                "WHERE u.Inventory_id = i.Inventory_id " +
                "AND u.User_id = ? " +
                "ORDER BY i.Data_acquisizione DESC";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Item item = new Item();
                    item.setItemId(rs.getInt("Item_id"));
                    item.setMediaId(rs.getInt("Media_id"));
                    item.setInventoryId(rs.getInt("Inventory_id"));
                    item.setCondizioni(rs.getString("Condizioni"));
                    item.setNote(rs.getString("Note"));

                    Timestamp timestamp = rs.getTimestamp("Data_acquisizione");
                    if (timestamp != null) {
                        item.setData_acquisizione(timestamp.toLocalDateTime());
                    }

                    items.add(item);
                }
            }
        }

        return items;
    }

    /**
     * Recupera un item specifico tramite il suo ID
     *
     * @param itemId ID dell'item
     * @return Item trovato o null se non esiste
     * @throws SQLException se l'operazione fallisce
     */
    public Item getItemById(int itemId) throws SQLException {
        String sql = "SELECT Item_id, Media_id, Inventory_id, Condizioni, Note, Data_acquisizione " +
                "FROM ITEM_INVENTARIO WHERE Item_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, itemId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Item item = new Item();
                    item.setItemId(rs.getInt("Item_id"));
                    item.setMediaId(rs.getInt("Media_id"));
                    item.setInventoryId(rs.getInt("Inventory_id"));
                    item.setCondizioni(rs.getString("Condizioni"));
                    item.setNote(rs.getString("Note"));

                    Timestamp timestamp = rs.getTimestamp("Data_acquisizione");
                    if (timestamp != null) {
                        item.setData_acquisizione(timestamp.toLocalDateTime());
                    }

                    return item;
                }
            }
        }

        return null;
    }
}