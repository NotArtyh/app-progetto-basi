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
            stmt.setBytes(2, media.getFoto());
            stmt.setString(3, media.getTitolo());
            stmt.setTimestamp(4, java.sql.Timestamp.valueOf(media.getData_aggiunta()));
            stmt.setString(5, media.getNome_formato());
            stmt.executeUpdate();
        
            
            }
        }
    }
    
