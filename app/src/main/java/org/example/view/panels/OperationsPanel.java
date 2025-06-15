package org.example.view.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.example.view.components.StyledButton;

/*
 * This pannel is responsible for showing the operations 
 * buttons that stand on the right third of the home dashboard
 */
public class OperationsPanel extends JPanel {
    private UserActionListener actionListener;

    public OperationsPanel() {
        setLayout(new GridBagLayout());
        createOperationsPanel();
    }

    public void setActionListener(UserActionListener actionListener) {
        this.actionListener = actionListener;
    }

    private void createOperationsPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create buttons
        JButton addItemButton = new StyledButton("➕", "Add Item");
        JButton viewInventoryButton = new StyledButton("👀", "View Inventory");
        JButton tradeStatusButton = new StyledButton("🤝", "Trade Status");

        // Add action listeners
        addItemButton.addActionListener(e -> {
            if (actionListener != null)
                actionListener.onAddItem();
        });
        viewInventoryButton.addActionListener(e -> {
            if (actionListener != null)
                actionListener.onViewInventory();
        });
        tradeStatusButton.addActionListener(e -> {
            if (actionListener != null)
                actionListener.onTradeStatus();
        });

        // Add buttons as veritcal "list"
        buttonPanel.add(addItemButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(viewInventoryButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(tradeStatusButton);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        add(buttonPanel, gbc);
    }

    public interface UserActionListener {
        void onAddItem();

        void onViewInventory();

        void onTradeStatus();
    }
}
