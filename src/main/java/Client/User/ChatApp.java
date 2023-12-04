package Client.User;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.Objects;

class Friend {
    private String username;
    private ImageIcon profilePicture;
    ImageIcon profileImage = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("profile_image.jpg")));

    public Friend(String username) {
        this.username = username;
        this.profilePicture = profileImage;
    }

    public String getUsername() {
        return username;
    }

    public ImageIcon getProfilePicture() {
        return profilePicture;
    }
}

class FriendCellRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(
            JList<?> list, Object value, int index,
            boolean isSelected, boolean cellHasFocus) {

        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        if (value instanceof Friend) {
            Friend friend = (Friend) value;
            label.setIcon(new ImageIcon(getRoundImage(friend.getProfilePicture().getImage())));
            label.setText(friend.getUsername());
        }

        return label;
    }

    private Image getRoundImage(Image img) {
        BufferedImage roundedImage = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = roundedImage.createGraphics();
        g2.setClip(new Ellipse2D.Float(0, 0, 50, 50));
        g2.drawImage(img, 0, 0, 50, 50, null);
        g2.dispose();
        return roundedImage;
    }
}

public class ChatApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Chat App");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());

            DefaultListModel<Friend> friendListModel = new DefaultListModel<>();
            friendListModel.addElement(new Friend("Friend1"));
            friendListModel.addElement(new Friend("Friend2"));
            // Add more friends as needed

            JList<Friend> friendList = new JList<>(friendListModel);
            friendList.setCellRenderer(new FriendCellRenderer());

            JScrollPane friendListScrollPane = new JScrollPane(friendList);
            frame.add(friendListScrollPane, BorderLayout.WEST);

            frame.setSize(400, 300);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
