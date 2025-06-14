package org.example.view.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.example.database.ItemDAO;
import org.example.database.MediaDAO;
import org.example.database.UserDAO;
import org.example.model.Item;
import org.example.model.User;

public class UsersInventoryPanel extends JPanel {
    
    private TradeRequestListener tradeRequestListener;
    
    private static final int GRID_SIZE = 4;
    private static final int ITEMS_PER_PAGE = GRID_SIZE * GRID_SIZE;
    private int currentPage = 0;
    
    private List<User> allUsers;
    private User selectedUser;
    private List<Item> selectedUserItems;
    
    private JPanel usersPanel;
    private JPanel itemsGridPanel;
    private JLabel selectedUserLabel;
    private JLabel pageLabel;
    private JButton prevButton;
    private JButton nextButton;
    private JButton refreshButton;
    private MediaDAO mediaDAO;
    private ItemDAO itemDAO ;
    private UserDAO userDAO;

    public UsersInventoryPanel() {
        this.mediaDAO = new MediaDAO();
        this.allUsers = new ArrayList<>();
        this.selectedUserItems = new ArrayList<>();
        this.userDAO = new UserDAO();
        this.itemDAO = new ItemDAO();
        
        initializePanel();
        loadAllUsers();
    }

    public void setTradeRequestListener(TradeRequestListener listener) {
        this.tradeRequestListener = listener;
    }

