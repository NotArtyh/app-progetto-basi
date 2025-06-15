package org.example.view.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;

public class HomePanel extends JPanel {

    public HomePanel(JPanel userBar, JPanel usersInventoryPanel, JPanel operationsPanel) {
        setLayout(new GridBagLayout());
        createHomePanel(userBar, usersInventoryPanel, operationsPanel);
    }

    private void createHomePanel(JPanel userBar, JPanel usersInventoryPanel, JPanel operationsPanel) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;

        // Top user bar (full width)
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 0.1;
        gbc.insets = new Insets(0, 0, 0, 0);
        add(userBar, gbc);

        // UsersInventoryPanel panel (2/3 width)
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0.66;
        gbc.weighty = 0.8;
        gbc.insets = new Insets(10, 10, 5, 10); // bottom spacing reduced
        add(usersInventoryPanel, gbc);

        // Operations panel (1/3 width)
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.34;
        gbc.weighty = 0.8;
        gbc.insets = new Insets(10, 5, 5, 10);
        add(operationsPanel, gbc);

        /*  Trade request panel
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 0.34;
        gbc.weighty = 0.1;
        gbc.insets = new Insets(5, 5, 10, 10); // space between panels
        add(tradeRequestsPanel, gbc);
        */
}

}