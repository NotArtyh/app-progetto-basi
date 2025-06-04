package org.example.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.example.model.PersonalData;

/**
 * Data Access Object for personal data entity
 * Handles all database operations for DATI_ANAGRAFICI table
 */
public class PersonalDataDAO {
    
    /**
     * Create a new person in the database
     * @param PersonalData  object to create
     * @throws SQLException if database operation fails
     */
    public void createPerson(PersonalData person) throws SQLException {
        String sql = "INSERT INTO DATI_ANAGRAFICI (Persona_id, Nome , Cognome , Sesso , Telefono , Stato , Provincia , CAP, Via_Viale_Piazza, Numero_civico) VALUES (? , ? , ? , ? , ? , ? , ? , ? , ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, person.getPersonaId());
            stmt.setString(2, person.getNome());
            stmt.setString(3, person.getCognome());
            stmt.setString(4, person.getSesso());
            stmt.setString(5, person.getTelefono());
            stmt.setString(6, person.getStato_residenza());
            stmt.setString(7, person.getProvincia());
            stmt.setString(8, person.getCap());
            stmt.setString(9, person.getVia());
            stmt.setString(10, person.getCivico());
    
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        person.setPersonaId(rs.getInt(1));
                    }
                }
            }
        }
    }
}