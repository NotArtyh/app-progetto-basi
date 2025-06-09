package org.example.view.panels;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;

public class RegisterPanel extends JPanel {
    private UserActionListener actionListener;

    public RegisterPanel() {
        createRegisterPanel();
    }

    public void setActionListener(UserActionListener actionListener) {
        this.actionListener = actionListener;
    }

    private void createRegisterPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Create form fields
        JTextField nomeField = new JTextField(20);
        JTextField cognomeField = new JTextField(20);
        JTextField sessoField = new JTextField(20);
        JTextField telefonoField = new JTextField(20);
        JTextField statoResidenzaField = new JTextField(20);
        JTextField provinciaField = new JTextField(20);
        JTextField capField = new JTextField(20);
        JTextField viaField = new JTextField(20);
        JTextField civicoField = new JTextField(20);
        JTextField usernameField = new JTextField(20);
        JTextField emailField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);

        // Add fields to form
        String[] labels = { "Nome:", "Cognome:", "Sesso (M/F):", "Telefono:", "Stato Residenza:",
                "Provincia:", "CAP:", "Via:", "Civico:", "Username:", "Email:", "Password:" };
        JTextField[] fields = { nomeField, cognomeField, sessoField, telefonoField, statoResidenzaField,
                provinciaField, capField, viaField, civicoField, usernameField, emailField, passwordField };

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i;
            gbc.gridwidth = 1;
            JLabel label = new JLabel(labels[i]);
            label.setFont(new Font("Arial", Font.PLAIN, 12));
            formPanel.add(label, gbc);

            gbc.gridx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weightx = 1.0;
            formPanel.add(fields[i], gbc);
            gbc.weightx = 0;
            gbc.fill = GridBagConstraints.NONE;
        }

        // Button panel
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        JButton submitButton = new JButton("Register");
        JButton cancelButton = new JButton("Cancel");

        submitButton.setBackground(new Color(40, 167, 69));
        submitButton.setForeground(Color.BLACK);
        submitButton.setFont(new Font("Arial", Font.BOLD, 12));

        cancelButton.setBackground(new Color(220, 53, 69));
        cancelButton.setForeground(Color.BLACK);
        cancelButton.setFont(new Font("Arial", Font.BOLD, 12));

        submitButton.addActionListener(e -> {
            // Validate fields
            boolean valid = true;
            StringBuilder errors = new StringBuilder();

            for (int i = 0; i < fields.length; i++) {
                if (fields[i].getText().trim().isEmpty()) {
                    valid = false;
                    errors.append("- ").append(labels[i].replace(":", "")).append(" è obbligatorio\n");
                }
            }

            if (!valid) {
                // Display error message
                return;
            }

            // Create registration data and submit
            String name = nomeField.getText().trim();
            String surname = cognomeField.getText().trim();
            String sex = sessoField.getText().trim();
            String phoneNumber = telefonoField.getText().trim();
            String stateResidency = statoResidenzaField.getText().trim();
            String province = provinciaField.getText().trim();
            String cap = capField.getText().trim();
            String street = viaField.getText().trim();
            String streetCode = civicoField.getText().trim();
            String username = usernameField.getText().trim();
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (actionListener != null) {
                actionListener.onRegisterSubmit(name, surname, sex, phoneNumber, stateResidency, province, cap, street,
                        streetCode, username, email, password);
            }
        });

        cancelButton.addActionListener(e -> {
            if (actionListener != null)
                actionListener.onRegisterCancel();
        });

        buttonPanel.add(cancelButton);
        buttonPanel.add(submitButton);

        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    public interface UserActionListener {
        void onRegisterSubmit(String name, String surname, String sex, String phoneNumber, String stateResidency,
                String province, String cap, String street, String streetCode, String username, String email,
                String password);

        void onRegisterCancel();
    }
}
