package org.example.view.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.example.view.components.StyledButton;

public class LogInPanel extends JPanel {
    private UserActionListener actionListener;

    public LogInPanel() {
        createLogInPanel();
    }

    public void setActionListener(UserActionListener actionListener) {
        this.actionListener = actionListener;
    }

    private void createLogInPanel() {
        setLayout(new BorderLayout(20, 20));
        setBorder(new EmptyBorder(40, 40, 40, 40));
        setBackground(new Color(245, 247, 250));

        JLabel titleLabel = new JLabel("Login");
        titleLabel.setFont(new Font("Roboto", Font.BOLD, 26));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
       
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.anchor = GridBagConstraints.WEST;

        JTextField usernameField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(passwordField, gbc);

        // Add button panel immediately after form fields
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.EAST;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        JButton loginButton = new StyledButton("","Login");
        JButton cancelButton = new StyledButton("", "Cancel");

        loginButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username and Password are required!", "Error", JOptionPane.ERROR_MESSAGE);
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

        formPanel.add(buttonPanel, gbc);

        add(formPanel, BorderLayout.CENTER);
    }



    public interface UserActionListener {
        void onLoginSubmit(String username, String password);
        void onLoginCancel();
    }
}