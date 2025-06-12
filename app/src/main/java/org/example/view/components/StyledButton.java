package org.example.view.components;

import java.awt.*;
import javax.swing.*;

public class StyledButton extends JButton {
    public StyledButton(String icon, String text) {
        super(icon + " " + text);
        this.setFont(new Font("Roboto", Font.PLAIN, 12));
        this.setPreferredSize(new Dimension(120, 40));
        this.setFocusPainted(false);
    }
}
