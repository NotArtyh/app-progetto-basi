package org.example.database;

public class DatabaseException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(Throwable cause) {
        super(cause);
    }

    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}

/*
 * 
 * We keep it just because it could facilitate some operations
 * later on in the project. As of now it isn't really used
 * but it should make the code a bit cleaner to read and write.
 * 
 */