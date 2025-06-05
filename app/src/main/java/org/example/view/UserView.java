package org.example.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.example.model.User;

/**
 * User View Swing Class
 * Handles all user interface operations using Swing GUI
 */
public class UserView {
    
    private JFrame mainFrame;
    private JPanel mainPanel;
    private JTextArea outputArea;
    private JScrollPane scrollPane;
    private DefaultTableModel tableModel;
    private JTable userTable;
    
    // Interface per gestire le azioni dei bottoni
    public interface UserActionListener {
        void onRegisterUser();
        void onViewAllUsersTable();
        void onAuthenticateUser();
        void onExit();
    }
    
    // New interface for form submissions
    public interface FormActionListener {
        void onRegisterSubmit(RegistrationData data);
        void onLoginSubmit(String username, String password);
    }
    
    // Data class for registration form
    public static class RegistrationData {
        public String nome, cognome, sesso, telefono, stato_residenza;
        public String provincia, cap, via, civico, username, email, password;
        
        public RegistrationData(String nome, String cognome, String sesso, String telefono,
                              String stato_residenza, String provincia, String cap, String via,
                              String civico, String username, String email, String password) {
            this.nome = nome;
            this.cognome = cognome;
            this.sesso = sesso;
            this.telefono = telefono;
            this.stato_residenza = stato_residenza;
            this.provincia = provincia;
            this.cap = cap;
            this.via = via;
            this.civico = civico;
            this.username = username;
            this.email = email;
            this.password = password;
        }
    }
    
    private UserActionListener actionListener;
    private FormActionListener formActionListener;
    
    /**
     * Constructor - Initialize the GUI
     */
    public UserView() {
        initializeGUI();
    }
    
    /**
     * Set the action listener for button events
     */
    public void setActionListener(UserActionListener listener) {
        this.actionListener = listener;
    }
    
    /**
     * Set the form action listener for form submissions
     */
    public void setFormActionListener(FormActionListener listener) {
        this.formActionListener = listener;
    }
    
    /**
     * Initialize the main GUI components
     */
    private void initializeGUI() {
        // Create main frame
        mainFrame = new JFrame("User Management System");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(1500, 1100);
        mainFrame.setLocationRelativeTo(null);
        
        // Set system look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Use default look and feel if system L&F fails
        }
        
        // Create main panel
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        // Create components
        createHeaderPanel();
        createButtonPanel();
        createOutputPanel();
        
        mainFrame.add(mainPanel);
        
