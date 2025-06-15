package org.example.view.panels;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.example.database.TradeDAO;
import org.example.model.Trade;

/**
 * Pannello per visualizzare e gestire le richieste di scambio
 */
public class TradeRequestsPanel extends JPanel {

    private JTable tradeTable;
    private TradeTableModel tableModel;
    private TradeDAO tradeDAO;
    private int currentUserId;

    private UserActionListener actionListener;

    public void setActionListener(UserActionListener actionListener) {
        this.actionListener = actionListener;
    }

    // Colonne della tabella
    private static final String[] COLUMN_NAMES = {
            "Trade ID", "Recieving user", "Offered items", "Wanted items",
            "Request date", "Status", "Actions"
    };

    public TradeRequestsPanel(int currentUserId) {
        this.currentUserId = currentUserId;
        this.tradeDAO = new TradeDAO();
        initializeComponents();
        setupLayout();
        loadTradeRequests();
    }

    private void initializeComponents() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Trade Requests"));

        // Inizializza il modello della tabella
        tableModel = new TradeTableModel();
        tradeTable = new JTable(tableModel);

        // Configura la tabella
        tradeTable.setRowHeight(40);
        tradeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tradeTable.getTableHeader().setReorderingAllowed(false);

        // Imposta renderer personalizzato per la colonna delle azioni
        tradeTable.getColumn("Actions").setCellRenderer(new ButtonRenderer());
        tradeTable.getColumn("Actions").setCellEditor(new ButtonEditor(new JCheckBox()));

