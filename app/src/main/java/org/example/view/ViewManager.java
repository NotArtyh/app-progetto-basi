package org.example.view;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

/*
 * Manager for different pannels, we baically switch between the given pannels
 * with this class that creates the main frame where everything should be displayed
 * maps a name to a specific pannel - for reusability
 * can retrieve and show the corresponding pannels
 */
public class ViewManager {
    private final MainFrame mainFrame;
    private final Map<String, JPanel> panels = new HashMap<>();

    public ViewManager(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public void registerPanel(String name, JPanel panel) {
        panels.put(name, panel);
        mainFrame.addPanel(name, panel);
    }

    public void show(String name) {
        mainFrame.showPanel(name);
    }

    public JPanel getPanel(String name) {
        return panels.get(name);
    }
}