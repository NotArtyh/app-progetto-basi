package org.example.model;
package model;

/**
 * User Entity Class
 * Represents a user from the DATI_UTENTE table
 */
public class User {
    private int userId;
    private int personaId;
    private int inventoryId;
    private int livello;
    private String username;
    private String passwordUte;
    private String email;
    
    // Default Constructor
    public User() {}
    
    // Constructor without userId (for creating new users)
    public User(int personaId, int inventoryId, int livello, String username, String passwordUte, String email) {
        this.personaId = personaId;
        this.inventoryId = inventoryId;
        this.livello = livello;
        this.username = username;
        this.passwordUte = passwordUte;
        this.email = email;
    }
    
    // Full Constructor (for existing users)
    public User(int userId, int personaId, int inventoryId, int livello, String username, String passwordUte, String email) {
        this.userId = userId;
        this.personaId = personaId;
        this.inventoryId = inventoryId;
        this.livello = livello;
        this.username = username;
        this.passwordUte = passwordUte;
        this.email = email;
    }
    
    // Getters and Setters
    public int getUserId() { 
        return userId; 
    }
    
    public void setUserId(int userId) { 
        this.userId = userId; 
    }
    
    public int getPersonaId() { 
        return personaId; 
    }
    
    public void setPersonaId(int personaId) { 
        this.personaId = personaId; 
    }
    
    public int getInventoryId() { 
        return inventoryId; 
    }
    
    public void setInventoryId(int inventoryId) { 
        this.inventoryId = inventoryId; 
    }
    
    public int getLivello() { 
        return livello; 
    }
    
    public void setLivello(int livello) { 
        this.livello = livello; 
    }
    
    public String getUsername() { 
        return username; 
    }
    
    public void setUsername(String username) { 
        this.username = username; 
    }
    
    public String getPasswordUte() { 
        return passwordUte; 
    }
    
    public void setPasswordUte(String passwordUte) { 
        this.passwordUte = passwordUte; 
    }
    
    public String getEmail() { 
        return email; 
    }
    
    public void setEmail(String email) { 
        this.email = email; 
    }
    
    @Override
    public String toString() {
        return "User{userId=" + userId + 
               ", personaId=" + personaId + 
               ", inventoryId=" + inventoryId + 
               ", livello=" + livello + 
               ", username='" + username + 
               "', email='" + email + "'}";
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return userId == user.userId;
    }
    
    @Override
    public int hashCode() {
        return Integer.hashCode(userId);
    }
}