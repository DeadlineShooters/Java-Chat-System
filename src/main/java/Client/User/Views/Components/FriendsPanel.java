package Client.User.Views.Components;

import Client.User.Repositories.UserRepo;
import Client.User.Views.Util;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FriendsPanel extends JPanel {
    String[] friendsUsername = new String[]{"Nguyen Tuan Kiet", "Friend 2", "Friend 3", "Friend 4"};
//    User user1 = new User("a@a", "kiet", "123");
//    User user2 = new User("a@a", "tu", "123");
//    Friend friend1 = new Friend(user1.username(), user2.username());
//    ChatRoom chatRoom1 = new ChatRoom("");
    String[] explore = new String[]{"Friend 1", "Friend 3"};

    public FriendsPanel() {
        super(new BorderLayout());

        setPreferredSize(new Dimension(230, this.getPreferredSize().height));

        JTabbedPane friendsTabbedPane = new JTabbedPane();
        friendsTabbedPane.addTab("Friends", createFriendsListPanel(friendsUsername));
        friendsTabbedPane.addTab("Explore", createExplorePanel());

        add(friendsTabbedPane, BorderLayout.CENTER);
    }
    private JPanel createExplorePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel searchPanel = new JPanel();
        JTextField searchField = new JTextField(13);

        // search panel
        JButton searchButton = new JButton();
        ImageIcon searchIcon = Util.createImageIcon("searchIcon.png", 15, 15);
        searchButton.setIcon(searchIcon);
        searchButton.setPreferredSize(new Dimension(20, searchButton.getPreferredSize().height));

        JButton refreshButton = new JButton();
        ImageIcon refreshIcon = Util.createImageIcon("refreshIcon.png", 15, 15);
        refreshButton.setIcon(refreshIcon);
        refreshButton.setPreferredSize(new Dimension(20, searchButton.getPreferredSize().height));

        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(refreshButton);
        panel.add(searchPanel, BorderLayout.NORTH);

        // list section

        DefaultListModel<String> listModel = new DefaultListModel<>();
        JList<String> usersList = new JList<>(listModel);
        usersList.setCellRenderer(new CustomCell());

//        for (String username : friendsUsername) {
//            listModel.addElement(username);
//        }
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String prompt = searchField.getText();
                searchField.setText("");
                ArrayList<String> usernames = UserRepo.findUsers(prompt);
                for (String username : usernames) {
                    listModel.addElement(username);
                }
            }
        });
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listModel.clear();
            }
        });


        JScrollPane friendsScrollPane = new JScrollPane(usersList);
        panel.add(friendsScrollPane, BorderLayout.CENTER);



        return panel;
    }

}
