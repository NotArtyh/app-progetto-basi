package org.example.controller;

import org.example.services.UsersInventoryService;
import org.example.view.ViewManager;


public class UsersInventoryController {
    private UsersInventoryService usersInventoryService;
    private ViewManager viewManager;

    public UsersInventoryController(UsersInventoryService usersInventoryService, ViewManager viewManager) {
        this.usersInventoryService = usersInventoryService;
        this.viewManager = viewManager;
    }
/* 
    private void handleLoadAllUsers() {
        ServiceResult result = usersInventoryService.getAllUsers();
        
        if (result.isSuccess()) {
            @SuppressWarnings("unchecked")
            List<User> users = (List<User>) result.getData();
            this.allUsers = users != null ? users : new ArrayList<>();
        } else {
            System.out.println(result.getMessage());
        }
    }

    private void handleLoadUserItems(int userId) {
        ServiceResult result = usersInventoryService.getUserItems(userId);
        
        if (result.isSuccess()) {
            @SuppressWarnings("unchecked")
            List<Item> items = result.getViewListData();
            this.selectedUserItems = items != null ? items : new ArrayList<>();
        } else {
            System.out.println(result.getMessage());
        }
    }
        */

}



