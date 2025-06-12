package org.example.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.example.SessionManager;
import org.example.model.User;

/**
 * Data Access Object for User entity
 * Handles all database operations for DATI_UTENTE table
 */
public class UserDAO {

    /**
     * Create a new user in the database
     * 
     * @param user User object to create
     * @throws SQLException if database operation fails
     */
    public DAOResult createUser(User user) throws SQLException {
        String sql = "INSERT INTO DATI_UTENTE (Persona_id, Inventory_id, Livello, Username, PasswordUtente, Email) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, user.getPersonaId());
            stmt.setInt(2, user.getInventoryId());
            stmt.setInt(3, user.getLivello());
            stmt.setString(4, user.getUsername());
            stmt.setString(5, user.getPasswordUtente());
            stmt.setString(6, user.getEmail());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        user.setUserId(rs.getInt(1));
                    }
                }
                return new DAOResult(true, user.getUserId());
            } else
                return new DAOResult(false);
        }
    }

    /**
     * Authenticate user with username and password
     * 
     * @param username Username
     * @param password Password
     * @return true if authentication successful, false otherwise
     * @throws SQLException if database operation fails
     */
    public DAOResult authenticateUser(String username, String password) throws SQLException {
        String sql = "SELECT User_id FROM DATI_UTENTE WHERE Username = ? AND PasswordUtente = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new DAOResult(true, rs.getInt("User_id"));
                }
            }
        }
        return new DAOResult(false);
    }

    /**
     * Get user by ID
     * 
     * IMPORTANT - This method is only used in pair with the SessionManager
     * in the UserService to handle setting a current Session user when either
     * a user logs in or when registering a new user which automathilly logs you
     * in that account.
     * Its a trade off between implementing the needed logic here in the DAO or
     * in the UserService and it makes more sense to have everything in the service
     * while the DAO handles just the db querries
     * 
     * @param userId User ID to search for
     * @return User object or null if not found - NOT DAOResult so not good but has
     *         to do for now
     * @throws SQLException if database operation fails
     */
    public User getUserById(int userId) throws SQLException {
        String sql = "SELECT * FROM DATI_UTENTE WHERE User_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getInt("User_id"),
                            rs.getInt("Persona_id"),
                            rs.getInt("Inventory_id"),
                            rs.getInt("Livello"),
                            rs.getString("Username"),
                            rs.getString("PasswordUtente"),
                            rs.getString("Email"));
                }
            }
        }
        return null;
    }

    // ------------------------- check if below is needed ---------------------

    /**
     * Get user by username
     * 
     * @param username Username to search for
     * @return User object or null if not found
     * @throws SQLException if database operation fails
     */
    public User getUserByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM DATI_UTENTE WHERE Username = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getInt("User_id"),
                            rs.getInt("Persona_id"),
                            rs.getInt("Inventory_id"),
                            rs.getInt("Livello"),
                            rs.getString("Username"),
                            rs.getString("PasswordUtente"),
                            rs.getString("Email"));
                }
            }
        }
        return null;
    }

    /**
     * Get all users from database
     * 
     * @return List of all users
     * @throws SQLException if database operation fails
     */
    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM DATI_UTENTE ORDER BY User_id";

        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                users.add(new User(
                        rs.getInt("User_id"),
                        rs.getInt("Persona_id"),
                        rs.getInt("Inventory_id"),
                        rs.getInt("Livello"),
                        rs.getString("Username"),
                        rs.getString("PasswordUtente"),
                        rs.getString("Email")));
            }
        }
        return users;
    }

    /**
     * Get users by level
     * 
     * @param livello Level to filter by
     * @return List of users at specified level
     * @throws SQLException if database operation fails
     */
    public List<User> getUsersByLevel(int livello) throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM DATI_UTENTE WHERE Livello = ? ORDER BY Username";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, livello);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    users.add(new User(
                            rs.getInt("User_id"),
                            rs.getInt("Persona_id"),
                            rs.getInt("Inventory_id"),
                            rs.getInt("Livello"),
                            rs.getString("Username"),
                            rs.getString("PasswordUtente"),
                            rs.getString("Email")));
                }
            }
        }
        return users;
    }

    /**
     * Update user information
     * 
     * @param user User object with updated information
     * @throws SQLException if database operation fails
     */
    public void updateUser(User user) throws SQLException {
        String sql = "UPDATE DATI_UTENTE SET Persona_id = ?, Inventory_id = ?, Livello = ?, Username = ?, PasswordUtente = ?, Email = ? WHERE User_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, user.getPersonaId());
            stmt.setInt(2, user.getInventoryId());
            stmt.setInt(3, user.getLivello());
            stmt.setString(4, user.getUsername());
            stmt.setString(5, user.getPasswordUtente());
            stmt.setString(6, user.getEmail());
            stmt.setInt(7, user.getUserId());

            stmt.executeUpdate();
        }
    }

    /**
     * Update only user's level
     * 
     * @param userId   User ID
     * @param newLevel New level value
     * @throws SQLException if database operation fails
     */
    public void updateUserLevel(int userId, int newLevel) throws SQLException {
        String sql = "UPDATE DATI_UTENTE SET Livello = ? WHERE User_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, newLevel);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        }
    }

    /**
     * Delete user from database
     * 
     * @param userId User ID to delete
     * @throws SQLException if database operation fails
     */
    public void deleteUser(int userId) throws SQLException {
        String sql = "DELETE FROM DATI_UTENTE WHERE User_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.executeUpdate();
        }
    }

    /**
     * Check if username exists
     * 
     * @param username Username to check
     * @return true if username exists, false otherwise
     * @throws SQLException if database operation fails
     */
    public boolean usernameExists(String username) throws SQLException {
        String sql = "SELECT COUNT(*) FROM DATI_UTENTE WHERE Username = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
}