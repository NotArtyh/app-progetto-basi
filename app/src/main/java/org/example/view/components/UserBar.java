package org.example.view.components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import org.example.model.User;
import org.example.services.ServiceResult;

import java.awt.*;

public class UserBar extends JPanel {
    private UserActionListener actionListener;
    private String username;

    public UserBar() {
    }

    public UserBar(ServiceResult viewData) {
        User user = (User) viewData.getViewDataPayload();
        this.username = user.getUsername();
        createUserBar();
    }

    public void setActionListener(UserActionListener actionListener) {
        this.actionListener = actionListener;
    }

    private void createUserBar() {
        setLayout(new BorderLayout());
        setBackground(new Color(210, 213, 221));

        // right panel elements
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setOpaque(false);

        JLabel userLabel = new JLabel(username);
        userLabel.setFont(new Font("Roboto", Font.BOLD, 18));
        userLabel.setForeground(Color.BLACK);
        rightPanel.add(userLabel);

        rightPanel.add(Box.createRigidArea(new Dimension(16, 0)));

        JPanel profilePicture = new SquareImagePanel("pfp1.png");
        rightPanel.add(profilePicture);

        // left panel elements
        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setOpaque(false); // Let parent background show through

        JButton logoutButton = new StyledButton("🚪", "Logout");
        logoutButton.addActionListener(e -> {
            if (actionListener != null) {
                actionListener.onLogOut();
            }
        });

        leftPanel.add(logoutButton);

        // Add some padding to the user label
        rightPanel.setBorder(new EmptyBorder(0, 0, 0, 32));
        leftPanel.setBorder(new EmptyBorder(0, 32, 0, 0));

        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);
    }

    public interface UserActionListener {
        void onLogOut();
    }
}
