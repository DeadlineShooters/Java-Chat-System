package Client.User.Views.Components;

import Client.Models.ChatRoom;
import Client.Models.User;
import Client.User.Views.Util;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class FriendsPanel extends JPanel {
    String[] friendsUsername = new String[]{"Nguyen Tuan Kiet", "Friend 2", "Friend 3", "Friend 4"};
    User user1 = new User("a@a", "kiet", "123");
    User user2 = new User("a@a", "tu", "123");
//    Friend friend1 = new Friend(user1.username(), user2.username());
    ChatRoom chatRoom1 = new ChatRoom("");
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
        friendsList.setCellRenderer(new CustomCell());

        for (String username : usernames) {
            listModel.addElement(username);
        }


        JScrollPane friendsScrollPane = new JScrollPane(friendsList);
        friendsListPanel.add(friendsScrollPane, BorderLayout.CENTER);

        return friendsListPanel;
    }
}
class CustomCell extends JLabel implements ListCellRenderer<String> {
    @Override
    public Component getListCellRendererComponent(
            JList<? extends String> list, String username, int index,
            boolean isSelected, boolean cellHasFocus) {


        ImageIcon profileImage = Util.createRoundedImageIcon("user-avatar.jpg", 40);
        // To create square profile image
//        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("user-avatar.jpg")));
//        Image img = icon.getImage().getScaledInstance(40, 40,  java.awt.Image.SCALE_SMOOTH);
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

}