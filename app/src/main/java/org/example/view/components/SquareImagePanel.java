package org.example.view.components;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class SquareImagePanel extends JPanel {
    private static final int SIZE = 60; // Size of the square image

    public SquareImagePanel(String imageName) {
        setPreferredSize(new Dimension(SIZE, SIZE));
        // setBackground(Color.WHITE);
        setOpaque(false);
        setLayout(new BorderLayout());

        try {
            BufferedImage originalImage = ImageIO.read(getClass().getResource("/images/" + imageName));
            BufferedImage scaledImage = getScaledImage(originalImage, SIZE, SIZE);

            JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            imageLabel.setVerticalAlignment(SwingConstants.CENTER);
            imageLabel.setOpaque(false);

            add(imageLabel, BorderLayout.CENTER);
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Error loading image: " + e.getMessage());
            add(new JLabel("Image not found"));
        }
    }

    private BufferedImage getScaledImage(BufferedImage src, int width, int height) {
        Image tmp = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage scaled = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = scaled.createGraphics();
        g2.drawImage(tmp, 0, 0, null);
        g2.dispose();
        return scaled;
    }
}
