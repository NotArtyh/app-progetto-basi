package org.example.controller;

import org.example.services.InventoryService;
import org.example.services.ServiceResult;
import org.example.view.ViewManager;

/**
 * Inventory Controller Class
 * Handles business logic and coordinates between Model and View
 */
public class InventoryController {
    private InventoryService inventoryService;
    private ViewManager viewManager;

    public InventoryController(InventoryService inventoryService, ViewManager viewManager) {
        this.inventoryService = inventoryService;
        this.viewManager = viewManager;
    }

    /*
     * An example of a Controller method that just wires the view to the corespond
     * service here this method would be use to handle the display of an invenotry
     * and the like the service is responsible for calling all the DAOs it need to
     * operate such querry The Controller is bound to the "entity" that is best
     * paired with: in this case the inventory is bounded to all those operations
     * that would use it directly, like the display of the inventory with all its
     * items inside displaying a list of these inventories and the likes.
     * 
     * NO LOGIC HERE - ALL OPERATIONS INSIDE THE SERVICE
     * we simply wire the view and the service togheter aka we expect
     * a ServiceResult type reuslt that tells the view what to display
     */
    public void handleInventoryDisplay(int invenotryId) {
        try {
            ServiceResult result = inventoryService.getItemsInInvetory(invenotryId);

            // Update the view based on result
            if (result.isSuccess()) {
                // View goes forward - Ok
                System.out.println(result.getMessage());
            } else {
                // View displays an error and doesn't go forwards
                System.out.println(result.getMessage());
            }
        } catch (Exception e) {
            // Display the fatal error on the view
            System.out.println("Fatal error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
