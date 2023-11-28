package Client.User.components;

import javax.swing.*;
import java.awt.*;

public class FriendsPanel extends JPanel {
    private JList<String> allFriendsList;
    private JList<String> onlineFriendsList;

    public FriendsPanel() {
        super(new BorderLayout());
        allFriendsList = new JList<>(new String[]{"Nguyen Tuan Kiet", "Friend 2", "Friend 3", "Friend 4"});
        onlineFriendsList = new JList<>(new String[]{"Friend 1", "Friend 3"});

        setBorder(BorderFactory.createTitledBorder("Friends"));
        setPreferredSize(new Dimension(200, this.getPreferredSize().height));

        JTabbedPane friendsTabbedPane = new JTabbedPane();
        friendsTabbedPane.addTab("All", createFriendsListPanel(allFriendsList));
        friendsTabbedPane.addTab("Online", createFriendsListPanel(onlineFriendsList));

        add(friendsTabbedPane, BorderLayout.CENTER);
    }

    private JPanel createFriendsListPanel(JList<String> friendsList) {
        JPanel friendsListPanel = new JPanel(new BorderLayout());
        JScrollPane friendsScrollPane = new JScrollPane(friendsList);
        friendsListPanel.add(friendsScrollPane, BorderLayout.CENTER);
        return friendsListPanel;
    }

}
