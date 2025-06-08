package org.example.view.components;

import javax.swing.*;
import java.awt.*;

public class StatusBar extends JPanel {
    private JLabel messageLabel;

    public StatusBar() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLoweredBevelBorder());

        messageLabel = new JLabel(" Ready");
        messageLabel.setFont(new Font("Roboto", Font.PLAIN, 12));
        add(messageLabel, BorderLayout.WEST);
    }

    public void setMessage(String message) {
        messageLabel.setText(" " + message);
        messageLabel.setForeground(Color.BLACK);
    }

    public void setErrorMessage(String message) {
        messageLabel.setText(" ERROR: " + message);
        messageLabel.setForeground(Color.RED);
    }
}
