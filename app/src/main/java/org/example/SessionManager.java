package org.example;

import org.example.model.User;

/*
 * Singleton class that traks current session user
 * Use this class when in need of knowing the curren user id
 * for all types of operations
 */
public class SessionManager {
    private static SessionManager instance;
    private User currentUser;

    private SessionManager() {
        // private constructor to avoid it being generated elsewhere
    }

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public User getCurrenUser() {
        return currentUser;
    }

    public void clearSession() {
        this.currentUser = null;
    }

    public boolean isUserLoggedIn() {
        return currentUser != null;
    }
}
