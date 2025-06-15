package org.example.view.components;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;

public class StyledButton extends JButton {
    public StyledButton(String icon, String text) {
        super(icon + " " + text);
        this.setFont(new Font("Roboto", Font.PLAIN, 12));
        this.setPreferredSize(new Dimension(120, 40));
        this.setFocusPainted(false);
    }




}