    private void initializePanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(15, 15, 15, 15));
        setBackground(new Color(248, 249, 250));

        // Titolo principale
        JLabel titleLabel = new JLabel("Vetrina", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Roboto", Font.BOLD, 20));
        titleLabel.setBorder(new EmptyBorder(0, 0, 15, 0));
        titleLabel.setForeground(new Color(33, 37, 41));
        add(titleLabel, BorderLayout.NORTH);

        // Pannello principale diviso in due sezioni
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(248, 249, 250));

        // Sezione utenti (in alto)
        createUsersSection(mainPanel);

        // Sezione inventario utente selezionato (in basso)
        createInventorySection(mainPanel);

        add(mainPanel, BorderLayout.CENTER);
    }

    private void createUsersSection(JPanel parent) {
        JPanel usersSection = new JPanel(new BorderLayout(5, 5));
        usersSection.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(206, 212, 218)), 
            "Seleziona Utente"
        ));
        usersSection.setBackground(Color.WHITE);
        usersSection.setPreferredSize(new Dimension(0, 120));

        usersPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        usersPanel.setBackground(Color.WHITE);

        JScrollPane usersScrollPane = new JScrollPane(usersPanel);
        usersScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        usersScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        usersScrollPane.setPreferredSize(new Dimension(0, 90));

        usersSection.add(usersScrollPane, BorderLayout.CENTER);
        parent.add(usersSection, BorderLayout.NORTH);
    }

    private void createInventorySection(JPanel parent) {
        JPanel inventorySection = new JPanel(new BorderLayout(5, 5));
        inventorySection.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(206, 212, 218)), 
            "Inventario Utente"
        ));
        inventorySection.setBackground(Color.WHITE);

        // Label per utente selezionato
        selectedUserLabel = new JLabel("Nessun utente selezionato", SwingConstants.CENTER);
        selectedUserLabel.setFont(new Font("Roboto", Font.BOLD, 14));
        selectedUserLabel.setBorder(new EmptyBorder(5, 0, 10, 0));
        selectedUserLabel.setForeground(new Color(108, 117, 125));
        inventorySection.add(selectedUserLabel, BorderLayout.NORTH);

        // Griglia per gli item
        itemsGridPanel = new JPanel(new GridLayout(GRID_SIZE, GRID_SIZE, 8, 8));
        itemsGridPanel.setBackground(Color.WHITE);
        itemsGridPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JScrollPane itemsScrollPane = new JScrollPane(itemsGridPanel);
        itemsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        itemsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        inventorySection.add(itemsScrollPane, BorderLayout.CENTER);

        // Controlli di navigazione
        createNavigationControls(inventorySection);

        parent.add(inventorySection, BorderLayout.CENTER);
    }

    private void createNavigationControls(JPanel parent) {
        JPanel controlPanel = new JPanel(new BorderLayout());
        controlPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
        controlPanel.setBackground(Color.WHITE);

        // Navigazione pagine
        JPanel navigationPanel = new JPanel(new FlowLayout());
        navigationPanel.setBackground(Color.WHITE);

        prevButton = new JButton("◀ Precedente");
        prevButton.setFont(new Font("Roboto", Font.BOLD, 11));
        prevButton.setEnabled(false);
        prevButton.addActionListener(e -> previousPage());

        nextButton = new JButton("Successivo ▶");
        nextButton.setFont(new Font("Roboto", Font.BOLD, 11));
        nextButton.setEnabled(false);
        nextButton.addActionListener(e -> nextPage());

        pageLabel = new JLabel("Pagina 1 di 1", SwingConstants.CENTER);
        pageLabel.setFont(new Font("Roboto", Font.PLAIN, 11));

        navigationPanel.add(prevButton);
        navigationPanel.add(Box.createHorizontalStrut(15));
        navigationPanel.add(pageLabel);
        navigationPanel.add(Box.createHorizontalStrut(15));
        navigationPanel.add(nextButton);

        controlPanel.add(navigationPanel, BorderLayout.CENTER);

        // Pulsante refresh
        refreshButton = new JButton("🔄 Aggiorna");
        refreshButton.setFont(new Font("Roboto", Font.BOLD, 11));
        refreshButton.addActionListener(e -> refreshData());
        controlPanel.add(refreshButton, BorderLayout.EAST);

        parent.add(controlPanel, BorderLayout.SOUTH);
    }

    private void loadAllUsers() {

        try {
            this.allUsers = userDAO.getAllUsers();
            updateUsersPanel();
        } catch (SQLException e) {
            showError("Errore nel caricamento degli utenti: " + e.getMessage());
            this.allUsers = new ArrayList<>();
            updateUsersPanel();
        }


    }


    private void updateUsersPanel() {
        usersPanel.removeAll();

        if (allUsers.isEmpty()) {
            JLabel noUsersLabel = new JLabel("Nessun altro utente trovato");
            noUsersLabel.setForeground(Color.GRAY);
            noUsersLabel.setFont(new Font("Roboto", Font.ITALIC, 12));
            usersPanel.add(noUsersLabel);
        } else {
            for (User user : allUsers) {
                JPanel userPanel = createUserPanel(user);
                usersPanel.add(userPanel);
            }
        }

        usersPanel.revalidate();
        usersPanel.repaint();
    }

    private JPanel createUserPanel(User user) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createRaisedBevelBorder(),
            new EmptyBorder(8, 10, 8, 10)
        ));
        panel.setBackground(new Color(240, 248, 255));
        panel.setPreferredSize(new Dimension(200, 60));

        // Nome utente
        JLabel nameLabel = new JLabel(user.getUsername());
        nameLabel.setFont(new Font("Roboto", Font.BOLD, 12));
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(nameLabel, BorderLayout.CENTER);

        // Pannello pulsanti
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 3, 0));
        buttonPanel.setBackground(new Color(240, 248, 255));

        // Pulsante visualizza inventario
        JButton viewInventoryBtn = new JButton("Visualizza");
        viewInventoryBtn.setFont(new Font("Roboto", Font.PLAIN, 10));
        viewInventoryBtn.setPreferredSize(new Dimension(70, 25));
        viewInventoryBtn.addActionListener(e -> selectUser(user));

        // Pulsante richiesta scambio
        JButton tradeRequestBtn = new JButton("Scambio");
        tradeRequestBtn.setFont(new Font("Roboto", Font.BOLD, 10));
        tradeRequestBtn.setBackground(new Color(40, 167, 69));
        tradeRequestBtn.setForeground(Color.WHITE);
        tradeRequestBtn.setPreferredSize(new Dimension(70, 25));
        tradeRequestBtn.addActionListener(e -> requestTrade(user));

        buttonPanel.add(viewInventoryBtn);
        buttonPanel.add(tradeRequestBtn);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void selectUser(User user) {
        this.selectedUser = user;
        this.currentPage = 0;
        
        selectedUserLabel.setText("Inventario di: " + user.getUsername());
        selectedUserLabel.setForeground(new Color(33, 37, 41));
        
        loadUserItems(user.getUserId());
    }

    private void loadUserItems(int userId) {
        try {
            this.selectedUserItems = itemDAO.getItemsByUserId(userId);
            updateItemsGrid();
        } catch (SQLException e) {
           showError("Errore nel caricamento dell'inventario");
            this.selectedUserItems = new ArrayList<>();
            updateItemsGrid();
        }
       
    }

    private void updateItemsGrid() {
        itemsGridPanel.removeAll();

        if (selectedUser == null) {
            // Nessun utente selezionato
            for (int i = 0; i < ITEMS_PER_PAGE; i++) {
                itemsGridPanel.add(createEmptySlotPanel("Seleziona un utente"));
            }
        } else if (selectedUserItems.isEmpty()) {
            // Utente selezionato ma senza item
            JPanel emptyPanel = createEmptyItemPanel("Nessun item nell'inventario");
            itemsGridPanel.add(emptyPanel);
            
            for (int i = 1; i < ITEMS_PER_PAGE; i++) {
                itemsGridPanel.add(createEmptySlotPanel(""));
            }
        } else {
            // Mostra gli item dell'utente
            int startIndex = currentPage * ITEMS_PER_PAGE;
            int endIndex = Math.min(startIndex + ITEMS_PER_PAGE, selectedUserItems.size());

            for (int i = startIndex; i < endIndex; i++) {
                JPanel itemPanel = createItemPanel(selectedUserItems.get(i));
                itemsGridPanel.add(itemPanel);
            }

            // Riempie gli slot vuoti
            for (int i = endIndex - startIndex; i < ITEMS_PER_PAGE; i++) {
                itemsGridPanel.add(createEmptySlotPanel(""));
            }
        }

        updateNavigationControls();
        itemsGridPanel.revalidate();
        itemsGridPanel.repaint();
    }

    private JPanel createItemPanel(Item item) {
        JPanel panel = new JPanel(new BorderLayout(3, 3));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createRaisedBevelBorder(),
            new EmptyBorder(5, 5, 5, 5)
        ));
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(150, 120));

        try {
            String mediaTitle = getMediaTitle(item.getMediaId());

            // Header con titolo
            JPanel headerPanel = new JPanel(new BorderLayout());
            headerPanel.setBackground(new Color(233, 236, 239));
            
            JLabel titleLabel = new JLabel("<html><b>" + truncateText(mediaTitle, 15) + "</b></html>");
            titleLabel.setFont(new Font("Roboto", Font.BOLD, 10));
            titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
            headerPanel.add(titleLabel, BorderLayout.CENTER);

            panel.add(headerPanel, BorderLayout.NORTH);

            // Info panel
            JPanel infoPanel = new JPanel();
            infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
            infoPanel.setBackground(Color.WHITE);
            infoPanel.setBorder(new EmptyBorder(3, 3, 3, 3));

            addInfoRow(infoPanel, "Condizioni:", truncateText(item.getCondizioni(), 12));
            
            if (item.getNote() != null && !item.getNote().trim().isEmpty()) {
                addInfoRow(infoPanel, "Note:", truncateText(item.getNote(), 12));
            }

            String formattedDate = item.getData_acquisizione()
                    .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            addInfoRow(infoPanel, "Acquisito:", formattedDate);

            panel.add(infoPanel, BorderLayout.CENTER);

            // Tooltip
            panel.setToolTipText(createTooltipText(item, mediaTitle));

        } catch (Exception e) {
            JLabel errorLabel = new JLabel("<html><center>Errore<br>Item ID: " + item.getItemId() + "</center></html>");
            errorLabel.setForeground(Color.RED);
            errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
            panel.add(errorLabel, BorderLayout.CENTER);
        }

        return panel;
    }

    private void addInfoRow(JPanel parent, String label, String value) {
        JPanel row = new JPanel(new BorderLayout(2, 0));
        row.setBackground(Color.WHITE);

        JLabel labelComp = new JLabel(label);
        labelComp.setFont(new Font("Roboto", Font.BOLD, 9));
        labelComp.setForeground(new Color(70, 70, 70));

        JLabel valueComp = new JLabel(value);
        valueComp.setFont(new Font("Roboto", Font.PLAIN, 9));

        row.add(labelComp, BorderLayout.WEST);
        row.add(valueComp, BorderLayout.CENTER);

        parent.add(row);
        parent.add(Box.createVerticalStrut(1));
    }

    private JPanel createEmptySlotPanel(String message) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLoweredBevelBorder(),
            new EmptyBorder(5, 5, 5, 5)
        ));
        panel.setBackground(new Color(248, 249, 250));
        panel.setPreferredSize(new Dimension(150, 120));

        if (!message.isEmpty()) {
            JLabel emptyLabel = new JLabel("<html><center>" + message + "</center></html>");
            emptyLabel.setForeground(Color.LIGHT_GRAY);
            emptyLabel.setHorizontalAlignment(SwingConstants.CENTER);
            emptyLabel.setFont(new Font("Roboto", Font.ITALIC, 11));
            panel.add(emptyLabel, BorderLayout.CENTER);
        }

        return panel;
    }

    private JPanel createEmptyItemPanel(String message) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createRaisedBevelBorder(),
            new EmptyBorder(5, 5, 5, 5)
        ));
        panel.setBackground(new Color(255, 248, 220));
        panel.setPreferredSize(new Dimension(150, 120));

        JLabel messageLabel = new JLabel("<html><center>" + message + "</center></html>");
        messageLabel.setForeground(new Color(184, 134, 11));
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel.setFont(new Font("Roboto", Font.BOLD, 11));

        panel.add(messageLabel, BorderLayout.CENTER);
        return panel;
    }

    private String getMediaTitle(int mediaId) throws SQLException {
        return mediaDAO.getTitleById(mediaId);
    }

    private String truncateText(String text, int maxLength) {
        if (text == null) return "";
        return text.length() > maxLength ? text.substring(0, maxLength - 3) + "..." : text;
    }

    private String createTooltipText(Item item, String mediaTitle) {
        return "<html>" +
                "<b>Titolo:</b> " + mediaTitle + "<br>" +
                "<b>Item ID:</b> " + item.getItemId() + "<br>" +
                "<b>Proprietario:</b> " + (selectedUser != null ? selectedUser.getUsername() : "N/A") + "<br>" +
                "<b>Condizioni:</b> " + (item.getCondizioni() != null ? item.getCondizioni() : "N/A") + "<br>" +
                "<b>Note:</b> " + (item.getNote() != null && !item.getNote().isEmpty() ? item.getNote() : "Nessuna nota") + "<br>" +
                "<b>Data Acquisizione:</b> " + item.getData_acquisizione().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) +
                "</html>";
    }

    private void requestTrade(User targetUser) {
        if (tradeRequestListener != null) {
            tradeRequestListener.onTradeRequest(targetUser);
        } else {
            // Fallback: mostra un semplice messaggio
            JOptionPane.showMessageDialog(this, 
                "Funzionalità di scambio non ancora implementata.\nRichiesta per: " + targetUser.getUserId(),
                "Richiesta Scambio", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void updateNavigationControls() {
        if (selectedUserItems == null || selectedUserItems.isEmpty()) {
            pageLabel.setText("Pagina 1 di 1");
            prevButton.setEnabled(false);
            nextButton.setEnabled(false);
            return;
        }

        int totalPages = Math.max(1, (int) Math.ceil((double) selectedUserItems.size() / ITEMS_PER_PAGE));
        pageLabel.setText("Pagina " + (currentPage + 1) + " di " + totalPages);
        prevButton.setEnabled(currentPage > 0);
        nextButton.setEnabled((currentPage + 1) * ITEMS_PER_PAGE < selectedUserItems.size());
    }

    private void previousPage() {
        if (currentPage > 0) {
            currentPage--;
            updateItemsGrid();
        }
    }

    private void nextPage() {
        if (selectedUserItems != null && (currentPage + 1) * ITEMS_PER_PAGE < selectedUserItems.size()) {
            currentPage++;
            updateItemsGrid();
        }
    }

    private void refreshData() {
        currentPage = 0;
        selectedUser = null;
        selectedUserItems.clear();
        selectedUserLabel.setText("Nessun utente selezionato");
        selectedUserLabel.setForeground(new Color(108, 117, 125));
        
        loadAllUsers();
        updateItemsGrid();
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Errore", JOptionPane.ERROR_MESSAGE);
    }

    public interface TradeRequestListener {
        void onTradeRequest(User targetUser);
    }

     public interface UserActionListener {
       
        void refreshData();

        void onViewInventory();

        void onTradeRequest();
    }

}

    


