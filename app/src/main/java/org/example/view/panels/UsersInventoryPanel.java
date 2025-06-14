package org.example.view.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
    
    private static final int GRID_COLUMNS = 4;
    
    private List<User> allUsers;
    
    private JPanel mainScrollPanel;
    private MediaDAO mediaDAO;
    private ItemDAO itemDAO;
    private UserDAO userDAO;

    public UsersInventoryPanel() {
        this.mediaDAO = new MediaDAO();
        this.itemDAO = new ItemDAO();
        this.allUsers = new ArrayList<>();
        this.userDAO = new UserDAO();
        
        initializePanel();
        
        // Chiamiamo il caricamento in modo sicuro
        javax.swing.SwingUtilities.invokeLater(() -> {
            loadAllUsersWithInventories();
        });
    }

    public void setTradeRequestListener(TradeRequestListener listener) {
        this.tradeRequestListener = listener;
    }

    private void initializePanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(15, 15, 15, 15));
        setBackground(new Color(248, 249, 250));

        // Titolo principale
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(248, 249, 250));
        
        JLabel titleLabel = new JLabel("Vetrina", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Roboto", Font.BOLD, 20));
        titleLabel.setBorder(new EmptyBorder(0, 0, 15, 0));
        titleLabel.setForeground(new Color(33, 37, 41));
        
        
       
        
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        
        add(headerPanel, BorderLayout.NORTH);

        // Pannello principale scorrevole
        mainScrollPanel = new JPanel();
        mainScrollPanel.setLayout(new BoxLayout(mainScrollPanel, BoxLayout.Y_AXIS));
        mainScrollPanel.setBackground(new Color(248, 249, 250));

        JScrollPane scrollPane = new JScrollPane(mainScrollPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        add(scrollPane, BorderLayout.CENTER);
    }

    private void loadAllUsersWithInventories() {
        try {
            this.allUsers = userDAO.getAllUsersExceptCurrent(1);
            System.out.println("Caricati " + allUsers.size() + " utenti");
            updateMainPanel();
        } catch (SQLException e) {
            System.err.println("Errore SQL: " + e.getMessage());
            e.printStackTrace();
            showError("Errore nel caricamento degli utenti: " + e.getMessage());
            this.allUsers = new ArrayList<>();
            updateMainPanel();
        } catch (Exception e) {
            System.err.println("Errore generico: " + e.getMessage());
            e.printStackTrace();
            showError("Errore imprevisto: " + e.getMessage());
            this.allUsers = new ArrayList<>();  
            updateMainPanel();
        }
    }

    private void updateMainPanel() {
        mainScrollPanel.removeAll();

        if (allUsers.isEmpty()) {
            JPanel noUsersPanel = new JPanel(new BorderLayout());
            noUsersPanel.setBackground(Color.WHITE);
            noUsersPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(206, 212, 218)),
                new EmptyBorder(20, 20, 20, 20)
            ));
            
            JLabel noUsersLabel = new JLabel("Nessun altro utente trovato", SwingConstants.CENTER);
            noUsersLabel.setForeground(Color.GRAY);
            noUsersLabel.setFont(new Font("Roboto", Font.ITALIC, 14));
            noUsersPanel.add(noUsersLabel, BorderLayout.CENTER);
            
            mainScrollPanel.add(noUsersPanel);
        } else {
            for (User user : allUsers) {
                JPanel userSection = createUserSectionPanel(user);
                mainScrollPanel.add(userSection);
                mainScrollPanel.add(Box.createVerticalStrut(15));
            }
        }

        mainScrollPanel.revalidate();
        mainScrollPanel.repaint();
    }

    private JPanel createUserSectionPanel(User user) {
        JPanel sectionPanel = new JPanel(new BorderLayout(10, 10));
        sectionPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(206, 212, 218)),
            new EmptyBorder(15, 15, 15, 15)
        ));
        sectionPanel.setBackground(Color.WHITE);

        // Header con nome utente e pulsante scambio
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);

        JLabel userLabel = new JLabel(user.getUsername());
        userLabel.setFont(new Font("Roboto", Font.BOLD, 16));
        userLabel.setForeground(new Color(33, 37, 41));

        JButton tradeRequestBtn = new JButton("Richiedi Scambio");
        tradeRequestBtn.setFont(new Font("Roboto", Font.BOLD, 12));
        tradeRequestBtn.setBackground(new Color(40, 167, 69));
        tradeRequestBtn.setForeground(Color.WHITE);
        tradeRequestBtn.setPreferredSize(new Dimension(150, 35));
        tradeRequestBtn.addActionListener(e -> requestTrade(user));

        headerPanel.add(userLabel, BorderLayout.WEST);
        headerPanel.add(tradeRequestBtn, BorderLayout.EAST);

        sectionPanel.add(headerPanel, BorderLayout.NORTH);

        // Pannello inventario
        JPanel inventoryPanel = createInventoryPanel(user);
        sectionPanel.add(inventoryPanel, BorderLayout.CENTER);

        return sectionPanel;
    }

    private JPanel createInventoryPanel(User user) {
        JPanel inventoryContainer = new JPanel(new BorderLayout());
        inventoryContainer.setBackground(Color.WHITE);

        try {
            List<Item> userItems = itemDAO.getItemsByUserId(user.getUserId());
            System.out.println("Utente " + user.getUsername() + " ha " + userItems.size() + " items");
            
            if (userItems.isEmpty()) {
                JPanel emptyPanel = new JPanel(new BorderLayout());
                emptyPanel.setBackground(new Color(255, 248, 220));
                emptyPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
                emptyPanel.setPreferredSize(new Dimension(0, 80));
                
                JLabel emptyLabel = new JLabel("Nessun item nell'inventario", SwingConstants.CENTER);
                emptyLabel.setForeground(new Color(184, 134, 11));
                emptyLabel.setFont(new Font("Roboto", Font.ITALIC, 12));
                emptyPanel.add(emptyLabel, BorderLayout.CENTER);
                
                inventoryContainer.add(emptyPanel, BorderLayout.CENTER);
            } else {
                // Calcola il numero di righe necessarie
                int rows = (int) Math.ceil((double) userItems.size() / GRID_COLUMNS);
                
                JPanel itemsGrid = new JPanel(new GridLayout(rows, GRID_COLUMNS, 8, 8));
                itemsGrid.setBackground(Color.WHITE);
                itemsGrid.setBorder(new EmptyBorder(10, 10, 10, 10));

                // Aggiungi tutti gli item
                for (Item item : userItems) {
                    try {
                        JPanel itemPanel = createItemPanel(item, user);
                        itemsGrid.add(itemPanel);
                    } catch (Exception e) {
                        System.err.println("Errore creazione panel per item " + item.getItemId() + ": " + e.getMessage());
                        // Aggiungi un panel di errore invece di bloccare tutto
                        JPanel errorPanel = createErrorItemPanel("Errore item " + item.getItemId());
                        itemsGrid.add(errorPanel);
                    }
                }

                // Riempie gli slot vuoti nell'ultima riga se necessario
                int totalSlots = rows * GRID_COLUMNS;
                for (int i = userItems.size(); i < totalSlots; i++) {
                    itemsGrid.add(createEmptySlotPanel());
                }

                inventoryContainer.add(itemsGrid, BorderLayout.CENTER);
            }
            
        } catch (SQLException e) {
            System.err.println("Errore SQL per utente " + user.getUsername() + ": " + e.getMessage());
            JPanel errorPanel = new JPanel(new BorderLayout());
            errorPanel.setBackground(new Color(248, 215, 218));
            errorPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
            errorPanel.setPreferredSize(new Dimension(0, 80));
            
            JLabel errorLabel = new JLabel("Errore nel caricamento dell'inventario", SwingConstants.CENTER);
            errorLabel.setForeground(new Color(220, 53, 69));
            errorLabel.setFont(new Font("Roboto", Font.BOLD, 12));
            errorPanel.add(errorLabel, BorderLayout.CENTER);
            
            inventoryContainer.add(errorPanel, BorderLayout.CENTER);
        } catch (Exception e) {
            System.err.println("Errore generico per utente " + user.getUsername() + ": " + e.getMessage());
            e.printStackTrace();
            inventoryContainer.add(createErrorItemPanel("Errore caricamento inventario"), BorderLayout.CENTER);
        }

        return inventoryContainer;
    }

    private JPanel createItemPanel(Item item, User owner) {
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

            if (item.getData_acquisizione() != null) {
                String formattedDate = item.getData_acquisizione()
                        .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                addInfoRow(infoPanel, "Acquisito:", formattedDate);
            }

            panel.add(infoPanel, BorderLayout.CENTER);


        } catch (Exception e) {
            System.err.println("Errore creazione item panel: " + e.getMessage());
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

    private JPanel createEmptySlotPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLoweredBevelBorder(),
            new EmptyBorder(5, 5, 5, 5)
        ));
        panel.setBackground(new Color(248, 249, 250));
        panel.setPreferredSize(new Dimension(150, 120));

        return panel;
    }

    private JPanel createErrorItemPanel(String message) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.RED),
            new EmptyBorder(10, 10, 10, 10)
        ));
        panel.setBackground(new Color(248, 215, 218)); 
        panel.setPreferredSize(new Dimension(150, 120));

        JLabel errorLabel = new JLabel("<html><center>" + message + "</center></html>");
        errorLabel.setForeground(new Color(220, 53, 69));
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        errorLabel.setFont(new Font("Roboto", Font.BOLD, 10));
        panel.add(errorLabel, BorderLayout.CENTER);

        return panel;
    }

    private String getMediaTitle(int mediaId) {
        try {
            return mediaDAO.getTitleById(mediaId);
        } catch (SQLException e) {
            System.err.println("Errore recupero titolo media " + mediaId + ": " + e.getMessage());
            return "Titolo non disponibile";
        } catch (Exception e) {
            System.err.println("Errore generico recupero titolo media " + mediaId + ": " + e.getMessage());
            return "Errore titolo";
        }
    }

    private String truncateText(String text, int maxLength) {
        if (text == null) return "";
        return text.length() > maxLength ? text.substring(0, maxLength - 3) + "..." : text;
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

    private void refreshData() {
        loadAllUsersWithInventories();
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Errore", JOptionPane.ERROR_MESSAGE);
    }

    public interface TradeRequestListener {
        void onTradeRequest(User targetUser);
    }

public interface UserActionListener {
        
        void onTradeRequest();
    }
}