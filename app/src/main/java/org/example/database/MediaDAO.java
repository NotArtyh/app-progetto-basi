package org.example.database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MediaDAO {
    /**
     * Get Media Id that matches the provided title
     *
     * @param title media title to match
     * @return DAO result with success value, false no id, true also id returned
     * @throws SQLException if database operation fails
     */
    public DAOResult getIdTitle(String title) throws SQLException {
        // Selecting everything because we may use a media class later if we want to use
        // the image or other info
        String sql = "SELECT * FROM MEDIA WHERE Titolo = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, title);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int mediaId = rs.getInt("Media_id"); // Retrieve the media_id from the result set
                    return new DAOResult(true, mediaId);
                } else
                    return new DAOResult(false);
            }
        }
    }
    
    /**
     * Get all media titles from the database
     *
     * @return List of all media titles
     * @throws SQLException if database operation fails
     */
    public List<String> getAllTitles() throws SQLException {
        List<String> titles = new ArrayList<>();
        String sql = "SELECT Titolo FROM MEDIA ORDER BY Titolo ASC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                titles.add(rs.getString("Titolo"));
            }
        }
        
        return titles;
    }
}