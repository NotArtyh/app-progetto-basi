package org.example.controller;

import org.example.services.InventoryService;
import org.example.view.ViewManager;

public class UsersInventoryController {

private  InventoryService usersInventoryService;
private  ViewManager viewManager;
    
    public UsersInventoryController(InventoryService usersInventoryService, ViewManager viewManager) {
    this.usersInventoryService = usersInventoryService;
    this.viewManager = viewManager;
    }



}
