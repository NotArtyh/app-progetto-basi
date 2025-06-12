package org.example.view.components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.StyledEditorKit;

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
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(51, 122, 183));
        topBar.setBorder(new EmptyBorder(10, 20, 10, 20));
        topBar.setPreferredSize(new Dimension(0, 80));

        JLabel userLabel = new JLabel("User: " + username);
        userLabel.setFont(new Font("Arial", Font.BOLD, 18));
        userLabel.setForeground(Color.WHITE);

        // Logout button
        JButton logoutButton = new StyledButton("🚪", "Logout");

        logoutButton.addActionListener(e -> {
            if (actionListener != null) {
                actionListener.onLogOut();
            }
        });

        topBar.add(logoutButton, BorderLayout.WEST);
        add(topBar);
    }

    public interface UserActionListener {
        void onLogOut();
    }
}
