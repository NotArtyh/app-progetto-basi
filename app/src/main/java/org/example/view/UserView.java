package org.example.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

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
    private int currentPage = 1;
    private final int totalPages = 5; // Change as needed based on user count

    // Interface per gestire le azioni dei bottoni
    public interface UserActionListener {
        void onRegisterUser();

        void onAuthenticateUser();

        void onExit();
    }

    // New interface for form submissions
    public interface FormActionListener {
        void onRegisterSubmit(RegistrationData data);

        void onLoginSubmit(String username, String password);

        void onLogout();
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

    // Homepage components
    private JFrame homepageFrame;
    private String currentUsername;

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
        mainFrame = new JFrame("MUSIC TRADES COLLECTION");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(1500, 1100);
        mainFrame.setLocationRelativeTo(null);

        // Set system look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Ignore and use default
        }

        // Create split container (left = image, right = UI)
        JPanel contentPanel = new JPanel(new GridLayout(1, 2)); // 1 row, 2 columns

        // ⬅️ Left side: image panel
        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setVerticalAlignment(SwingConstants.CENTER);
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/images/landing.png"));

        double scaleFactor = 0.45;
        int scaledWidth = (int) (originalIcon.getIconWidth() * scaleFactor);
        int scaledHeight = (int) (originalIcon.getIconHeight() * scaleFactor);

        Image scaledImage = originalIcon.getImage().getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(scaledImage));
        contentPanel.add(imageLabel);

        // ➡️ Right side: your existing layout
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(100, 80, 100, 80)); // Tighter vertical and horizontal padding
        mainPanel.setPreferredSize(new Dimension(600, 600));

        // Create your components
        createHeaderPanel();
        createButtonPanel();
        createOutputPanel();

        JPanel rightContainer = new JPanel(new GridBagLayout());
        rightContainer.add(mainPanel);
        contentPanel.add(rightContainer);

        // Add to frame and show
        mainFrame.add(contentPanel);
        mainFrame.setVisible(true);

        // Optional: show welcome message
        displayWelcome();
    }

    /**
     * Create header panel with title
     */
    private void createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0)); // Top & bottom padding

        JLabel titleLabel = new JLabel("User Sign-In", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Roboto", Font.BOLD, 28));
        titleLabel.setForeground(new Color(30, 30, 30));

        headerPanel.add(titleLabel, BorderLayout.CENTER);
        mainPanel.add(headerPanel, BorderLayout.NORTH);
    }

    /**
     * Create button panel with all action buttons
     */
    private void createButtonPanel() {
        // Main button panel with vertical layout
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create buttons
        JButton registrationButton = createStyledButton("Registration", "👤");
        JButton loginButton = createStyledButton("Login", "🔐");
        JButton exitButton = createStyledButton("Exit", "🚪");

        // Set font size
        Font buttonFont = new Font("Roboto", Font.PLAIN, 16);
        registrationButton.setFont(buttonFont);
        loginButton.setFont(buttonFont);
        exitButton.setFont(buttonFont);

        // Add action listeners
        registrationButton.addActionListener(e -> showRegistrationWindow());
        loginButton.addActionListener(e -> showLoginWindow());
        exitButton.addActionListener(e -> {
            if (actionListener != null)
                actionListener.onExit();
        });

        // Top row panel with GridLayout for side-by-side buttons
        JPanel topRow = new JPanel(new GridLayout(1, 2, 10, 0));
        topRow.add(registrationButton);
        topRow.add(loginButton);
        topRow.setMaximumSize(new Dimension(500, 80)); // fix height

        // Exit button container (to span full width)
        JPanel bottomRow = new JPanel(new BorderLayout());
        bottomRow.add(exitButton, BorderLayout.CENTER);
        bottomRow.setMaximumSize(new Dimension(500, 80));

        // Add rows to main panel
        buttonPanel.add(topRow);
        buttonPanel.add(Box.createVerticalStrut(10)); // spacing
        buttonPanel.add(bottomRow);

        // Add to main UI
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
    }

    /**
     * Create styled button with icon
     */
    private JButton createStyledButton(String text, String icon) {
        JButton button = new JButton(icon + " " + text);
        button.setFont(new Font("Roboto", Font.PLAIN, 11));
        button.setPreferredSize(new Dimension(120, 40));
        button.setFocusPainted(false);
        button.setHorizontalAlignment(SwingConstants.CENTER);
        return button;
    }

    /**
     * Show registration window with form fields
     */
    private void showRegistrationWindow() {
        JDialog registrationDialog = new JDialog(mainFrame, "User Registration", true);
        registrationDialog.setSize(900, 800);
        registrationDialog.setLocationRelativeTo(mainFrame);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Header
        JLabel headerLabel = new JLabel("NEW USER REGISTRATION");
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
                JOptionPane.showMessageDialog(registrationDialog,
                        "Errori nella compilazione:\n" + errors.toString(),
                        "Error", JOptionPane.ERROR_MESSAGE);
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
                    new String(passwordField.getPassword()));

            if (formActionListener != null) {
                formActionListener.onRegisterSubmit(data);
            }

            registrationDialog.dispose();
        });

        cancelButton.addActionListener(e -> registrationDialog.dispose());

        buttonPanel.add(cancelButton);
        buttonPanel.add(submitButton);

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
        JDialog loginDialog = new JDialog(mainFrame, "User login", true);
        loginDialog.setSize(600, 400);
        loginDialog.setLocationRelativeTo(mainFrame);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Header
        JLabel headerLabel = new JLabel("USER LOGIN");
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
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
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
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        formPanel.add(passwordField, gbc);

        // Button panel
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        JButton loginButton = new JButton("Login");
        JButton cancelButton = new JButton("Cancel");

        loginButton.setBackground(new Color(40, 167, 69));
        loginButton.setForeground(Color.BLACK);
        loginButton.setFont(new Font("Arial", Font.BOLD, 12));

        cancelButton.setBackground(new Color(220, 53, 69));
        cancelButton.setForeground(Color.BLACK);
        cancelButton.setFont(new Font("Arial", Font.BOLD, 12));

        loginButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(loginDialog,
                        "Username e Password sono obbligatori!",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (formActionListener != null) {
                formActionListener.onLoginSubmit(username, password);
            }

            loginDialog.dispose();
        });

        cancelButton.addActionListener(e -> loginDialog.dispose());

        buttonPanel.add(cancelButton);
        buttonPanel.add(loginButton);

        // Add components to main panel
        mainPanel.add(headerLabel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        loginDialog.add(mainPanel);
        loginDialog.setVisible(true);
    }

    /**
     * Create output panel for displaying results
     */
    private void createOutputPanel() {
        JPanel outputPanel = new JPanel(new BorderLayout());

        // Text area for general output
        outputArea = new JTextArea();
        outputArea.setFont(new Font("Courier New", Font.PLAIN, 12));
        outputArea.setEditable(false);
        outputArea.setBackground(new Color(248, 248, 248));

        JScrollPane tableScrollPane = new JScrollPane(userTable);
        tableScrollPane.setPreferredSize(new Dimension(0, 300));

        mainPanel.add(outputPanel, BorderLayout.SOUTH);
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

            // Show homepage instead of just a message
            showHomepage(username);
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
     * Display vetrina page
     **/
    private void showVetrinaPage() {
        JFrame vetrinaFrame = new JFrame("Vetrina");
        vetrinaFrame.setSize(1200, 800);
        vetrinaFrame.setLocationRelativeTo(null);
        vetrinaFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Vetrina - User Inventories");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel usersPanel = new JPanel(new GridLayout(5, 1, 10, 10)); // 5 utenti per pagina

        // Method to populate users for the selected page
        Runnable populateUsers = () -> {
            usersPanel.removeAll();
            for (int i = 1; i <= 5; i++) {
                int userNumber = (currentPage - 1) * 5 + i;

                JPanel userPanel = new JPanel(new BorderLayout());
                userPanel.setBorder(BorderFactory.createTitledBorder("User " + userNumber));

                JPanel itemsPanel = new JPanel(new GridLayout(4, 5, 5, 5)); // 20 items per utente
                for (int j = 1; j <= 20; j++) {
                    JLabel itemLabel = new JLabel("Item " + j, SwingConstants.CENTER);
                    itemLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                    itemsPanel.add(itemLabel);
                }

                userPanel.add(itemsPanel, BorderLayout.CENTER);
                usersPanel.add(userPanel);
            }
            usersPanel.revalidate();
            usersPanel.repaint();
        };

        JScrollPane scrollPane = new JScrollPane(usersPanel);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Bottom pagination panel
        JPanel paginationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        for (int i = 1; i <= totalPages; i++) {
            int pageNum = i;
            JButton pageButton = new JButton(String.valueOf(i));
            pageButton.addActionListener(e -> {
                currentPage = pageNum;
                populateUsers.run();
            });
            paginationPanel.add(pageButton);
        }

        mainPanel.add(paginationPanel, BorderLayout.SOUTH);

        vetrinaFrame.add(mainPanel);

        // Initialize with page 1
        currentPage = 1;
        populateUsers.run();

        vetrinaFrame.setVisible(true);
    }

    /**
     * Show inventory page
     */
    private void showInventoryPage() {
        JFrame inventoryFrame = new JFrame("My Inventory");
        inventoryFrame.setSize(1000, 700);
        inventoryFrame.setLocationRelativeTo(null);
        inventoryFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("My Inventory");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Placeholder items list (replace with real data from inventory later)
        JPanel itemsPanel = new JPanel(new GridLayout(0, 5, 10, 10)); // Dynamic rows, 5 columns

        for (int i = 1; i <= 30; i++) {
            JLabel itemLabel = new JLabel("Item " + i, SwingConstants.CENTER);
            itemLabel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
            itemLabel.setPreferredSize(new Dimension(150, 100));
            itemsPanel.add(itemLabel);
        }

        JScrollPane scrollPane = new JScrollPane(itemsPanel);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        inventoryFrame.add(mainPanel);
        inventoryFrame.setVisible(true);
    }

    public void showHomepage(String username) {
        this.currentUsername = username;

        // Hide main frame
        mainFrame.setVisible(false);

        // Create homepage frame
        homepageFrame = new JFrame("Homepage");
        homepageFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        homepageFrame.setSize(1500, 1000);
        homepageFrame.setLocationRelativeTo(null);
        homepageFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Main panel
        JPanel homepageMainPanel = new JPanel(new BorderLayout());

        // Top bar with user info and logout
        JPanel topBar = createTopBar(username);
        homepageMainPanel.add(topBar, BorderLayout.NORTH);

        // Content area
        JPanel contentPanel = createHomepageContent();
        homepageMainPanel.add(contentPanel, BorderLayout.CENTER);

        homepageFrame.add(homepageMainPanel);
        homepageFrame.setVisible(true);
    }

    /**
     * Create top bar with user info and logout button
     */
    private JPanel createTopBar(String username) {
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(51, 122, 183));
        topBar.setBorder(new EmptyBorder(10, 20, 10, 20));
        topBar.setPreferredSize(new Dimension(0, 80));

        // Left side - Welcome message
        JLabel welcomeLabel = new JLabel("Welcome in MUSIC TRADES COLLECTION!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeLabel.setForeground(Color.WHITE);

        // Right side - User info and logout
        JPanel rightPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        rightPanel.setOpaque(false);

        // User info
        JLabel userLabel = new JLabel("User: " + username);
        userLabel.setFont(new Font("Arial", Font.BOLD, 18));
        userLabel.setForeground(Color.WHITE);
        userLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        // Logout button
        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.BOLD, 12));
        logoutButton.setBackground(new Color(189, 48, 62)); // Red color
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        logoutButton.setOpaque(true); // Ensure background color is applied
        logoutButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        logoutButton.addActionListener(e -> handleLogout());
        logoutButton.setUI(new javax.swing.plaf.basic.BasicButtonUI()); // Force UI to respect background color

        rightPanel.add(userLabel);
        rightPanel.add(logoutButton);

        topBar.add(welcomeLabel, BorderLayout.WEST);
        topBar.add(rightPanel, BorderLayout.EAST);

        return topBar;
    }

    /**
     * Create homepage content
     */
    private JPanel createHomepageContent() {
        JPanel contentPanel = new JPanel(new BorderLayout(20, 20));
        contentPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
        contentPanel.setBackground(new Color(202, 207, 240));

        // Dashboard header
        JLabel dashboardLabel = new JLabel("Dashboard");
        dashboardLabel.setFont(new Font("Arial", Font.BOLD, 50));
        dashboardLabel.setForeground(new Color(52, 58, 64));
        dashboardLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Cards panel
        JPanel cardsPanel = new JPanel(new GridLayout(1, 3, 20, 20));
        cardsPanel.setOpaque(false);

        // Create dashboard cards
        JPanel vetrinaCard = createDashboardCard("👀", "Vetrina", "View all other users' items and propose new trades",
                new Color(40, 167, 69));

        // Add mouse listener to vetrinaCard to show vetrina page
        vetrinaCard.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                showVetrinaPage();
            }
        });
        JPanel inventoryCard = createDashboardCard("👤", "My Inventory", "View and edit your inventory",
                new Color(23, 162, 184));

        // Add mouse listener to inventoryCard to show inventory page
        inventoryCard.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                showInventoryPage();
            }
        });

        JPanel profileCard = createDashboardCard("⚙️", "My profile", "Show your personal data and your trade offers",
                new Color(255, 193, 7));

        // Change background color for each card
        vetrinaCard.setBackground(new Color(220, 255, 220)); // Light green background
        inventoryCard.setBackground(new Color(220, 240, 255)); // Light blue background
        profileCard.setBackground(new Color(255, 245, 220)); // Light yellow background

        // Add hover effect to reset background color
        for (JPanel card : new JPanel[] { vetrinaCard, inventoryCard, profileCard }) {
            card.addMouseListener(new java.awt.event.MouseAdapter() {
                private Color originalColor = card.getBackground();

                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    card.setBackground(new Color(248, 249, 250)); // Hover color
                    card.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {
                    card.setBackground(originalColor); // Reset to original color
                    card.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                }
            });
        }

        // Center icon, title, and description for each card
        for (JPanel card : new JPanel[] { vetrinaCard, inventoryCard, profileCard }) {
            JPanel headerPanel = (JPanel) card.getComponent(0);
            headerPanel.setLayout(new GridLayout(2, 1, 0, 5)); // Adjust layout for centering

            JLabel iconLabel = (JLabel) headerPanel.getComponent(0);
            iconLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center icon
            iconLabel.setFont(new Font("Arial", Font.PLAIN, 50)); // Increase icon size

            JLabel titleLabel = (JLabel) headerPanel.getComponent(1);
            titleLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center title
            titleLabel.setFont(new Font("Arial", Font.BOLD, 50)); // Increase title size

            JLabel descLabel = (JLabel) card.getComponent(1);
            descLabel.setHorizontalAlignment(SwingConstants.CENTER);
            descLabel.setFont(new Font("Arial", Font.PLAIN, 30)); // Center description
        }
        cardsPanel.add(vetrinaCard);
        cardsPanel.add(inventoryCard);
        cardsPanel.add(profileCard);

        contentPanel.add(dashboardLabel, BorderLayout.NORTH);
        contentPanel.add(cardsPanel, BorderLayout.CENTER);

        return contentPanel;
    }

    /**
     * Create dashboard card
     */
    private JPanel createDashboardCard(String icon, String title, String description, Color color) {
        JPanel card = new JPanel(new BorderLayout(10, 10));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(222, 226, 230), 1),
                new EmptyBorder(20, 20, 20, 20)));

        // Icon and title
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Arial", Font.PLAIN, 48));
        iconLabel.setForeground(color);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(52, 58, 64));

        headerPanel.add(iconLabel, BorderLayout.WEST);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        // Description
        JLabel descLabel = new JLabel(description);
        descLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        descLabel.setForeground(new Color(108, 117, 125));

        card.add(headerPanel, BorderLayout.NORTH);
        card.add(descLabel, BorderLayout.CENTER);

        // Add hover effect
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                card.setBackground(new Color(248, 249, 250));
                card.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                card.setBackground(Color.WHITE);
                card.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            }
        });

        return card;
    }

    /**
     * Handle logout action
     */
    private void handleLogout() {
        boolean confirmed = showConfirmation("Are you sure you want to logout?");

        if (confirmed) {
            // Close homepage
            if (homepageFrame != null) {
                homepageFrame.dispose();
                homepageFrame = null;
            }

            // Show main frame again
            mainFrame.setVisible(true);

            // Clear current user
            currentUsername = null;

            // Display logout message
            displayMessage("Logged out successfully.");

            // Notify the form action listener
            if (formActionListener != null) {
                formActionListener.onLogout();
            }
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