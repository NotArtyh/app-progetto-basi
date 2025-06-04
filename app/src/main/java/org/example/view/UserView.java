package org.example.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
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
    
    private UserActionListener actionListener;
    
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
        buttons[0].addActionListener(e -> { if (actionListener != null) actionListener.onRegisterUser(); });
        buttons[1].addActionListener(e -> { if (actionListener != null) actionListener.onViewAllUsersTable(); });
        buttons[2].addActionListener(e -> { if (actionListener != null) actionListener.onAuthenticateUser(); });
        buttons[3].addActionListener(e -> { if (actionListener != null) actionListener.onExit(); });
        
        // Add buttons to panel
        for (JButton button : buttons) {
            buttonPanel.add(button);
        }
        
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
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
     * 
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