        // Show welcome message
        displayWelcome();
    }
    
    /**
     * Create header panel with title
     */
    private void createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(51, 122, 183));
        
        JLabel titleLabel = new JLabel("SCHERMATA INGRESSO UTENTE");
        titleLabel.setFont(new Font("Calibri", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        headerPanel.add(titleLabel);
        headerPanel.setPreferredSize(new Dimension(0, 60));
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
    }
    
    /**
     * Create button panel with all action buttons
     */
    private void createButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        buttonPanel.setBorder(BorderFactory.createTitledBorder("Actions"));
        buttonPanel.setPreferredSize(new Dimension(0, 150));
        
        // Create buttons
        JButton[] buttons = {
            createStyledButton("REGISTRAZIONE UTENTE", "👤"),
            createStyledButton("View Users Table", "📊"),
            createStyledButton("LOGIN UTENTE", "🔐"),
            createStyledButton("ESCI", "🚪")
        };
        
        // Add action listeners
        buttons[0].addActionListener(e -> showRegistrationWindow());
        buttons[1].addActionListener(e -> { if (actionListener != null) actionListener.onViewAllUsersTable(); });
        buttons[2].addActionListener(e -> showLoginWindow());
        buttons[3].addActionListener(e -> { if (actionListener != null) actionListener.onExit(); });
        
        // Add buttons to panel
        for (JButton button : buttons) {
            buttonPanel.add(button);
        }
        
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
    }
    
    /**
     * Show registration window with form fields
     */
    private void showRegistrationWindow() {
        JDialog registrationDialog = new JDialog(mainFrame, "Registrazione Utente", true);
        registrationDialog.setSize(500, 600);
        registrationDialog.setLocationRelativeTo(mainFrame);
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Header
        JLabel headerLabel = new JLabel("REGISTRAZIONE NUOVO UTENTE");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerLabel.setOpaque(true);
        headerLabel.setBackground(new Color(51, 122, 183));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
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
        String[] labels = {"Nome:", "Cognome:", "Sesso (M/F):", "Telefono:", "Stato Residenza:", 
                          "Provincia:", "CAP:", "Via:", "Civico:", "Username:", "Email:", "Password:"};
        JTextField[] fields = {nomeField, cognomeField, sessoField, telefonoField, statoResidenzaField,
                              provinciaField, capField, viaField, civicoField, usernameField, emailField, passwordField};
        
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
        JButton submitButton = new JButton("Registra");
        JButton cancelButton = new JButton("Annulla");
        
        submitButton.setBackground(new Color(40, 167, 69));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFont(new Font("Arial", Font.BOLD, 12));
        
        cancelButton.setBackground(new Color(220, 53, 69));
        cancelButton.setForeground(Color.WHITE);
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
                JOptionPane.showMessageDialog(registrationDialog, 
                    "Errori nella compilazione:\n" + errors.toString(), 
                    "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Create registration data and submit
            RegistrationData data = new RegistrationData(
                nomeField.getText().trim(),
                cognomeField.getText().trim(),
                sessoField.getText().trim(),
                telefonoField.getText().trim(),
                statoResidenzaField.getText().trim(),
                provinciaField.getText().trim(),
                capField.getText().trim(),
                viaField.getText().trim(),
                civicoField.getText().trim(),
                usernameField.getText().trim(),
                emailField.getText().trim(),
                new String(passwordField.getPassword())
            );
            
            if (formActionListener != null) {
                formActionListener.onRegisterSubmit(data);
            }
            
            registrationDialog.dispose();
        });
        
        cancelButton.addActionListener(e -> registrationDialog.dispose());
        
        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);
        
        // Add components to main panel
        mainPanel.add(headerLabel, BorderLayout.NORTH);
        
        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        registrationDialog.add(mainPanel);
        registrationDialog.setVisible(true);
    }
    
    /**
     * Show login window with username and password fields
     */
    private void showLoginWindow() {
        JDialog loginDialog = new JDialog(mainFrame, "Login Utente", true);
        loginDialog.setSize(400, 250);
        loginDialog.setLocationRelativeTo(mainFrame);
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Header
        JLabel headerLabel = new JLabel("LOGIN UTENTE");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerLabel.setOpaque(true);
        headerLabel.setBackground(new Color(51, 122, 183));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        JTextField usernameField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);
        
        // Username
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(usernameLabel, gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        formPanel.add(usernameField, gbc);
        
        // Password
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(passwordLabel, gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        formPanel.add(passwordField, gbc);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        JButton loginButton = new JButton("Login");
        JButton cancelButton = new JButton("Annulla");
        
        loginButton.setBackground(new Color(40, 167, 69));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 12));
        
        cancelButton.setBackground(new Color(220, 53, 69));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFont(new Font("Arial", Font.BOLD, 12));
        
        loginButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());
            
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(loginDialog, 
                    "Username e Password sono obbligatori!", 
                    "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (formActionListener != null) {
                formActionListener.onLoginSubmit(username, password);
            }
            
            loginDialog.dispose();
        });
        
        cancelButton.addActionListener(e -> loginDialog.dispose());
        
        buttonPanel.add(loginButton);
        buttonPanel.add(cancelButton);
        
        // Add components to main panel
        mainPanel.add(headerLabel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        loginDialog.add(mainPanel);
        loginDialog.setVisible(true);
    }
    
    /**
     * Create styled button with icon
     */
    private JButton createStyledButton(String text, String icon) {
        JButton button = new JButton("<html><center>" + icon + "<br>" + text + "</center></html>");
        button.setFont(new Font("Arial", Font.PLAIN, 11));
        button.setPreferredSize(new Dimension(120, 40));
        button.setFocusPainted(false);
        return button;
    }
    
    /**
     * Create output panel for displaying results
     */
    private void createOutputPanel() {
        JPanel outputPanel = new JPanel(new BorderLayout());
        outputPanel.setBorder(BorderFactory.createTitledBorder("Output"));
        
        // Text area for general output
        outputArea = new JTextArea();
        outputArea.setFont(new Font("Courier New", Font.PLAIN, 12));
        outputArea.setEditable(false);
        outputArea.setBackground(new Color(248, 248, 248));
        
        scrollPane = new JScrollPane(outputArea);
        scrollPane.setPreferredSize(new Dimension(0, 300));
        
        // Table for user data
        String[] columnNames = {"User ID", "Persona ID", "Inventory ID", "Level", "Username", "Email"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };
        
        userTable = new JTable(tableModel);
        userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        userTable.getTableHeader().setBackground(new Color(51, 122, 183));
        userTable.getTableHeader().setForeground(Color.WHITE);
        userTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        
        JScrollPane tableScrollPane = new JScrollPane(userTable);
        tableScrollPane.setPreferredSize(new Dimension(0, 300));
        
        // Tabbed pane to switch between text output and table
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Text Output", scrollPane);
        tabbedPane.addTab("Table View", tableScrollPane);
        
        outputPanel.add(tabbedPane, BorderLayout.CENTER);
        mainPanel.add(outputPanel, BorderLayout.SOUTH);
    }
    
    /**
     * Display users in table format
     */
    public void displayUsersTable(List<User> users) {
        // Clear existing data
        tableModel.setRowCount(0);
        
        if (users.isEmpty()) {
            displayMessage("No users found.");
            return;
        }
        
        // Add users to table
        for (User user : users) {
            Object[] rowData = {
                user.getUserId(),
                user.getPersonaId(),
                user.getInventoryId(),
                user.getLivello(),
                user.getUsername(),
                user.getEmail()
            };
            tableModel.addRow(rowData);
        }
        
        // Switch to table tab
        JTabbedPane tabbedPane = (JTabbedPane) ((JPanel) mainPanel.getComponent(2)).getComponent(0);
        tabbedPane.setSelectedIndex(1);
        
        displayMessage("Table updated with " + users.size() + " users.");
    }
    
    /**
     * Display success message
     */
    public void displayMessage(String message) {
        appendToOutput("✓ INFO: " + message + "\n");
    }
    
    /**
     * Display error message
     */
    public void displayError(String error) {
        appendToOutput("✗ ERROR: " + error + "\n");
        
        // Also show as popup for important errors
        JOptionPane.showMessageDialog(mainFrame, error, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Display warning message
     */
    public void displayWarning(String warning) {
        appendToOutput("⚠ WARNING: " + warning + "\n");
    }
    
    /**
     * Display welcome message
     */
    public void displayWelcome() {
        StringBuilder sb = new StringBuilder();
        sb.append("══════════════════════════════════════════════════\n");
        sb.append("      WELCOME TO USER MANAGEMENT SYSTEM\n");
        sb.append("══════════════════════════════════════════════════\n");
        sb.append("Select an action from the buttons above to get started.\n\n");
        
        appendToOutput(sb.toString());
    }
    
    /**
     * Display goodbye message
     */
    public void displayGoodbye() {
        StringBuilder sb = new StringBuilder();
        sb.append("════════════════════════════════════════\n");
        sb.append("      Thank you for using the system!\n");
        sb.append("              Goodbye! 👋\n");
        sb.append("════════════════════════════════════════\n");
        
        appendToOutput(sb.toString());
        
        // Show goodbye dialog
        JOptionPane.showMessageDialog(mainFrame, 
            "Thank you for using the User Management System!\nGoodbye! 👋", 
            "Goodbye", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Display authentication result
     */
    public void displayAuthenticationResult(String username, boolean success) {
        if (success) {
            String message = "✓ Authentication successful!\n  Welcome back, " + username + "!\n";
            appendToOutput(message);
            JOptionPane.showMessageDialog(mainFrame, 
                "Welcome back, " + username + "!", 
                "Authentication Successful", 
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            String message = "✗ Authentication failed!\n  Invalid username or password.\n";
            appendToOutput(message);
            JOptionPane.showMessageDialog(mainFrame, 
                "Invalid username or password.", 
                "Authentication Failed", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Get user input via dialog
     */
    public String getUserInput(String prompt) {
        return JOptionPane.showInputDialog(mainFrame, prompt);
    }
    
    /**
     * Get user input with default value
     */
    public String getUserInput(String prompt, String defaultValue) {
        return (String) JOptionPane.showInputDialog(mainFrame, prompt, "Input", 
            JOptionPane.QUESTION_MESSAGE, null, null, defaultValue);
    }
    
    /**
     * Show confirmation dialog
     */
    public boolean showConfirmation(String message) {
        int result = JOptionPane.showConfirmDialog(mainFrame, message, "Confirmation", 
            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return result == JOptionPane.YES_OPTION;
    }
    
    /**
     * Append text to output area
     */
    private void appendToOutput(String text) {
        outputArea.append(text);
        outputArea.setCaretPosition(outputArea.getDocument().getLength());
    }
    
    /**
     * Show the main window
     */
    public void show() {
        mainFrame.setVisible(true);
    }
    
    /**
     * Hide the main window
     */
    public void hide() {
        mainFrame.setVisible(false);
    }
    
    /**
     * Close the application
     */
    public void close() {
        mainFrame.dispose();
        System.exit(0);
    }
    
    /**
     * Get the main frame for centering dialogs
     */
    public JFrame getMainFrame() {
        return mainFrame;
    }
}