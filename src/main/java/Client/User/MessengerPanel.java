package Client.User;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class MessengerPanel extends JFrame {

    public MessengerPanel() {
        setTitle("Messenger Panel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(300, 500));

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Create a list of friends
        String[] friends = {"Friend 1", "Friend 2", "Friend 3", "Friend 4"};

        // Create a JList with a custom cell renderer
        JList<String> friendsList = new JList<>(friends);
        friendsList.setCellRenderer(new CustomCellRenderer());

        JScrollPane scrollPane = new JScrollPane(friendsList);

        // Add a refresh button
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> {
            // Handle refresh action
            // You can update the friend list here
        });

        // Add a search field
        JTextField searchField = new JTextField(10);

        // Create a panel for search and refresh components
        JPanel searchPanel = new JPanel();
        searchPanel.add(searchField);
        searchPanel.add(refreshButton);

        // Add components to the main panel
        mainPanel.add(searchPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Set the main panel as the content pane
        setContentPane(mainPanel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Custom cell renderer for friend list
    private static class CustomCellRenderer extends JPanel implements ListCellRenderer<String> {
        private final JLabel nameLabel;
        private final JLabel avatarLabel;

        public CustomCellRenderer() {
            setLayout(new BorderLayout());

            nameLabel = new JLabel();
            avatarLabel = new JLabel();
            avatarLabel.setPreferredSize(new Dimension(40, 40)); // Set avatar size

            add(avatarLabel, BorderLayout.WEST);
            add(nameLabel, BorderLayout.CENTER);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends String> list, String value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            nameLabel.setText(value);
            nameLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0)); // Adjust padding

            // Set rounded avatar image
            BufferedImage roundedImage = createRoundedImage("user-avatar.jpg", 40);
            avatarLabel.setIcon(new ImageIcon(roundedImage));

            setOpaque(true);
            setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
            setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());

            return this;
        }

        private BufferedImage createRoundedImage(String path, int size) {
            try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path)) {
                if (inputStream != null) {
                    BufferedImage originalImage = ImageIO.read(inputStream);
                    BufferedImage roundedImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
                    Graphics2D g2d = roundedImage.createGraphics();

                    // Create a circular clipping shape
                    Ellipse2D.Double clip = new Ellipse2D.Double(0, 0, size, size);
                    g2d.setClip(clip);

                    // Draw the original image onto the clipped area
                    g2d.drawImage(originalImage, 0, 0, size, size, null);
                    g2d.dispose();

                    return roundedImage;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MessengerPanel());
    }
}
