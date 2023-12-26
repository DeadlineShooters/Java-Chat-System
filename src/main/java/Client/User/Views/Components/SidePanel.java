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

public class SidePanel extends JPanel {
    private String chatRoomClicked = "";
    JPanel chatRoomsPanel;
    JPanel usersPanel;
    JPanel friendsPanel;
    JTabbedPane friendsTabbedPane;
    Color itemBgColor = Color.pink;
    Color chatRoomClickedColor = Color.lightGray;
    HashMap<String, JPanel> itemsPointer = new HashMap<>(); // <chatRoomId, listItem>
    private static SidePanel sidePanel = null;

    public static SidePanel getInstance() {
        if (sidePanel == null)
            sidePanel = new SidePanel();
        return sidePanel;
    }

    private SidePanel() {
        super(new BorderLayout());

        setPreferredSize(new Dimension(230, this.getPreferredSize().height));

        friendsTabbedPane = new JTabbedPane();
        friendsTabbedPane.addTab("Chat", chatRoomsTab());
        friendsTabbedPane.addTab("Explore", createExploreTab());
        friendsTabbedPane.addTab("Friends", createFriendsTab());

//        handleChatRoomClick();

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
                    System.out.println(username);
                    usersPanel.add(createListItem("",username, users.get(username)));

                }
                usersPanel.revalidate(); // Trigger layout update
                usersPanel.repaint();
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
        chatRoomsPanel.removeAll();
        chatRoomsPanel.revalidate();
        Map<String, String> chatRooms = CurrentUser.getInstance().getChatRooms();
        for (String chatRoomId : chatRooms.keySet()) {
//            System.out.println("adding");

//            System.out.println(chatRooms.get(chatRoomId));
            Boolean status = false;
            if (!ChatRoomRepo.isGroupChat(chatRoomId)) {
                status = UserRepo.isOnline(chatRooms.get(chatRoomId));
//                System.out.println(chatRooms.get(chatRoomId)+" "+status);
            }
            JPanel chatRoom = createListItem(chatRoomId,chatRooms.get(chatRoomId), status);
            chatRoomsPanel.add(chatRoom);
            itemsPointer.put(chatRoomId, chatRoom);
        }
    }
    private JPanel createListItem(String chatRoomId, String name, Boolean status) {
        boolean isChatRoom = !chatRoomId.isEmpty();

        JPanel item = new JPanel(new BorderLayout());
        item.setPreferredSize(new Dimension(item.getPreferredSize().width, 80));
        item.setBackground(itemBgColor);
        item.setBorder(new LineBorder(Color.black, 1));

        JLabel content = new JLabel();
        content.setBorder(new EmptyBorder(0, 10, 0, 10));
        content.setText("   "+name);
        content.setIcon(Util.createRoundedImageIcon("user-avatar.jpg", 60));
        content.setFont(new Font("Arial", Font.BOLD, 13));

        JPanel contentWrapper = new JPanel();
        contentWrapper.setLayout(null);
        contentWrapper.setBackground(null);
        contentWrapper.add(content);
        content.setBounds(0,10, content.getPreferredSize().width, content.getPreferredSize().height);

        item.add(contentWrapper, BorderLayout.CENTER);
        if (isChatRoom)
            item.putClientProperty("chatRoomId", chatRoomId);
        item.putClientProperty("name", name);

        if (!isChatRoom || !ChatRoomRepo.isGroupChat(chatRoomId)) {
            if (!CurrentUser.getInstance().isFriend(name)) {
                JButton addFriendBtn = new JButton();
                addFriendBtn.setIcon(Util.createImageIcon("addFriendIcon.png", 20, 20));
//                addFriendBtn.setPreferredSize(new Dimension(40, 20));
                //            addFriendBtn.setContentAreaFilled(false);
                //            addFriendBtn.setBorder(null);
                addFriendBtn.setFocusPainted(false);
                contentWrapper.add(addFriendBtn);
                addFriendBtn.setBounds(180, 30, 25, 25);
                addFriendBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (!addFriend(name)) {
                            JOptionPane.showMessageDialog(item, "Wrong username or password!", "Alert", JOptionPane.WARNING_MESSAGE);
                            return;
                        }

//                         Remove the button from its parent container
                         Container parentContainer = addFriendBtn.getParent();
                         parentContainer.remove(addFriendBtn);

//                        usersPanel.removeAll();
//                        usersPanel.revalidate(); // Trigger layout update
                        if (friendsTabbedPane.getSelectedIndex() != 0)
                            displayChatrooms();
                        displayFriends();
//                        friendsTabbedPane.setSelectedIndex(0);
                        if (SettingsPanel.getInstance().getComponent(0) != null)
                            SettingsPanel.getInstance().initPrivateChat(chatRoomId, name);
                        JOptionPane.showMessageDialog(ChatPanel.getInstance(), "unfriend user: "+name+" successfully.");
                    }
                });

            }
            if (status) {
                JPanel greenBall = new JPanel();
                greenBall.setBorder(new Util.RoundedBorder(12));
                greenBall.setBackground(Color.green);


                greenBall.setBounds(55, 55, 15, 15);

                contentWrapper.add(greenBall);
                contentWrapper.setComponentZOrder(greenBall, 0);
            }
