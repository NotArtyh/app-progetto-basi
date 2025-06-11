package org.example.services;
import java.util.List;
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
    private Object viewData;
    private List<Object> viewListData;

    public ServiceResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ServiceResult(Object viewData) {
        this.viewData = viewData;
    }

    public ServiceResult(boolean success, String message, List<Object> viewListData) {
        this.success = success;
        this.message = message;
        this.viewListData = viewListData;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getViewData() {
        return viewData;
    }

    public void setViewData(Object viewData) {
        this.viewData = viewData;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