        // Imposta larghezze delle colonne
        tradeTable.getColumnModel().getColumn(0).setPreferredWidth(80); // ID
        tradeTable.getColumnModel().getColumn(1).setPreferredWidth(120); // Utente
        tradeTable.getColumnModel().getColumn(2).setPreferredWidth(150); // Item Offerti
        tradeTable.getColumnModel().getColumn(3).setPreferredWidth(150); // Item Richiesti
        tradeTable.getColumnModel().getColumn(4).setPreferredWidth(120); // Data
        tradeTable.getColumnModel().getColumn(5).setPreferredWidth(100); // Stato
        tradeTable.getColumnModel().getColumn(6).setPreferredWidth(150); // Azioni
    }

    private void setupLayout() {
        // Pannello superiore con titolo e pulsante refresh
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Trade requests you received", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

        topPanel.add(titleLabel, BorderLayout.CENTER);

        // exit button listener
        JButton exitButton = new JButton("Exit");
        exitButton.setBackground(Color.GRAY);
        exitButton.setForeground(Color.BLACK);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (actionListener != null) {
                    actionListener.onExit();
                }
            }
        });

        // Pannello per il pulsante Exit a destra
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(exitButton);
        topPanel.add(buttonPanel, BorderLayout.EAST);

        // Aggiungi componenti al pannello principale
        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(tradeTable), BorderLayout.CENTER);

        // Pannello informativo in basso
        JLabel infoLabel = new JLabel("You can accept or reject trade requests by clicking the buttons in the Actions column.");
        infoLabel.setHorizontalAlignment(JLabel.CENTER);
        infoLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        add(infoLabel, BorderLayout.SOUTH);
    }

    private void loadTradeRequests() {
        try {
            // Qui dovresti implementare il metodo nel TradeDAO per ottenere le richieste
            List<TradeRequestInfo> requests = getTradeRequestsForUser(currentUserId);
            tableModel.setTradeRequests(requests);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error in loading trade requests: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Metodo temporaneo - dovrai implementare la logica nel TradeDAO
    private List<TradeRequestInfo> getTradeRequestsForUser(int userId) {
        List<TradeRequestInfo> requests = new ArrayList<>();

        // Dati di esempio - sostituisci con chiamata al database
        requests.add(new TradeRequestInfo(1, "dariaa34", "In the End", "Billie Jean",
                LocalDateTime.now().minusDays(2), "Pending..."));
        requests.add(new TradeRequestInfo(2, "luca_red", "Take On Me", "Thriller, Wonderwall",
                LocalDateTime.now().minusDays(1), "Accepted"));
        requests.add(new TradeRequestInfo(3, "celinee.d", "Karma Police", "Wonderwall",
                LocalDateTime.now().minusHours(3), "Pending..."));

        return requests;
    }

    private void acceptTradeRequest(int tradeId) {
        int result = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to accept this trade request?",
                "Confirm Accept", JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            try {
                // Implementa la logica per accettare lo scambio
                updateTradeStatus(tradeId, "Accepted");

                // Aggiorna lo stato nella lista locale
                updateLocalTradeStatus(tradeId, "Accepted");

                JOptionPane.showMessageDialog(this,
                        "Trade request accepted successfully.",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Error accepting the trade request: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void rejectTradeRequest(int tradeId) {
        int result = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to reject this trade request?",
                "Confirm Reject", JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            try {
                // Implementa la logica per rifiutare lo scambio
                updateTradeStatus(tradeId, "Rejected");

                // Aggiorna lo stato nella lista locale
                updateLocalTradeStatus(tradeId, "Rejected");

                JOptionPane.showMessageDialog(this,
                        "Trade request rejected successfully.",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                        "Error rejecting the trade request: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Metodo da implementare nel TradeDAO
    private void updateTradeStatus(int tradeId, String newStatus) throws SQLException {
        // Implementa la logica per aggiornare lo stato nel database
        // tradeDAO.updateTradeStatus(tradeId, newStatus);
        System.out.println("Updapdating status for trade " + tradeId + " in: " + newStatus);
    }

    // Metodo per aggiornare lo stato nella lista locale e rinfrescare la tabella
    private void updateLocalTradeStatus(int tradeId, String newStatus) {
        boolean updated = tableModel.updateTradeStatus(tradeId, newStatus);
        if (updated) {
            // Rinfresca la tabella per mostrare le modifiche
            tableModel.fireTableDataChanged();

            // Interrompe eventuali editing in corso
            if (tradeTable.isEditing()) {
                tradeTable.getCellEditor().stopCellEditing();
            }
        }
    }

    // Classe per rappresentare le informazioni di una richiesta di scambio
    private static class TradeRequestInfo {
        private int tradeId;
        private String requesterUsername;
        private String offeredItems;
        private String requestedItems;
        private LocalDateTime requestDate;
        private String status;

        public TradeRequestInfo(int tradeId, String requesterUsername, String offeredItems,
                String requestedItems, LocalDateTime requestDate, String status) {
            this.tradeId = tradeId;
            this.requesterUsername = requesterUsername;
            this.offeredItems = offeredItems;
            this.requestedItems = requestedItems;
            this.requestDate = requestDate;
            this.status = status;
        }

        // Getters
        public int getTradeId() {
            return tradeId;
        }

        public String getRequesterUsername() {
            return requesterUsername;
        }

        public String getOfferedItems() {
            return offeredItems;
        }

        public String getRequestedItems() {
            return requestedItems;
        }

        public LocalDateTime getRequestDate() {
            return requestDate;
        }

        public String getStatus() {
            return status;
        }

        // Setter per aggiornare lo stato
        public void setStatus(String status) {
            this.status = status;
        }
    }

    // Modello della tabella
    private class TradeTableModel extends AbstractTableModel {
        private List<TradeRequestInfo> tradeRequests = new ArrayList<>();

        public void setTradeRequests(List<TradeRequestInfo> requests) {
            this.tradeRequests = requests;
            fireTableDataChanged();
        }

        // Metodo per aggiornare lo stato di una specifica richiesta
        public boolean updateTradeStatus(int tradeId, String newStatus) {
            for (int i = 0; i < tradeRequests.size(); i++) {
                TradeRequestInfo request = tradeRequests.get(i);
                if (request.getTradeId() == tradeId) {
                    request.setStatus(newStatus);
                    // Notifica il cambiamento per quella specifica riga
                    fireTableRowsUpdated(i, i);
                    return true;
                }
            }
            return false;
        }

        @Override
        public int getRowCount() {
            return tradeRequests.size();
        }

        @Override
        public int getColumnCount() {
            return COLUMN_NAMES.length;
        }

        @Override
        public String getColumnName(int column) {
            return COLUMN_NAMES[column];
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            TradeRequestInfo request = tradeRequests.get(rowIndex);

            switch (columnIndex) {
                case 0:
                    return request.getTradeId();
                case 1:
                    return request.getRequesterUsername();
                case 2:
                    return request.getOfferedItems();
                case 3:
                    return request.getRequestedItems();
                case 4:
                    return request.getRequestDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
                case 5:
                    return request.getStatus();
                case 6:
                    return ""; // Colonna azioni gestita dal renderer
                default:
                    return null;
            }
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            // Solo la colonna delle azioni è editabile (per i pulsanti)
            return columnIndex == 6;
        }

        public TradeRequestInfo getRequestAt(int rowIndex) {
            return tradeRequests.get(rowIndex);
        }
    }

    // Renderer per i pulsanti nella colonna azioni
    private class ButtonRenderer implements TableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {

            TradeRequestInfo request = tableModel.getRequestAt(row);
            boolean isPending = "Pending...".equals(request.getStatus());

            if (isPending) {
                // Crea pannello con pulsanti per richieste in attesa
                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));

                JButton acceptButton = new JButton("Accept");
                JButton rejectButton = new JButton("Reject");

                acceptButton.setPreferredSize(new Dimension(70, 25));
                rejectButton.setPreferredSize(new Dimension(70, 25));

                acceptButton.setBackground(new Color(46, 125, 50));
                acceptButton.setForeground(Color.BLACK);
                rejectButton.setBackground(new Color(211, 47, 47));
                rejectButton.setForeground(Color.BLACK);

                buttonPanel.add(acceptButton);
                buttonPanel.add(rejectButton);

                return buttonPanel;
            } else {
                // Crea pannello con solo label dello stato per richieste processate
                JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                JLabel statusLabel = new JLabel(request.getStatus());
                statusLabel.setHorizontalAlignment(JLabel.CENTER);

                if ("Accepted".equals(request.getStatus())) {
                    statusLabel.setForeground(new Color(46, 125, 50));
                    statusLabel.setFont(statusLabel.getFont().deriveFont(Font.BOLD));
                } else if ("Rejejected".equals(request.getStatus())) {
                    statusLabel.setForeground(new Color(211, 47, 47));
                    statusLabel.setFont(statusLabel.getFont().deriveFont(Font.BOLD));
                }

                statusPanel.add(statusLabel);
                return statusPanel;
            }
        }
    }

    // Editor per i pulsanti nella colonna azioni
    private class ButtonEditor extends DefaultCellEditor {
        private JPanel buttonPanel;
        private JButton acceptButton;
        private JButton rejectButton;
        private int currentRow;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);

            buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
            acceptButton = new JButton("Accept");
            rejectButton = new JButton("Reject");

            acceptButton.setPreferredSize(new Dimension(70, 25));
            rejectButton.setPreferredSize(new Dimension(70, 25));

            acceptButton.setBackground(new Color(46, 125, 50));
            acceptButton.setForeground(Color.BLACK);
            rejectButton.setBackground(new Color(211, 47, 47));
            rejectButton.setForeground(Color.BLACK);

            acceptButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    TradeRequestInfo request = tableModel.getRequestAt(currentRow);
                    acceptTradeRequest(request.getTradeId());
                    fireEditingStopped();
                }
            });

            rejectButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    TradeRequestInfo request = tableModel.getRequestAt(currentRow);
                    rejectTradeRequest(request.getTradeId());
                    fireEditingStopped();
                }
            });

            buttonPanel.add(acceptButton);
            buttonPanel.add(rejectButton);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            currentRow = row;

            TradeRequestInfo request = tableModel.getRequestAt(row);
            boolean isPending = "Pending...".equals(request.getStatus());

            if (isPending) {
                return buttonPanel;
            } else {
                // Per stati non in attesa, restituisci un pannello con solo la label
                JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                JLabel statusLabel = new JLabel(request.getStatus());
                statusLabel.setHorizontalAlignment(JLabel.CENTER);

                if ("Accepted".equals(request.getStatus())) {
                    statusLabel.setForeground(new Color(46, 125, 50));
                    statusLabel.setFont(statusLabel.getFont().deriveFont(Font.BOLD));
                } else if ("Rejected".equals(request.getStatus())) {
                    statusLabel.setForeground(new Color(211, 47, 47));
                    statusLabel.setFont(statusLabel.getFont().deriveFont(Font.BOLD));
                }

                statusPanel.add(statusLabel);
                return statusPanel;
            }
        }

        @Override
        public Object getCellEditorValue() {
            return "";
        }
    }

    public interface UserActionListener {
        void onExit();
    }

    /*
     * Metodo per testare il pannello
     * public static void main(String[] args) {
     * SwingUtilities.invokeLater(() -> {
     * JFrame frame = new JFrame("Test Trade Requests Panel");
     * frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     * frame.setSize(900, 400);
     * frame.setLocationRelativeTo(null);
     * 
     * TradeRequestsPanel panel = new TradeRequestsPanel(1); // Test con user ID 1
     * frame.add(panel);
     * 
     * frame.setVisible(true);
     * });
     * }
     */

}