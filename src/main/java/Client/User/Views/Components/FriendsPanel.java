package Client.User.Views.Components;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.Objects;

class CustomCell extends JLabel implements ListCellRenderer<String> {
    int PROFILE_IMAGE_SIZE = 50;
    @Override
    public Component getListCellRendererComponent(
            JList<? extends String> list, String username, int index,
            boolean isSelected, boolean cellHasFocus) {


        ImageIcon profileImage = createRoundedImageIcon("profile_image.jpg");
        // To create square profile image
//        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("profile_image.jpg")));
//        Image img = icon.getImage().getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH);
//        ImageIcon profileImage = new ImageIcon(img);

        setIcon(profileImage);
        setText(username);

        setOpaque(true);
        setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
        setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());


        Border border = BorderFactory.createLineBorder(Color.BLACK);
        setBorder(BorderFactory.createCompoundBorder(border,
                BorderFactory.createEmptyBorder(0, 10, 0, 0)));


        // Set height for each cell
        setPreferredSize(new Dimension(getPreferredSize().width, 60));

        return this;
    }
    private ImageIcon createRoundedImageIcon(String path) {
        ImageIcon originalIcon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource(path)));
        Image img = originalIcon.getImage();
        BufferedImage roundedImage = new BufferedImage(PROFILE_IMAGE_SIZE, PROFILE_IMAGE_SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = roundedImage.createGraphics();

        // Create a circular clipping shape
        Ellipse2D.Double clip = new Ellipse2D.Double(0, 0, PROFILE_IMAGE_SIZE, PROFILE_IMAGE_SIZE);
        g2d.setClip(clip);

        // Draw the original image onto the clipped area
        g2d.drawImage(img, 0, 0, PROFILE_IMAGE_SIZE, PROFILE_IMAGE_SIZE, null);
        g2d.dispose();

        return new ImageIcon(roundedImage);
    }
}
public class FriendsPanel extends JPanel {
    String[] friendsUsername = new String[]{"Nguyen Tuan Kiet", "Friend 2", "Friend 3", "Friend 4"};
    String[] onlineFriendsUsername = new String[]{"Friend 1", "Friend 3"};

    public FriendsPanel() {
        super(new BorderLayout());

        setPreferredSize(new Dimension(200, this.getPreferredSize().height));

        JTabbedPane friendsTabbedPane = new JTabbedPane();
        friendsTabbedPane.addTab("All", createFriendsListPanel(friendsUsername));
        friendsTabbedPane.addTab("Online", createFriendsListPanel(onlineFriendsUsername));

        add(friendsTabbedPane, BorderLayout.CENTER);
    }

    private JPanel createFriendsListPanel(String[] usernames) {
        JPanel friendsListPanel = new JPanel(new BorderLayout());

        DefaultListModel<String> listModel = new DefaultListModel<>();
        JList<String> friendsList = new JList<>(listModel);

        for (String username : usernames) {
            listModel.addElement(username);
        }

        friendsList.setCellRenderer(new CustomCell());

        JScrollPane friendsScrollPane = new JScrollPane(friendsList);
        friendsListPanel.add(friendsScrollPane, BorderLayout.CENTER);

        return friendsListPanel;
    }
}