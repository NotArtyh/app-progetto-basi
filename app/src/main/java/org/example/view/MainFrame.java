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
        setTitle("👨‍🌾");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(true);
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
}
