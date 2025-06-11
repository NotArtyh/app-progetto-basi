package org.example.services;

import java.util.List;

import org.example.model.Item;
import org.example.model.User;

/**
 * Wrapper class used to handle the outcome for the
 * service operation, it has a boolean for the success
 * of said operation and a message that goes with it
 * this makes it easier to handle the output and
 * the interaction with the view is made possible
 * say for displaying an error message and such
 */
public class ServiceResult {
    private boolean success;
    private String message;

    public ServiceResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ServiceResult(List<Item> items) {
        this.items = items;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // ------------- handling list returns for view ---------

    private List<Item> items;
    private List<User> users;

    public Object getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
