package org.example.view.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.example.SessionManager;
import org.example.model.User;
import org.example.services.ServiceResult;

public class UserBar extends JPanel {
    private UserActionListener actionListener;
    private String username;
    private int level;

    public UserBar() {
    }

    public UserBar(ServiceResult viewData) {
        User user = (User) viewData.getViewDataPayload();
        this.username = user.getUsername();
        this.level = SessionManager.getInstance().getCurrenUser().getLivello(); // Assicurati che User abbia getLevel()
        createUserBar();
    }

    public void setActionListener(UserActionListener actionListener) {
        this.actionListener = actionListener;
    }

    private void createUserBar() {
        setLayout(new BorderLayout());
        setBackground(new Color(210, 213, 221));

        // Right panel elements
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 8, 0, 8);
        gbc.anchor = GridBagConstraints.CENTER;

        // Username label
        JLabel userLabel = new JLabel(username);
        userLabel.setFont(new Font("Roboto", Font.BOLD, 18));
        userLabel.setForeground(Color.BLACK);
        gbc.gridx = 0;
        rightPanel.add(userLabel, gbc);

        // Level label
        JLabel levelLabel = new JLabel("Level: " + level);
        levelLabel.setFont(new Font("Roboto", Font.PLAIN, 16));
        levelLabel.setForeground(Color.DARK_GRAY);
        gbc.gridx = 1;
        rightPanel.add(levelLabel, gbc);

        // Spacing before profile picture
        gbc.gridx = 2;
        rightPanel.add(Box.createRigidArea(new Dimension(16, 0)), gbc);

        // Profile picture
        JPanel profilePicture = new SquareImagePanel("pfp3.png");
        gbc.gridx = 3;
        rightPanel.add(profilePicture, gbc);

        // Left panel elements
        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setOpaque(false);

        JButton logoutButton = new StyledButton("🚪", "Logout");
        logoutButton.addActionListener(e -> {
            if (actionListener != null) {
                actionListener.onLogOut();
            }
        });

        leftPanel.add(logoutButton);

        // Padding
        rightPanel.setBorder(new EmptyBorder(0, 0, 0, 32));
        leftPanel.setBorder(new EmptyBorder(0, 32, 0, 0));

        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);
    }

    public interface UserActionListener {
        void onLogOut();
    }
}
