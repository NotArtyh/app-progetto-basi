package org.example.view;

import org.example.view.components.StatusBar;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private StatusBar statusBar;
    // private HeaderPanel headerPanel;

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

        // Status bar at bottom - Will be deprecated soon
        statusBar = new StatusBar();
        add(statusBar, BorderLayout.SOUTH);
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

    /*
     * Show a status message - Will be deprecated soon
     */
    public void showStatusMessage(String message) {
        statusBar.setMessage(message);
    }

    /*
     * Show an error message
     */
    public void showErrorMessage(String message) {
        statusBar.setErrorMessage(message);
    }
}
