package org.example.database;

/*
 * Wrapper class for handling the results
 * that a DAO can send.
 * Used to be a int return but having to validate
 * a -1 for the failure case was awkward and we
 * are in java so a wrapper is the obvious choice 
 */
public class DAOResult {
    private boolean success;
    private int id;

    // Constructor for failure case, we don't specify any id in the case of failure
    public DAOResult(boolean success) {
        this.success = success;
    }

    // Constructor for success case, we provide the id
    public DAOResult(boolean success, int id) {
        this.success = success;
        this.id = id;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }    
}
