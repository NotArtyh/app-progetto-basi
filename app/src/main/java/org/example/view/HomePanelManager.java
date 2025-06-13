package org.example.view;

import org.example.view.components.UserBar;
import org.example.view.panels.HomePanel;
import org.example.view.panels.OperationsPanel;
import org.example.view.panels.UsersInventoryPanel;

public class HomePanelManager {

    private ViewManager viewManager;
    private UserBar userBar;
    private UsersInventoryPanel usersInventoryPanel;
    private OperationsPanel operationsPanel;
    private HomePanel homePanel;

    public HomePanelManager(ViewManager viewManager) {
        this.viewManager = viewManager;
        this.userBar = new UserBar();
        this.usersInventoryPanel = new UsersInventoryPanel();
        this.operationsPanel = new OperationsPanel();
        this.homePanel = new HomePanel(userBar, usersInventoryPanel, operationsPanel);
    }

    public void setUserBar(UserBar userBar) {
        this.userBar = userBar;
    }

    public void setUsersInventoryPanel(UsersInventoryPanel usersInventoryPanel) {
        this.usersInventoryPanel = usersInventoryPanel;
    }

    public void setOperationsPanel(OperationsPanel operationsPanel) {
        this.operationsPanel = operationsPanel;
    }

    public void updateHomePanel() {
        HomePanel updatedHomePanel = new HomePanel(userBar, usersInventoryPanel, operationsPanel);
        viewManager.updatePanel("home", updatedHomePanel);
    }

    public void registerHomePanel() {
        viewManager.registerPanel("home", homePanel);
    }
}
