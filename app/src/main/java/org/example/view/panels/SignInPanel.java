package org.example.view.panels;

import javax.swing.*;
import java.awt.*;

import org.example.view.components.ImagePanel;
import org.example.view.components.StyledButton;

/*
 * Panel responsible for showing the sign in menu where you can chose
 * between login in the app, registering a new account or exiting
 */
public class SignInPanel extends JPanel {
    private UserActionListener actionListener;

    public SignInPanel() {
        setLayout(new GridLayout(1, 2));
        createSignInPanel();
    }

    public void setActionListener(UserActionListener actionListener) {
        this.actionListener = actionListener;
    }

    private void createSignInPanel() {
        // Main button panel with vertical layout
        JPanel rightPanel = new JPanel(new GridBagLayout());
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // call the image panel to set the left side immage
        JPanel landingPanel = new ImagePanel("landing.png");
        landingPanel.setLayout(new GridBagLayout()); // To center the image
        add(landingPanel);

        // Create buttons
        JButton registrationButton = new StyledButton("👤", "Registration");
        JButton loginButton = new StyledButton("🔐", "Login");
        JButton exitButton = new StyledButton("🚪", "Exit");

        // Add action listeners
        registrationButton.addActionListener(e -> {
            if (actionListener != null)
                actionListener.onRegistration();
        });
        loginButton.addActionListener(e -> {
            if (actionListener != null)
                actionListener.onLogin();
        });
        exitButton.addActionListener(e -> {
            if (actionListener != null)
                actionListener.onExit();
        });

        // Top row panel with GridLayout for side-by-side buttons
        JPanel topRow = new JPanel(new GridLayout(1, 2, 10, 0));
        topRow.add(registrationButton);
        topRow.add(loginButton);

        // Exit button container (to span full width)
        JPanel bottomRow = new JPanel(new BorderLayout());
        bottomRow.add(exitButton, BorderLayout.CENTER);

        // Add rows to main panel
        buttonPanel.add(topRow);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(bottomRow);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        rightPanel.add(buttonPanel, gbc);
        add(rightPanel);
        // Here we should implement the imagePanel to have the view as
        // we used to but it isn't really important as of now
    }

    public interface UserActionListener {
        void onRegistration();

        void onLogin();

        void onExit();
    }
}
