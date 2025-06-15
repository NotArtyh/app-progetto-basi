package org.example.view;

import org.example.view.components.UserBar;
import org.example.view.panels.HomePanel;
import org.example.view.panels.OperationsPanel;
import org.example.view.panels.PersonalInventoryPanel;
import org.example.view.panels.TradePanel;
import org.example.view.panels.TradeRequestsPanel;
import org.example.view.panels.UsersInventoryPanel;

public class DynamicPanelManager {
    private ViewManager viewManager;

    private UserBar userBar;
    private OperationsPanel operationsPanel;
    private TradeRequestsPanel tradeRequestsPanel;
    private HomePanel homePanel;

    private PersonalInventoryPanel personalInventoryPanel;
    private UsersInventoryPanel usersInventoryPanel;
    private TradePanel tradePanel;

    public DynamicPanelManager(ViewManager viewManager) {
        this.viewManager = viewManager;
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

    // ------ Trade panel

    public void setTradePanel(TradePanel tradePanel) {
        this.tradePanel = tradePanel;
    }

    public void updateTradePanel() {
        viewManager.updatePanel("trade", tradePanel);
    }

}
