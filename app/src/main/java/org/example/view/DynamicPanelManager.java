package org.example.view;

import org.example.view.components.UserBar;
import org.example.view.panels.HomePanel;
import org.example.view.panels.OperationsPanel;
import org.example.view.panels.PersonalInventoryPanel;
import org.example.view.panels.UsersInventoryPanel;

public class DynamicPanelManager {
    private ViewManager viewManager;

    private UserBar userBar;

    private OperationsPanel operationsPanel;
    private HomePanel homePanel;

    private PersonalInventoryPanel personalInventoryPanel;
    private UsersInventoryPanel usersInventoryPanel;

    public DynamicPanelManager(ViewManager viewManager) {
        this.viewManager = viewManager;

        this.userBar = new UserBar();
        this.usersInventoryPanel = new UsersInventoryPanel();
        this.operationsPanel = new OperationsPanel();
        this.homePanel = new HomePanel(userBar, usersInventoryPanel, operationsPanel);

        this.personalInventoryPanel = new PersonalInventoryPanel();
    }

    // ------ Home panel

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

    // ------ Personal inventory panel

    public void setPersonalInventoryPanel(PersonalInventoryPanel personalInventoryPanel) {
        this.personalInventoryPanel = personalInventoryPanel;
    }

    public void updatePersonalInventoryPanel() {
        viewManager.updatePanel("inventory", personalInventoryPanel);
    }

    // ------ Users inventory panel

    public void setusersInventoryPanel(UsersInventoryPanel usersInventoryPanel) {
        this.usersInventoryPanel = usersInventoryPanel;
    }

    public void updateUsersInventoryPanel() {
        viewManager.updatePanel("usersinv", usersInventoryPanel);
    }
}
