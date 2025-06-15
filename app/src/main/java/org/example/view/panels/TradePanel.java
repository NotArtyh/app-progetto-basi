package org.example.view.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import javax.swing.border.EmptyBorder;

import org.example.model.Item;
import org.example.model.User;
import org.example.services.ServiceResult;

public class TradePanel extends JPanel {
    private UserActionListener actionListener;

    private List<Item> currentUserItems;
    private List<String> currentUserItemsTitles;

    private User targetUser;
    private List<Item> targetUserItems;
    private List<String> targetUserItemsTitles;

    private List<Item> offeredItems;
    private List<Item> wantedItems;

    private JButton tradeButton;

    public TradePanel() {
    }

    public TradePanel(ServiceResult currentUserData, ServiceResult targetUserDataResult, User targetUser) {
        this.targetUser = targetUser;
        this.offeredItems = new ArrayList<>();
        this.wantedItems = new ArrayList<>();

        handleCurrentUserData(currentUserData);
        handletargetUserDataResult(targetUserDataResult);
        initializePanel();
    }

    private void handleCurrentUserData(ServiceResult currentUserData) {
        if (currentUserData.getViewDataPayload() != null) {
            Map<Item, String> titletItemsMap = (Map<Item, String>) currentUserData.getViewDataPayload();
            this.currentUserItems = new ArrayList<>(titletItemsMap.keySet());
            this.currentUserItemsTitles = new ArrayList<>(titletItemsMap.values());
        }
    }

    private void handletargetUserDataResult(ServiceResult targetUserDataResult) {
        if (targetUserDataResult.getViewDataPayload() != null) {
            Map<Item, String> titletItemsMap = (Map<Item, String>) targetUserDataResult.getViewDataPayload();
            this.targetUserItems = new ArrayList<>(titletItemsMap.keySet());
            this.targetUserItemsTitles = new ArrayList<>(titletItemsMap.values());
        }
    }

    private void initializePanel() {
        JPanel contentPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        contentPanel.add(createItemPanel("Your Items", currentUserItems, currentUserItemsTitles, true));
        contentPanel.add(createItemPanel(targetUser.getUsername() + " Items", targetUserItems,
                targetUserItemsTitles, false));

        add(contentPanel, BorderLayout.CENTER);
        add(createControlPanel(), BorderLayout.SOUTH);
    }

    private JPanel createItemPanel(String title, List<Item> items, List<String> titles, boolean isCurrentUser) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(title));

        JPanel itemGrid = new JPanel();
        itemGrid.setLayout(new BoxLayout(itemGrid, BoxLayout.Y_AXIS));

        if (items == null || items.isEmpty()) {
            JLabel emptyLabel = new JLabel("No items to display");
            emptyLabel.setHorizontalAlignment(SwingConstants.CENTER);
            itemGrid.add(emptyLabel);
        } else {
            for (int i = 0; i < items.size(); i++) {
                Item item = items.get(i);
                String customTitle = titles.get(i);
                JPanel card = createItemCard(item, customTitle, isCurrentUser);
                itemGrid.add(card);
                itemGrid.add(Box.createVerticalStrut(8));
            }
        }

        JScrollPane scrollPane = new JScrollPane(itemGrid);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createItemCard(Item item, String title, boolean isCurrentUser) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                new EmptyBorder(8, 8, 8, 8)));
        card.setBackground(Color.WHITE);
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel titleLabel = new JLabel(title);
        JLabel conditionLabel = new JLabel("Condition: " + item.getCondizioni());
        JLabel notesLabel = new JLabel("Notes: " + item.getNote());
        JLabel dateLabel = new JLabel("Acquired: " + item.getData_acquisizione());

        card.add(titleLabel);
        card.add(conditionLabel);
        card.add(notesLabel);
        card.add(dateLabel);

        card.addMouseListener(new MouseAdapter() {
            boolean selected = false;

            @Override
            public void mouseClicked(MouseEvent e) {
                selected = !selected;
                card.setBackground(selected ? new Color(200, 230, 255) : Color.WHITE);

                if (isCurrentUser) {
                    if (selected)
                        offeredItems.add(item);
                    else
                        offeredItems.remove(item);
                } else {
                    if (selected)
                        wantedItems.add(item);
                    else
                        wantedItems.remove(item);
                }

                updateTradeButtonState();
            }
        });

        return card;
    }

    private JPanel createControlPanel() {
        JPanel controlPanel = new JPanel(new BorderLayout());
        controlPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JButton exitButton = new JButton("Exit");
        exitButton.setForeground(Color.BLACK);
        exitButton.setBackground(new Color(220, 53, 69));
        exitButton.setFocusPainted(false);
        exitButton.addActionListener(e -> {
            if (actionListener != null)
                actionListener.onExit();
        });

        tradeButton = new JButton("Trade");
        tradeButton.setEnabled(false);
        tradeButton.setBackground(new Color(40, 167, 69));
        tradeButton.setForeground(Color.BLACK);
        tradeButton.setFocusPainted(false);
        tradeButton.addActionListener(e -> {
        if (actionListener != null) {
        actionListener.onTrade(offeredItems, wantedItems);
        
        // Mostra messaggio di conferma
        JOptionPane.showMessageDialog(
            TradePanel.this,
            "Trade request sent successfully!",
            "Confirmation",
            JOptionPane.INFORMATION_MESSAGE
        );

        // Torna alla home
        actionListener.onExit();
    }
});

        controlPanel.add(exitButton, BorderLayout.WEST);
        controlPanel.add(tradeButton, BorderLayout.EAST);
        return controlPanel;
    }

    private void updateTradeButtonState() {
        tradeButton.setEnabled(!offeredItems.isEmpty());
    }

    public void setActionListener(UserActionListener actionListener) {
        this.actionListener = actionListener;
    }

    public interface UserActionListener {
        void onTrade(List<Item> offeredItems, List<Item> wantedItems);

        void onExit();
    }

}
