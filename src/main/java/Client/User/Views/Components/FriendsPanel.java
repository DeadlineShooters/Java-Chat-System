package Client.User.Views.Components;

import Client.User.CurrentUser;
import Client.User.Repositories.ChatRoomRepo;
import Client.User.Repositories.FriendRepo;
import Client.User.Repositories.UserRepo;
import Client.User.Views.Util;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FriendsPanel extends JPanel {
    private String itemClicked = "";
    JPanel chatRoomsPanel;
    JPanel usersPanel;
    JTabbedPane friendsTabbedPane;
    Color itemBgColor = Color.pink;

    public FriendsPanel() {
        super(new BorderLayout());

        setPreferredSize(new Dimension(230, this.getPreferredSize().height));

        friendsTabbedPane = new JTabbedPane();
        friendsTabbedPane.addTab("Chat", chatRoomsTab());
        friendsTabbedPane.addTab("Explore", createExploreTab());

        add(friendsTabbedPane, BorderLayout.CENTER);
    }
    private JPanel createExploreTab() {
        // search panel
        JPanel searchPanel = new JPanel();
//        searchPanel.setBorder(new LineBorder(Color.black, 1));
        JTextField searchField = new JTextField(13);
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


        // list section
        usersPanel = new JPanel(new GridLayout(0, 1));
        JPanel subPanel = new JPanel(new BorderLayout());
        subPanel.add(usersPanel, BorderLayout.NORTH);

        JScrollPane usersScrollPane = new JScrollPane(subPanel);
        usersScrollPane.getVerticalScrollBar().setUnitIncrement(10);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usersPanel.removeAll();
                usersPanel.revalidate(); // Trigger layout update
//                usersPanel.repaint();

                String prompt = searchField.getText();
                searchField.setText("");
                String currentUsername = CurrentUser.getInstance().getUser().username();
                HashMap<String, Boolean> users = UserRepo.findUsers(currentUsername, prompt);
                for (String username : users.keySet()) {
                    usersPanel.add(createListItem("",username, users.get(username), 80));

                }
                usersPanel.revalidate(); // Trigger layout update
//                usersPanel.repaint();
            }
        });
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usersPanel.removeAll();
                usersPanel.revalidate(); // Trigger layout update
//                usersPanel.repaint();
            }
        });
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(searchPanel, BorderLayout.NORTH);
        panel.add(usersScrollPane, BorderLayout.CENTER);
        return panel;
    }
    private JPanel chatRoomsTab() {
        chatRoomsPanel = new JPanel(new GridLayout(0, 1));

        displayChatrooms();

        JPanel subPanel = new JPanel(new BorderLayout());
        subPanel.add(chatRoomsPanel, BorderLayout.NORTH);

        JScrollPane friendsScrollPane = new JScrollPane(subPanel);
        friendsScrollPane.getVerticalScrollBar().setUnitIncrement(10);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(friendsScrollPane, BorderLayout.CENTER);

        return panel;
    }
    void displayChatrooms() {
        Map<String, String> chatRooms = CurrentUser.getInstance().getChatRooms();
        for (String chatRoomId : chatRooms.keySet()) {
            System.out.println("adding");
            chatRoomsPanel.add(createListItem(chatRoomId,chatRooms.get(chatRoomId), false, 80));
        }
    }

    private JPanel createListItem(String chatRoomId, String name, Boolean status, int height) {
        JPanel item = new JPanel(new BorderLayout());
        item.setPreferredSize(new Dimension(item.getPreferredSize().width, height));
        item.setBackground(itemBgColor);

        JLabel content = new JLabel();
        content.setBorder(new EmptyBorder(0, 10, 0, 10));
        content.setText("   " + name);
        content.setIcon(Util.createRoundedImageIcon("user-avatar.jpg", 60));
        content.setFont(new Font("Arial", Font.BOLD, 13));

        item.setBorder(new LineBorder(Color.black, 1));
        item.add(content, BorderLayout.CENTER);

        if (!CurrentUser.getInstance().isFriend(name) && !ChatRoomRepo.isGroupChat(chatRoomId)) {
            JButton addFriendBtn = new JButton();
            addFriendBtn.setIcon(Util.createImageIcon("addFriendIcon.png", 20, 20));
            addFriendBtn.setPreferredSize(new Dimension(40, 20));
//            addFriendBtn.setContentAreaFilled(false);
//            addFriendBtn.setBorder(null);
            addFriendBtn.setFocusPainted(false);
            addFriendBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!addFriend(name)) {
                        JOptionPane.showMessageDialog(item, "Wrong username or password!","Alert",JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    // Remove the button from its parent container
//                    Container parentContainer = addFriendBtn.getParent();
//                    parentContainer.remove(addFriendBtn);

                    usersPanel.removeAll();
                    usersPanel.revalidate(); // Trigger layout update
                    friendsTabbedPane.setSelectedIndex(0);
                }
            });
            item.add(addFriendBtn, BorderLayout.EAST);
        }

        // Add hover effect and click event
        item.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!Objects.equals(content.getText(), itemClicked)) {
                    item.setBackground(Color.lightGray); // Change the background color on hover
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!Objects.equals(content.getText(), itemClicked)) {
                    item.setBackground(itemBgColor); // Reset the background color when the mouse exits
                }
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                itemClicked = content.getText();
                updateItemColors();
                ChatPanel.getInstance().startChatting(chatRoomId, name);
                // Handle other click actions if needed
            }
        });

        return item;
    }
    boolean addFriend(String username) {
        CurrentUser.getInstance().addFriend(username);
        displayChatrooms();
        return FriendRepo.addFriend(CurrentUser.getInstance().getUser().username(), username);
    }
    private void updateItemColors() {
        for (Component component : chatRoomsPanel.getComponents()) {
            if (component instanceof JPanel) {
                JPanel itemPanel = (JPanel) component;
                JLabel label = (JLabel) itemPanel.getComponent(0);
                if (Objects.equals(label.getText(), itemClicked)) {
                    itemPanel.setBackground(Color.lightGray);
                } else {
                    itemPanel.setBackground(itemBgColor);
                }
            }
        }
    }
}
