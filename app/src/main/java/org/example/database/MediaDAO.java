package org.example.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.example.model.Media;


public class MediaDAO {

    public void showMedia(Media media) throws SQLException {
        String sql = "SELECT * FROM MEDIA (Media_id, Foto, Titolo, Data_aggiunta_dominio, NomeFormato) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, media.getMediaId());
            stmt.setString(2, media.getFoto());
            stmt.setString(3, media.getTitolo());
            stmt.setTimestamp(4, java.sql.Timestamp.valueOf(media.getData_aggiunta()));
            stmt.setString(5, media.getNome_formato());
            stmt.executeUpdate();
        

            }
        }
    

        /*      FUNZIONE PER CONTROLLARE SE UN MEDIA ESISTE NEL DATABASE

     public boolean mediaExists(int mediaId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM DATI_UTENTE WHERE Media_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, mediaId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
}
    */

}
