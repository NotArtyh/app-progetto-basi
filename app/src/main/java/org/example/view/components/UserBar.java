package org.example.view.components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;

public class UserBar extends JPanel {
    private UserActionListener actionListener;
    private String username = "xXPippoXx";

    public UserBar() {
        createUserBar();
    }

    public void setActionListener(UserActionListener actionListener) {
        this.actionListener = actionListener;
    }

    private void createUserBar() {
        setLayout(new BorderLayout());
        setBackground(new Color(210, 213, 221));

        JLabel userLabel = new JLabel("User: " + username);
        userLabel.setFont(new Font("Roboto", Font.BOLD, 18));
        userLabel.setForeground(Color.BLACK);

        // Center panel for logout button
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false); // Let parent background show through

        JButton logoutButton = new StyledButton("🚪", "Logout");
        logoutButton.addActionListener(e -> {
            if (actionListener != null) {
                actionListener.onLogOut();
            }
        });

        centerPanel.add(logoutButton);

        // Add some padding to the user label
        userLabel.setBorder(new EmptyBorder(0, 0, 0, 32));
        centerPanel.setBorder(new EmptyBorder(0, 32, 0, 0));

        add(centerPanel, BorderLayout.WEST);
        add(userLabel, BorderLayout.EAST);
    }

    public interface UserActionListener {
        void onLogOut();
    }
}
