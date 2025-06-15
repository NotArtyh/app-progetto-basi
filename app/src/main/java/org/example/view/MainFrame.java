package org.example.view;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;

    public MainFrame() {
        initializeFrame();
        setupLayout();
    }

    private void initializeFrame() {
        setTitle("💿");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1024, 800);
        setLocationRelativeTo(null);
        setResizable(true);

        // Set icon image
        ImageIcon icon = new ImageIcon(getClass().getResource("/images/icon.png"));
        setIconImage(icon.getImage());
    }

    private void setupLayout() {
        // Layout setup
        setLayout(new BorderLayout());

        // Content to be displayed
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        add(cardPanel, BorderLayout.CENTER);
    }

    /*
     * Add a named panel to the main view
     */
    public void addPanel(String name, JPanel panel) {
        cardPanel.add(panel, name);
    }

    /*
     * Show a named panel
     */
    public void showPanel(String name) {
        cardLayout.show(cardPanel, name);
    }

    // Remove a panel by name
    public void removePanel(String name) {
        Component[] components = cardPanel.getComponents();
        for (Component comp : components) {
            if (comp.getName() != null && comp.getName().equals(name)) {
                cardPanel.remove(comp);
                break;
            }
        }
        cardPanel.revalidate();
        cardPanel.repaint();
    }

    // Replace a panel with the same name
    public void replacePanel(String name, JPanel newPanel) {
        removePanel(name);
        newPanel.setName(name);
        addPanel(name, newPanel);
        cardPanel.revalidate();
        cardPanel.repaint();
    }
}
