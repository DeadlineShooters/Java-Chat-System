package Client.User;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Objects;

public class RoundProfilePicture extends JFrame {
    public RoundProfilePicture() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setLocationRelativeTo(null);

        ImageIcon profileImage = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("profile_image.jpg")));  // Replace with the path to your image

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2d = (Graphics2D) g.create();
                int width = getWidth();
                int height = getHeight();

                Ellipse2D ellipse = new Ellipse2D.Double(0, 0, width, height);
                g2d.setClip(ellipse);
                g2d.drawImage(profileImage.getImage(), 0, 0, width, height, null);

                g2d.dispose();
            }
        };

        add(panel);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RoundProfilePicture());
    }
}
