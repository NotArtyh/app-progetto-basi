package org.example.view.components;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class Image extends JPanel {
    /**
     * Constructor that generates a penel with a provied image path
     * 
     * @param name name + .extension of images in resources/images folder
     */
    public Image(String path) {
        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setVerticalAlignment(SwingConstants.CENTER);
        ImageIcon originalIcon = new ImageIcon(getClass().getResource(path));
        imageLabel.setIcon(originalIcon);
    }
}
