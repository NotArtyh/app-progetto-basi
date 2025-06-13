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
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import org.example.view.components.StyledButton;
import org.example.model.Item;
import org.example.services.ServiceResult;

public class PersonalInventoryPanel extends JPanel {
    private UserActionListener actionListener;

    private static final int GRID_SIZE = 5;
    private static final int ITEMS_PER_PAGE = GRID_SIZE * GRID_SIZE;
    private int currentPage = 0;
    private List<Item> userItems;
    private List<String> userItemsTitles;
    private JPanel gridPanel;
    private JLabel pageLabel;
    private JButton prevButton;
    private JButton nextButton;

    // Constructor used to just initialize the pannel for a later update to happen
    // so that no resource are wasted trying to draw something that will be
    // inevitably redrawn
    public PersonalInventoryPanel() {
    }

    // consturct the pannels once the
    public PersonalInventoryPanel(ServiceResult viewData) {
        initializePanel();
        if (viewData.getViewDataPayload() != null) {
            Map<Item, String> titletItemsMap = (Map<Item, String>) viewData.getViewDataPayload();
            this.userItems = new ArrayList<>(titletItemsMap.keySet());
            this.userItemsTitles = new ArrayList<>(titletItemsMap.values());
        }
        updateGrid();
    }

    public void setActionListener(UserActionListener actionListener) {
        this.actionListener = actionListener;
    }

    private void initializePanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // Titolo del pannello
        JLabel titleLabel = new JLabel("Il Mio Inventario Personale", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Roboto", Font.BOLD, 24));
        titleLabel.setBorder(new EmptyBorder(0, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);

        // Pannello principale per la griglia
        gridPanel = new JPanel(new GridLayout(GRID_SIZE, GRID_SIZE, 10, 10));
        gridPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(gridPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER);

        // Pannello di controllo per la paginazione
        createControlPanel();
    }

    private void createControlPanel() {
        JPanel controlPanel = new JPanel(new BorderLayout());
        controlPanel.setBorder(new EmptyBorder(10, 0, 0, 0));

        // Pannello pulsanti navigazione
        JPanel navigationPanel = new JPanel(new FlowLayout());

        prevButton = new JButton("◀ Precedente");
        prevButton.setFont(new Font("Roboto", Font.BOLD, 12));
        prevButton.addActionListener(e -> previousPage());

        nextButton = new JButton("Successivo ▶");
        nextButton.setFont(new Font("Roboto", Font.BOLD, 12));
        nextButton.addActionListener(e -> nextPage());

        pageLabel = new JLabel("Pagina 1 di 1", SwingConstants.CENTER);
        pageLabel.setFont(new Font("Roboto", Font.PLAIN, 12));

        navigationPanel.add(prevButton);
        navigationPanel.add(Box.createHorizontalStrut(20));
        navigationPanel.add(pageLabel);
        navigationPanel.add(Box.createHorizontalStrut(20));
        navigationPanel.add(nextButton);

        controlPanel.add(navigationPanel, BorderLayout.CENTER);

        // Pulsante refresh
        JButton refreshButton = new StyledButton("🔄", "Update");
        refreshButton.setFont(new Font("Roboto", Font.BOLD, 12));
        refreshButton.addActionListener(e -> refreshInventory());
        controlPanel.add(refreshButton, BorderLayout.EAST);

        // exit Button
        JButton exiButton = new JButton("Exit");
        exiButton.setFont(new Font("Roboto", Font.BOLD, 12));
        exiButton.setForeground(Color.BLACK);
        exiButton.setBackground(new Color(220, 53, 69));
        exiButton.addActionListener(e -> {
            if (actionListener != null)
                actionListener.onExit();
        });
        controlPanel.add(exiButton, BorderLayout.WEST);

        add(controlPanel, BorderLayout.SOUTH);
    }

    private void updateGrid() {
        gridPanel.removeAll();

        if (userItems == null || userItems.isEmpty()) {
            // Mostra un messaggio se non ci sono item
            JPanel emptyPanel = createEmptyItemPanel("Nessun item nell'inventario");
            gridPanel.add(emptyPanel);

            // Riempie il resto della griglia con pannelli vuoti
            for (int i = 1; i < ITEMS_PER_PAGE; i++) {
                gridPanel.add(createEmptySlotPanel());
            }
        } else {
            int startIndex = currentPage * ITEMS_PER_PAGE;
            int endIndex = Math.min(startIndex + ITEMS_PER_PAGE, userItems.size());

            // Aggiungi gli item della pagina corrente
            for (int i = startIndex; i < endIndex; i++) {
                JPanel itemPanel = createItemPanel(userItems.get(i), i);
                gridPanel.add(itemPanel);
            }

            // Riempie gli slot vuoti rimanenti
            for (int i = endIndex - startIndex; i < ITEMS_PER_PAGE; i++) {
                gridPanel.add(createEmptySlotPanel());
            }
        }

        updateNavigationControls();
        gridPanel.revalidate();
        gridPanel.repaint();
    }

    private JPanel createItemPanel(Item item, int titleIndex) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(createItemBorder());
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(200, 150));

