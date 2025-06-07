package org.example.services;

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
}
