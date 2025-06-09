package org.example.view.panels;

import javax.swing.*;
import java.awt.*;

/*
 * This should be the main pannel of the app where we see everything,
 * it should be a composition of the other pannels:
 * - User Bar component - top 
 * - Users Inventory panel - 2/3 left
 * - Operations panel - 1/3 right
 */
public class HomePanel extends JPanel {

    public HomePanel(JPanel userBar, JPanel usersInventoryPanel, JPanel operationsPanel) {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        createHomePanel(userBar, usersInventoryPanel, operationsPanel);
    }

    private void createHomePanel(JPanel userBar, JPanel usersInventoryPanel, JPanel operationsPanel) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;

        // Top user bar (full width)
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 0.1;
        add(userBar, gbc);

        // UsersInventoryPanel panel (2/3 width)
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0.66;
        gbc.weighty = 0.9;
        add(usersInventoryPanel, gbc);

        // Operations panel (1/3 width)
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.34;
        gbc.weighty = 0.9;
        add(operationsPanel, gbc);
    }
}
