// RegistrationPanel styled similarly
package org.example.view.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class RegistrationPanel extends JPanel {
    private UserActionListener actionListener;

    public RegistrationPanel() {
        createRegisterPanel();
    }

    public void setActionListener(UserActionListener actionListener) {
        this.actionListener = actionListener;
    }

    private void createRegisterPanel() {
        setLayout(new BorderLayout(20, 20));
        setBorder(new EmptyBorder(30, 30, 30, 30));
        setBackground(new Color(245, 247, 250));

        JLabel titleLabel = new JLabel("Register");
        titleLabel.setFont(new Font("Roboto", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(245, 247, 250));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(245, 247, 250));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        String[] labels = { "Name:", "Surname:", "Sex (M/F):", "Phone:", "State:", "Province:", "CAP:", "Street:", "Civic No:", "Username:", "Email:", "Password:" };
        JTextField[] fields = new JTextField[labels.length];

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i;
            formPanel.add(new JLabel(labels[i]), gbc);

            gbc.gridx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            fields[i] = (i == labels.length - 1) ? new JPasswordField(20) : new JTextField(20);
            formPanel.add(fields[i], gbc);
        }

        gbc.gridx = 0;
        gbc.gridy = labels.length;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.EAST;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        JButton submitButton = new JButton("Register");
        JButton cancelButton = new JButton("Cancel");

        styleButton(submitButton, new Color(40, 167, 69));
        styleButton(cancelButton, new Color(220, 53, 69));

        submitButton.addActionListener(e -> {
            boolean valid = true;
            StringBuilder errors = new StringBuilder();

            for (int i = 0; i < fields.length; i++) {
                if (fields[i].getText().trim().isEmpty()) {
                    valid = false;
                    errors.append("- ").append(labels[i]).append("\n");
                }
            }

            if (!valid) {
                JOptionPane.showMessageDialog(this, "Insert the following fields:\n" + errors, "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (actionListener != null) {
                actionListener.onRegisterSubmit(
                        fields[0].getText().trim(), fields[1].getText().trim(), fields[2].getText().trim(),
                        fields[3].getText().trim(), fields[4].getText().trim(), fields[5].getText().trim(),
                        fields[6].getText().trim(), fields[7].getText().trim(), fields[8].getText().trim(),
                        fields[9].getText().trim(), fields[10].getText().trim(),
                        new String(((JPasswordField) fields[11]).getPassword())
                );
            }
        });

        cancelButton.addActionListener(e -> {
            if (actionListener != null) actionListener.onRegisterCancel();
        });

        buttonPanel.add(cancelButton);
        buttonPanel.add(submitButton);

        formPanel.add(buttonPanel, gbc);

        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(null);

        contentPanel.add(scrollPane, BorderLayout.CENTER);
        add(contentPanel, BorderLayout.CENTER);
    }

    private void styleButton(JButton button, Color bgColor) {
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Roboto", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    public interface UserActionListener {
        void onRegisterSubmit(String name, String surname, String sex, String phoneNumber, String stateResidency,
                              String province, String cap, String street, String streetCode, String username, String email,
                              String password);

        void onRegisterCancel();
    }
}
