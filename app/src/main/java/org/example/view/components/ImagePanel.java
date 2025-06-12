package org.example.view.components;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ImagePanel extends JPanel {
    /**
     * Constructor that generates a penel with a provied image path
     * 
     * @param name name + .extension of images in resources/images folder
     */
    public ImagePanel(String path) {
        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setVerticalAlignment(SwingConstants.CENTER);
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/images/" + path));

        double scaleFactor = 0.45;
        int scaledWidth = (int) (originalIcon.getIconWidth() * scaleFactor);
        int scaledHeight = (int) (originalIcon.getIconHeight() * scaleFactor);
        Image scaledImage = originalIcon.getImage().getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(scaledImage));
        add(imageLabel);
    }
}
