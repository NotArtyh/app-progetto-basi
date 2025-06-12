package org.example.view.panels;

import java.awt.GridBagLayout;

import javax.swing.JPanel;

public class UsersInventoryPanel extends JPanel {
    private UserActionListener actionListener;

    public UsersInventoryPanel() {
        setLayout(new GridBagLayout());
        createUsersInventoryPanel();
    }

    public void setActionListener(UserActionListener actionListener) {
        this.actionListener = actionListener;
    }

    private void createUsersInventoryPanel() {
        // Implement all the view Here for this pannel
        // it should contain a view of all the users invenotries in the platform and
        // thei containing items

    }

    public interface UserActionListener {
        // Implement the needed action listeners
    }
}
