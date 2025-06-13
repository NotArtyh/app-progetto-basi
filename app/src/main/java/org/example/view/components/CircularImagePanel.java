package org.example.view.components;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class CircularImagePanel extends JPanel {
    private static final int DIAMETER = 50;
    private static final int BORDER_THICKNESS = 4;

    public CircularImagePanel(String imageName) {
        int totalSize = DIAMETER + 2 * BORDER_THICKNESS;
        setPreferredSize(new Dimension(totalSize, totalSize));
        setOpaque(false);

        try {
            BufferedImage originalImage = ImageIO.read(getClass().getResource("/images/" + imageName));
            BufferedImage scaled = getScaledImage(originalImage, DIAMETER, DIAMETER);
            BufferedImage circularImage = makeCircularMaskedImage(scaled);
            BufferedImage finalWithBorder = drawWithWhiteBorder(circularImage);

            JLabel label = new JLabel(new ImageIcon(finalWithBorder));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setVerticalAlignment(SwingConstants.CENTER);
            add(label);
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Error loading image: " + e.getMessage());
            add(new JLabel("Image not found"));
        }
    }

    private BufferedImage getScaledImage(BufferedImage src, int w, int h) {
        Image tmp = src.getScaledInstance(w, h, Image.SCALE_SMOOTH);
        BufferedImage scaled = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = scaled.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(tmp, 0, 0, null);
        g2.dispose();
        return scaled;
    }

    private BufferedImage makeCircularMaskedImage(BufferedImage image) {
        BufferedImage masked = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = masked.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Ellipse2D circle = new Ellipse2D.Float(0, 0, image.getWidth(), image.getHeight());
        g2.setClip(circle);
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
        return masked;
    }

    private BufferedImage drawWithWhiteBorder(BufferedImage circularImage) {
        int sizeWithBorder = DIAMETER + 2 * BORDER_THICKNESS;
        BufferedImage finalImage = new BufferedImage(sizeWithBorder, sizeWithBorder, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = finalImage.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw white border circle
        g2.setColor(Color.WHITE);
        g2.fillOval(0, 0, sizeWithBorder, sizeWithBorder);

        // Draw the circular image in the center
        g2.drawImage(circularImage, BORDER_THICKNESS, BORDER_THICKNESS, null);
        g2.dispose();
        return finalImage;
    }
}
