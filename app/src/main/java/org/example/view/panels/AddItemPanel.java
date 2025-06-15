package org.example.view.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.example.database.MediaDAO;

public class AddItemPanel extends JPanel {
    private UserActionListener actionListener;
    private JComboBox<String> titleComboBox;

    public AddItemPanel() {
        createAddItemPanel();
    }

    public void setActionListener(UserActionListener actionListener) {
        this.actionListener = actionListener;
    }

    private void createAddItemPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Create form fields
        titleComboBox = createTitleComboBox();
        JTextField conditionField = new JTextField(20);

        // Create text area for notes
        JTextArea noteArea = new JTextArea(4, 20);
        noteArea.setLineWrap(true);
        noteArea.setWrapStyleWord(true);
        JScrollPane noteScrollPane = new JScrollPane(noteArea);
        noteScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        String[] labels = { "Title:", "Condition:", "Notes:" };
        Component[] components = { titleComboBox, conditionField, noteScrollPane };

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i;
            JLabel label = new JLabel(labels[i]);
            label.setFont(new Font("Roboto", Font.PLAIN, 12));
            formPanel.add(label, gbc);

            gbc.gridx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weightx = 1.0;
            formPanel.add(components[i], gbc);
            gbc.weightx = 0;
            gbc.fill = GridBagConstraints.NONE;
        }

        // Button panel
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        JButton submitButton = new JButton("Register");
        JButton cancelButton = new JButton("Cancel");

        submitButton.setBackground(new Color(40, 167, 69));
        submitButton.setForeground(Color.BLACK);
        submitButton.setFont(new Font("Roboto", Font.BOLD, 12));

        cancelButton.setBackground(new Color(220, 53, 69));
        cancelButton.setForeground(Color.BLACK);
        cancelButton.setFont(new Font("Roboto", Font.BOLD, 12));

        submitButton.addActionListener(e -> {
            boolean valid = true;
            StringBuilder errors = new StringBuilder();

            // here should go validation logic but too lazy to do it now
            if (!valid) {
                // Display Error Message
                return;
            }

            // Create item data and submit
            String title = (String) titleComboBox.getSelectedItem();
            String condition = conditionField.getText().trim();
            String note = noteArea.getText().trim();

            if (actionListener != null) {
                actionListener.onRegisterItemSubmit(title, condition, note);
            }
        });

        cancelButton.addActionListener(e -> {
            if (actionListener != null)
                actionListener.onRegisterItemCancel();
        });

        buttonPanel.add(cancelButton);
        buttonPanel.add(submitButton);

        // Layout everything
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);
    }

    private JComboBox<String> createTitleComboBox() {
        JComboBox<String> comboBox = new JComboBox<>();

        // Carica i titoli dal database
        loadTitlesFromDatabase(comboBox);

        // Imposta il numero massimo di righe visibili senza scorrimento
        comboBox.setMaximumRowCount(10);

        return comboBox;
    }

    private void loadTitlesFromDatabase(JComboBox<String> comboBox) {
        MediaDAO mediaDAO = new MediaDAO();

        try {
            List<String> titles = mediaDAO.getAllTitles();

            // Aggiungi un'opzione vuota per default
            comboBox.addItem("-- Select a media title --");

            // Aggiungi tutti i titoli al combobox
            for (String title : titles) {
                comboBox.addItem(title);
            }

        } catch (SQLException e) {
            // Gestisci l'errore mostrando un messaggio
            JOptionPane.showMessageDialog(this,
                    "Error in loading titles: " + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);

            // Aggiungi almeno un'opzione di default
            comboBox.addItem("-- Loading error --");
        }
    }

    public interface UserActionListener {
        void onRegisterItemSubmit(String title, String condition, String note);

        void onRegisterItemCancel();
    }
}