package Client.User.Views;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class Util {
    public static ImageIcon createRoundedImageIcon(String path, int size) {
        ImageIcon originalIcon = new ImageIcon(Objects.requireNonNull(Util.class.getClassLoader().getResource(path)));
        Image img = originalIcon.getImage();
        BufferedImage roundedImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = roundedImage.createGraphics();

        // Create a circular clipping shape
        Ellipse2D.Double clip = new Ellipse2D.Double(0, 0, size, size);
        g2d.setClip(clip);

        // Draw the original image onto the clipped area
        g2d.drawImage(img, 0, 0, size, size, null);
        g2d.dispose();

        return new ImageIcon(roundedImage);
    }
    public static ImageIcon createImageIcon(String path, int w, int h) {
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(Util.class.getClassLoader().getResource(path)));
        Image img = icon.getImage().getScaledInstance(w, h,  java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }
}
