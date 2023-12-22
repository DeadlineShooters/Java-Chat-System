package Client.User.Views;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;
import java.util.UUID;

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
    public static String createUUID() {
        return UUID.randomUUID().toString();
    }
    public static Timestamp getCurrentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static class RoundedBorder extends AbstractBorder {

        private int cornerRadius;

        public RoundedBorder(int cornerRadius) {
            this.cornerRadius = cornerRadius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g.create();

            RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(x, y, width - 1, height - 1, cornerRadius, cornerRadius);

            g2d.setColor(Color.BLACK); // You can customize the border color here
            g2d.draw(roundedRectangle);

            g2d.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(cornerRadius, cornerRadius, cornerRadius, cornerRadius);
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.left = insets.top = insets.right = insets.bottom = cornerRadius;
            return insets;
        }
    }
    public static Timestamp stringToTimestamp(String timestampString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        java.util.Date parsedDate = null;
        try {
            parsedDate = dateFormat.parse(timestampString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return new Timestamp(parsedDate.getTime());
    }

    public static void main(String[] args) {
        Timestamp t=  new Timestamp(System.currentTimeMillis());
        String sentAt = ""+t;
        Timestamp b = stringToTimestamp(sentAt);
        System.out.println(t);
        System.out.println(b);
    }
}