        try {
            // Ottieni il titolo del media
            String mediaTitle = userItemsTitles.get(titleIndex);
            // Pannello superiore con titolo
            JPanel headerPanel = new JPanel(new BorderLayout());
            headerPanel.setBackground(new Color(240, 248, 255));
            JLabel titleLabel = new JLabel("<html><b>" + truncateText(mediaTitle, 20) + "</b></html>");
            titleLabel.setFont(new Font("Roboto", Font.BOLD, 11));
            titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
            headerPanel.add(titleLabel, BorderLayout.CENTER);

            // ID dell'item
            JLabel idLabel = new JLabel("ID: " + item.getItemId());
            idLabel.setFont(new Font("Roboto", Font.PLAIN, 9));
            idLabel.setForeground(Color.GRAY);
            headerPanel.add(idLabel, BorderLayout.SOUTH);

            panel.add(headerPanel, BorderLayout.NORTH);

            // Pannello centrale con informazioni
            JPanel infoPanel = new JPanel();
            infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
            infoPanel.setBackground(Color.WHITE);
            infoPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

            // Condizioni
            addInfoRow(infoPanel, "Condizioni:", truncateText(item.getCondizioni(), 15));

            // Note (se presenti)
            if (item.getNote() != null && !item.getNote().trim().isEmpty()) {
                addInfoRow(infoPanel, "Note:", truncateText(item.getNote(), 15));
            }

            // Data acquisizione
            String formattedDate = item.getData_acquisizione()
                    .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            addInfoRow(infoPanel, "Acquisito:", formattedDate);

            panel.add(infoPanel, BorderLayout.CENTER);

            // Tooltip con informazioni complete
            panel.setToolTipText(createTooltipText(item, mediaTitle));

        } catch (Exception e) {
            // In caso di errore, mostra un pannello con errore
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
        labelComp.setFont(new Font("Roboto", Font.BOLD, 10));
        labelComp.setForeground(new Color(70, 70, 70));

        JLabel valueComp = new JLabel(value);
        valueComp.setFont(new Font("Roboto", Font.PLAIN, 10));

        row.add(labelComp, BorderLayout.WEST);
        row.add(valueComp, BorderLayout.CENTER);

        parent.add(row);
        parent.add(Box.createVerticalStrut(2));
    }

    private JPanel createEmptySlotPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(createEmptyBorder());
        panel.setBackground(new Color(250, 250, 250));
        panel.setPreferredSize(new Dimension(200, 150));

        JLabel emptyLabel = new JLabel("Slot vuoto");
        emptyLabel.setForeground(Color.LIGHT_GRAY);
        emptyLabel.setHorizontalAlignment(SwingConstants.CENTER);
        emptyLabel.setFont(new Font("Roboto", Font.ITALIC, 12));

        panel.add(emptyLabel, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createEmptyItemPanel(String message) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(createItemBorder());
        panel.setBackground(new Color(255, 248, 220));
        panel.setPreferredSize(new Dimension(200, 150));

        JLabel messageLabel = new JLabel("<html><center>" + message + "</center></html>");
        messageLabel.setForeground(new Color(184, 134, 11));
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel.setFont(new Font("Roboto", Font.BOLD, 12));

        panel.add(messageLabel, BorderLayout.CENTER);
        return panel;
    }

    private Border createItemBorder() {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createRaisedBevelBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    private Border createEmptyBorder() {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createLoweredBevelBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    // private String getMediaTitle(Item item) {
    // // Assumo che ci sia un metodo per ottenere il titolo dal MediaDAO
    // return mediaDAO.getTitleById(mediaId);
    // }

    private String truncateText(String text, int maxLength) {
        if (text == null)
            return "";
        return text.length() > maxLength ? text.substring(0, maxLength - 3) + "..." : text;
    }

    private String createTooltipText(Item item, String mediaTitle) {
        return "<html>" +
                "<b>Titolo:</b> " + mediaTitle + "<br>" +
                "<b>Item ID:</b> " + item.getItemId() + "<br>" +
                "<b>Media ID:</b> " + item.getMediaId() + "<br>" +
                "<b>Condizioni:</b> " + (item.getCondizioni() != null ? item.getCondizioni() : "N/A") + "<br>" +
                "<b>Note:</b> "
                + (item.getNote() != null && !item.getNote().isEmpty() ? item.getNote() : "Nessuna nota") + "<br>" +
                "<b>Data Acquisizione:</b> "
                + item.getData_acquisizione().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) +
                "</html>";
    }

    private void updateNavigationControls() {
        int totalPages = userItems != null ? (int) Math.ceil((double) userItems.size() / ITEMS_PER_PAGE) : 1;
        totalPages = Math.max(1, totalPages); // Almeno 1 pagina

        pageLabel.setText("Pagina " + (currentPage + 1) + " di " + totalPages);
        prevButton.setEnabled(currentPage > 0);
        nextButton.setEnabled(userItems != null && (currentPage + 1) * ITEMS_PER_PAGE < userItems.size());
    }

    private void previousPage() {
        if (currentPage > 0) {
            currentPage--;
            updateGrid();
        }
    }

    private void nextPage() {
        if (userItems != null && (currentPage + 1) * ITEMS_PER_PAGE < userItems.size()) {
            currentPage++;
            updateGrid();
        }
    }

    private void refreshInventory() {
        currentPage = 0;
        updateGrid();
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Errore", JOptionPane.ERROR_MESSAGE);
    }

    // Metodo pubblico per aggiornare l'inventario dall'esterno
    public void refreshData() {
        refreshInventory();
    }

    public interface UserActionListener {
        void onExit();
    }
}