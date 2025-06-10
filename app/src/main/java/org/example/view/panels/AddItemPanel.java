package org.example.view.panels;

import javax.swing.*;
import java.awt.*;

public class AddItemPanel {
        private UserActionListener actionListener;

    public AddItemPanel() {
        createAddItemPanel();
    }

    public void setActionListener(UserActionListener actionListener) {
        this.actionListener = actionListener;
    }

    private void createAddItemPanel() {
        // Implement the bar that contains the user info like name logout option user
        // icon etc
    }

    public interface UserActionListener {
        // Implement the needed action listeners
    }
}
