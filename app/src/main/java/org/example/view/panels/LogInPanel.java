package org.example.view.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LogInPanel extends JPanel {
    private UserActionListener actionListener;

    public LogInPanel() {
        createLogInPanel();
    }

    public void setActionListener(UserActionListener actionListener) {
        this.actionListener = actionListener;
    }

    private void createLogInPanel() {
        JPanel logInPanel = new JPanel();
        logInPanel.setLayout(new BorderLayout(10, 10));
        logInPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Call the headerComponent for adding a header to this section

        // Form area of panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Text Fields
        JTextField usernameField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);

        // Username
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Roboto", Font.PLAIN, 14));
        formPanel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        formPanel.add(usernameField, gbc);

        // Password
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Roboto", Font.PLAIN, 14));
        formPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        formPanel.add(passwordField, gbc);

        // Button panel for logIn and cancel
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        JButton loginButton = new JButton("Login");
        JButton cancelButton = new JButton("Cancel");

        loginButton.setBackground(new Color(40, 167, 69)); // change
        loginButton.setForeground(Color.BLACK);
        loginButton.setFont(new Font("Roboto", Font.BOLD, 12));

        cancelButton.setBackground(new Color(220, 53, 69)); // change
        cancelButton.setForeground(Color.BLACK);
        cancelButton.setFont(new Font("Roboto", Font.BOLD, 12));

        loginButton.addActionListener(e -> {
            // Get input of text fields
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());

            // validate fields input
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Username and Password are required!",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (actionListener != null) {
                actionListener.onLoginSubmit(username, password);
            }
        });

        cancelButton.addActionListener(e -> {
            if (actionListener != null)
                actionListener.onLoginCancel();
        });

        buttonPanel.add(cancelButton);
        buttonPanel.add(loginButton);

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public interface UserActionListener {
        void onLoginSubmit(String username, String password);

        void onLoginCancel();
    }
}
