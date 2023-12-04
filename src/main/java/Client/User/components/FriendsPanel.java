package Client.User.components;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Objects;

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
        
        friendsList.setCellRenderer(new ProfileListCellRenderer());

        JScrollPane friendsScrollPane = new JScrollPane(friendsList);
        friendsListPanel.add(friendsScrollPane, BorderLayout.CENTER);

        return friendsListPanel;
    }

    private class ProfileListCellRenderer extends JPanel implements ListCellRenderer<String> {
        private static final int PROFILE_IMAGE_SIZE = 50; // Set a fixed size for the profile pictures
        private static final int CELL_HEIGHT = 80; // Set the preferred height for each cell

        @Override
        public Component getListCellRendererComponent(JList<? extends String> list, String value, int index, boolean isSelected, boolean cellHasFocus) {
            setLayout(new BorderLayout());

            JPanel profilePicture = createProfileImage("profile_image.jpg"); // Replace with the actual image path
            JLabel usernameLabel = new JLabel(value);

            // Specify LEFT alignment for better readability
            add(profilePicture, BorderLayout.WEST);
            add(usernameLabel, BorderLayout.CENTER);

            setOpaque(true);
            setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
            setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());

            // Set preferred height for each cell
            setPreferredSize(new Dimension(getPreferredSize().width, CELL_HEIGHT));

            return this;
        }

        private JPanel createProfileImage(String path) {
            ImageIcon profileImage = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource(path)));
            JPanel panel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);

                    Graphics2D g2d = (Graphics2D) g.create();
                    int width = getWidth();
                    int height = getHeight();

                    Ellipse2D ellipse = new Ellipse2D.Double(0, 0, PROFILE_IMAGE_SIZE, PROFILE_IMAGE_SIZE);
                    g2d.setClip(ellipse);
                    g2d.drawImage(profileImage.getImage(), 0, 0, PROFILE_IMAGE_SIZE, PROFILE_IMAGE_SIZE, null);

                    g2d.dispose();
                }

                // Set preferred size for the profile picture
                @Override
                public Dimension getPreferredSize() {
                    return new Dimension(PROFILE_IMAGE_SIZE, PROFILE_IMAGE_SIZE);
                }
            };
            return panel;
        }
    }



//    private JPanel createProfileImage(String path) {
//        ImageIcon profileImage = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource(path)));
//        JPanel panel = new JPanel() {
//            @Override
//            protected void paintComponent(Graphics g) {
//                super.paintComponent(g);
//
//                Graphics2D g2d = (Graphics2D) g.create();
////                int width = getWidth();
////                int height = getHeight();
//                int width = 50, height = 50;
//
//                Ellipse2D ellipse = new Ellipse2D.Double(0, 0, width, height);
//                g2d.setClip(ellipse);
//                g2d.drawImage(profileImage.getImage(), 0, 0, width, height, null);
//
//                g2d.dispose();
//            }
//        };
//        return panel;
//    }
}