//                updateIsOnline(chatRoomId.isEmpty()? name: chatRoomId);
        }

        // Add hover effect and click event
        item.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!Objects.equals(chatRoomId, chatRoomClicked) || !Objects.equals(name, chatRoomClicked)) {
                    item.setBackground(Color.lightGray); // Change the background color on hover
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!Objects.equals(chatRoomId, chatRoomClicked) || !Objects.equals(name, chatRoomClicked)) {
                    item.setBackground(itemBgColor); // Reset the background color when the mouse exits
                }
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!isChatRoom) {
                    handleUserClick(item);
                    return;
                }
                handleChatRoomClick(item);
            }
        });

        return item;
    }
    void updateIsOnline(String id) {
//        System.out.println("at SidePanel, updateIsOnline: "+id);
//        System.out.println();
        JPanel item = itemsPointer.get(id);
        System.out.println("at SidePanel, updateIsOnline: "+item.getClientProperty("name"));
        JPanel contentWrapper = (JPanel) item.getComponent(0);
        JPanel greenBall = new JPanel();
        greenBall.setBorder(new Util.RoundedBorder(12));
        greenBall.setBackground(Color.green);


        greenBall.setBounds(55, 55, 15, 15);

        contentWrapper.add(greenBall);
        contentWrapper.setComponentZOrder(greenBall, 0);
        contentWrapper.revalidate();
        contentWrapper.repaint();
//        item.revalidate();
    }
    void updateIsOffline(String id) {
        JPanel item = itemsPointer.get(id);
        System.out.println("at SidePanel, updateIsOffline: "+item.getClientProperty("name"));
        JPanel contentWrapper = (JPanel) item.getComponent(0);
        contentWrapper.remove(contentWrapper.getComponent(0));
        contentWrapper.revalidate();
        contentWrapper.repaint();
    }
    void handleChatRoomClick(JPanel chatRoom) {
        String chatRoomId = (String)chatRoom.getClientProperty("chatRoomId");
        if (!chatRoomClicked.isEmpty())
            itemsPointer.get(chatRoomClicked).getComponent(0).setBackground(itemBgColor);
        chatRoomClicked = chatRoomId;
        chatRoom.getComponent(0).setBackground(chatRoomClickedColor);

        String chatUsername = (String)chatRoom.getClientProperty("name");
        ChatPanel.getInstance().startChatting(chatRoomId, chatUsername);
//        SettingsPanel.getInstance().chatUsername = chatUsername;
    }
    void handleUserClick(JPanel user) {
        String user1 = CurrentUser.getInstance().getUser().username();
        String user2 = (String)user.getClientProperty("name");
        String chatRoomId = ChatRoomRepo.findPrivateChatId(user1, user2);
        if (chatRoomId == null) {
            String newChatRoomId = ChatRoomRepo.createPrivateChat(user1, user2);
//            String newChatRoomId = "test123";

            JPanel chatroom = createListItem(newChatRoomId, user2, false);
            chatRoomsPanel.add(chatroom, 0);
            handleChatRoomClick(chatroom);
        } else {
            handleChatRoomClick(itemsPointer.get(chatRoomId));
        }

        friendsTabbedPane.setSelectedIndex(0);
    }
    boolean addFriend(String username) {
        CurrentUser.getInstance().addFriend(username);
//        displayChatrooms();
        return FriendRepo.addFriend(CurrentUser.getInstance().getUser().username(), username);
    }
    JPanel createFriendsTab() {
        friendsPanel = new JPanel(new GridLayout(0, 1));

        displayFriends();

        JPanel subPanel = new JPanel(new BorderLayout());
        subPanel.add(friendsPanel, BorderLayout.NORTH);

        JScrollPane friendsScrollPane = new JScrollPane(subPanel);
        friendsScrollPane.getVerticalScrollBar().setUnitIncrement(10);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(friendsScrollPane, BorderLayout.CENTER);

        return panel;
    }
    void displayFriends() {
        friendsPanel.removeAll();
        friendsPanel.revalidate();
        Map<String, Boolean> friends = CurrentUser.getInstance().getFriends();
        for (String username : friends.keySet()) {
//            System.out.println(username);
            JPanel item = createListItem("", username, friends.get(username));
            friendsPanel.add(item);
            itemsPointer.put(username, item);
        }
    }

}
