package Client.User.Views.Components;

import Client.User.Common;
import Client.User.CurrentUser;
import Client.User.Repositories.*;
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
import java.util.HashSet;

public class SettingsPanel extends JPanel {
    private static SettingsPanel settingsPanel;
    final String[] buttonTexts = {"Unfriend", "Block", "Report spam", "Delete history", "Create group with this person"};
    final String[] groupButtonTexts = {"Edit group name", "Add new members"};
    String chatUsername = null;
    String username = CurrentUser.getInstance().getUser().username();
    String currentChatRoomId = null;
    String spliter = Common.spliter;
    Color itemBgColor = Color.pink;
    JTextField groupNameField;

//    Color userClickedColor = Color.lightGray;
//    String userClicked = "";
    HashSet<String> usersChosen = new HashSet<>();
    JPanel usersPanel, chosenUsersPanel;
    JPanel membersPanel, adminsPanel;


    HashSet<String> membersChosen = new HashSet<>();
    public static SettingsPanel getInstance() {
        if (settingsPanel == null)
            settingsPanel = new SettingsPanel();
        return settingsPanel;
    }
    private SettingsPanel() {
        super(new BorderLayout(0, 15));
        setPreferredSize(new Dimension(300, this.getPreferredSize().height));


    }
    void initGroupChat(String chatRoomId) {
        this.removeAll();
        this.currentChatRoomId = chatRoomId;
        displayGroupContent();
    }
    void displayGroupContent() {
        this.removeAll();
        JButton searchButton = new JButton();
        ImageIcon searchIcon = Util.createImageIcon("searchIcon.png", 15, 15);
        searchButton.setIcon(searchIcon);

        // Create a panel to hold the buttons
        JPanel topPanel = new JPanel();
        topPanel.add(searchButton);
        // topPanel.add(addUserButton);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));



        for (String buttonText : groupButtonTexts) {
            HoverablePanel textPanel = new HoverablePanel(buttonText);
            textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.X_AXIS));

            JLabel label = new JLabel(buttonText);
            label.setFont(new Font(label.getFont().getName(), label.getFont().getStyle(), 13));
            label.setBorder(new EmptyBorder(0, 10, 0, 0));

            textPanel.add(label);
            textPanel.add(Box.createHorizontalGlue()); // This will push the label to the left

            bottomPanel.add(textPanel);
            bottomPanel.add(Box.createVerticalStrut(5)); // set vertical gap between buttons

            // Set the preferred height of the button based on the text height
            int preferredHeight = getFontMetrics(label.getFont()).getHeight() + 18; // gets text's height + 10 is for padding
            textPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, preferredHeight));
            textPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, preferredHeight));
        }

        JPanel optionsPanel = new JPanel(new BorderLayout());
        optionsPanel.add(topPanel, BorderLayout.NORTH);
        optionsPanel.add(bottomPanel, BorderLayout.CENTER);

        JTabbedPane tabsPanel = new JTabbedPane();
        tabsPanel.addTab("Options", optionsPanel);
        tabsPanel.addTab("Members", chatMembersTab());
        tabsPanel.addTab("Admins", adminsTab());

        // Add the button panel to the settings panel
//        this.add(topPanel, BorderLayout.NORTH);
//        this.add(bottomPanel, BorderLayout.CENTER);
        this.add(tabsPanel , BorderLayout.CENTER);
        this.revalidate();
    }
    JPanel adminsTab() {
        adminsPanel = new JPanel(new GridLayout(0, 1));
        JPanel subPanel = new JPanel(new BorderLayout());
        subPanel.add(adminsPanel, BorderLayout.NORTH);

        JScrollPane usersScrollPane = new JScrollPane(subPanel);
        usersScrollPane.getVerticalScrollBar().setUnitIncrement(12);


        displayAdmins();

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(usersScrollPane);
        return panel;
    }
    void displayAdmins() {
        adminsPanel.removeAll();
        adminsPanel.revalidate();
        HashSet<String> members = ChatMemberRepo.getAdmins(currentChatRoomId);
        for (String member: members) {
            JPanel item = createListItem(member,false, "member");
            adminsPanel.add(item);
        }
    }
    JPanel chatMembersTab() {

        membersPanel = new JPanel(new GridLayout(0, 1));
        JPanel subPanel = new JPanel(new BorderLayout());
        subPanel.add(membersPanel, BorderLayout.NORTH);

        JScrollPane usersScrollPane = new JScrollPane(subPanel);
        usersScrollPane.getVerticalScrollBar().setUnitIncrement(12);


        displayMembers();

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(usersScrollPane);
        return panel;
    }
    void displayMembers() {
        membersPanel.removeAll();
        membersPanel.revalidate();
        HashSet<String> members = ChatMemberRepo.getChatMembers(currentChatRoomId);
        for (String member: members) {
            JPanel item = createListItem(member,false, "member");
            membersPanel.add(item);
        }
    }
    void initPrivateChat(String chatRoomId, String chatUsername) {
        this.removeAll();
        this.chatUsername = chatUsername;
        this.currentChatRoomId = chatRoomId;
        displayPrivateContent();
    }
    void displayPrivateContent() {
        this.removeAll();

        JButton searchButton = new JButton();
        ImageIcon searchIcon = Util.createImageIcon("searchIcon.png", 15, 15);
        searchButton.setIcon(searchIcon);

        // Create a panel to hold the buttons
        JPanel topPanel = new JPanel();
        topPanel.add(searchButton);
        // topPanel.add(addUserButton);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));



        for (String buttonText : buttonTexts) {
            if (buttonText.equals("Unfriend") && !FriendRepo.isFriend(username, chatUsername)) {
                continue;
            }
            if (buttonText.equals("Block")) {
                if (BlockedUserRepo.isBlocked(username, chatUsername))
                    buttonText = "Unblock";
                else if (BlockedUserRepo.isBlocked(chatUsername, username))
                    continue;
            }
            HoverablePanel textPanel = new HoverablePanel(buttonText);
            textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.X_AXIS));

            JLabel label = new JLabel(buttonText);
            label.setFont(new Font(label.getFont().getName(), label.getFont().getStyle(), 13));
            label.setBorder(new EmptyBorder(0, 10, 0, 0));

            textPanel.add(label);
            textPanel.add(Box.createHorizontalGlue()); // This will push the label to the left

            bottomPanel.add(textPanel);
            bottomPanel.add(Box.createVerticalStrut(5)); // set vertical gap between buttons

            // Set the preferred height of the button based on the text height
            int preferredHeight = getFontMetrics(label.getFont()).getHeight() + 18; // gets text's height + 10 is for padding
            textPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, preferredHeight));
            textPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, preferredHeight));
        }

        JPanel optionsPanel = new JPanel(new BorderLayout());
        optionsPanel.add(topPanel, BorderLayout.NORTH);
        optionsPanel.add(bottomPanel, BorderLayout.CENTER);

        JTabbedPane optionsTab = new JTabbedPane();
        optionsTab.addTab("Options", optionsPanel);

        // Add the button panel to the settings panel
//        this.add(topPanel, BorderLayout.NORTH);
//        this.add(bottomPanel, BorderLayout.CENTER);
        this.add(optionsTab, BorderLayout.CENTER);
        this.revalidate();
    }
//    final String[] buttonTexts = {"Unfriend", "Block", "Report spam", "Delete history", "Create group with this person"};
    public void performAction(String buttonText) {
        if (buttonText.equals(buttonTexts[0])) {
            String confirmMsg = "Are you sure you want to unfriend user: " + chatUsername;
            if (!showConfirmation(confirmMsg))
                return;
            unfriend();
        } else if (buttonText.equals(buttonTexts[1])) {
            String confirmMsg = "Are you sure you want to block user: "+ chatUsername;
            if (!showConfirmation(confirmMsg))
                return;
            block();
        } else if (buttonText.equals("Unblock")) {
            String confirmMsg = "Do you want to unblock user: "+ chatUsername;
            if (!showConfirmation(confirmMsg))
                return;
            unblock();
        } else if (buttonText.equals(buttonTexts[2])) {

        } else if (buttonText.equals(buttonTexts[3])) {
            String confirmMsg = "Are you sure you want to delete chat history?";
            if (!showConfirmation(confirmMsg))
                return;
            deleteHistory();
        } else if (buttonText.equals(buttonTexts[4])) {
            createGroupWithPerson();
        } else if (buttonText.equals("Add new members")) {
            addMember();
        } else if (buttonText.equals("Edit group name")) {
            editGroupName();
        }
    }
    void editGroupName() {
        String name = JOptionPane.showInputDialog((JFrame) SwingUtilities.getWindowAncestor(this),
                "New group name: ",
                "Edit group name",
                JOptionPane.QUESTION_MESSAGE);
        if (name == null || name.isEmpty())
            return;
        ChatRoomRepo.updateGroupChatName(currentChatRoomId, name);
        ChatPanel.getInstance().displayTopPanel(name);
        SidePanel.getInstance().displayChatrooms();
    }
    private boolean showConfirmation(String confirmMsg) {
        int result = JOptionPane.showConfirmDialog(
                ChatPanel.getInstance(),
                confirmMsg,
                "Confirmation",
                JOptionPane.YES_NO_OPTION
        );

        return result == JOptionPane.YES_OPTION;
    }
    private void createGroupWithPerson() {
        usersChosen.clear();

        JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Create group chat", true);
        dialog.setLayout(new BorderLayout());
        dialog.setResizable(false);
        dialog.setSize(415, 500);

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

        // input group chat name
        JPanel groupNamePanel = new JPanel();
//        groupNamePanel.setLayout(new BoxLayout(groupNamePanel, BoxLayout.X_AXIS));
        groupNameField = new JTextField(20);
//        groupNameField.setPreferredSize(new Dimension(200, 30));
        JLabel groupNameLabel = new JLabel("Group chat name (optional): ");
        groupNamePanel.add(groupNameLabel);
        groupNamePanel.add(groupNameField);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.add(groupNamePanel);
        topPanel.add(searchPanel);

        dialog.add(topPanel, BorderLayout.NORTH);


        // list section
        usersPanel = new JPanel(new GridLayout(0, 1));
        JPanel subPanel = new JPanel(new BorderLayout());
        subPanel.add(usersPanel, BorderLayout.NORTH);

        JScrollPane usersScrollPane = new JScrollPane(subPanel);
        usersScrollPane.getVerticalScrollBar().setUnitIncrement(10);

        chosenUsersPanel = new JPanel(new GridLayout(0, 1));
        JPanel chosenSubPanel = new JPanel(new BorderLayout());
        chosenSubPanel.add(chosenUsersPanel, BorderLayout.NORTH);

        JScrollPane chosenUsersScrollPane = new JScrollPane(chosenSubPanel);
        chosenUsersScrollPane.getVerticalScrollBar().setUnitIncrement(10);

        JPanel outerleft = new JPanel(new BorderLayout());
        JPanel outerright = new JPanel(new BorderLayout());
        outerleft.add(usersScrollPane, BorderLayout.CENTER);
        outerright.add(chosenUsersScrollPane, BorderLayout.CENTER);

        JLabel pick = new JLabel("Pick at least 1 user");
        JLabel chosen = new JLabel("You picked");
        JPanel pickWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel chosenWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pickWrapper.add(pick);
        chosenWrapper.add(chosen);

        outerleft.add(pickWrapper, BorderLayout.NORTH);
        outerright.add(chosenWrapper, BorderLayout.NORTH);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usersPanel.removeAll();
                usersPanel.revalidate(); // Trigger layout update

                String prompt = searchField.getText();
                if (prompt.isEmpty())
                    return;
                searchField.setText("");
                String currentUsername = CurrentUser.getInstance().getUser().username();
                HashMap<String, Boolean> users = UserRepo.findUsers(currentUsername, prompt);
                for (String username : users.keySet()) {
                    JPanel item = createListItem(username, users.get(username), "");
                    usersPanel.add(item);
                }
                usersPanel.revalidate(); // Trigger layout update
            }
        });
        // display all users
        String currentUsername = CurrentUser.getInstance().getUser().username();
        HashMap<String, Boolean> users = UserRepo.findUsers(currentUsername, "");
        for (String username : users.keySet()) {
            JPanel item = createListItem(username, users.get(username), "");
            usersPanel.add(item);
        }
        // pick current chat partner as group chat member
        JPanel chatPartner = createListItem(chatUsername, UserRepo.isOnline(chatUsername), "chosen");
        chosenUsersPanel.add(chatPartner);
        usersChosen.add(chatUsername);
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usersPanel.removeAll();
                usersPanel.revalidate(); // Trigger layout update

//                usersPanel.repaint();
                String currentUsername = CurrentUser.getInstance().getUser().username();
                HashMap<String, Boolean> users = UserRepo.findUsers(currentUsername, "");
                for (String username : users.keySet()) {
                    JPanel item = createListItem(username, users.get(username), "");
                    usersPanel.add(item);
                }
                usersPanel.revalidate(); // Trigger layout update
            }
        });



        outerleft.setBounds(0,0, 200, 500);
        outerright.setBounds(200,0, 200, 500);
//        outerleft.setBackground(Color.blue);
//        outerright.setBackground(Color.red);

        JPanel combine = new JPanel();
        combine.setLayout(null);
        combine.setBackground(Color.yellow);

        combine.add(outerleft);
        combine.add(outerright);

        dialog.add(combine, BorderLayout.CENTER);

        JButton create = new JButton("Create a group chat");
        dialog.add(create, BorderLayout.SOUTH);

        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (handleCreateGroupChat()) {
                    dialog.dispose();
                    return;
                }
                JOptionPane.showMessageDialog(dialog, "Pick at least 1 user");
            }
        });

//        dialog.pack();
        dialog.setLocationRelativeTo(ChatPanel.getInstance()); // Center the dialog on the parent frame
        dialog.setVisible(true);
    }
    Boolean handleCreateGroupChat() {
        if (usersChosen.isEmpty()) {

            return false;
        }
        String groupName = groupNameField.getText();
        String groupChatId = ChatRoomRepo.createGroupChat(groupName, username, usersChosen);
        SidePanel.getInstance().displayChatrooms();
        JPanel groupChatRoom = SidePanel.getInstance().itemsPointer.get(groupChatId);
        SidePanel.getInstance().handleChatRoomClick(groupChatRoom);
        return true;
    }
    private JPanel createListItem(String username, Boolean status, String mode) {
        JPanel item = new JPanel(new BorderLayout());
        item.setPreferredSize(new Dimension(item.getPreferredSize().width, 80));
        item.setBackground(itemBgColor);
        item.setBorder(new LineBorder(Color.black, 1));

        JLabel content = new JLabel();
        content.setBorder(new EmptyBorder(0, 10, 0, 10));
        content.setText("   "+username);
        content.setIcon(Util.createRoundedImageIcon("user-avatar.jpg", 60));
        content.setFont(new Font("Arial", Font.BOLD, 13));

        JPanel contentWrapper = new JPanel();
        contentWrapper.setLayout(null);
        contentWrapper.setBackground(null);
        contentWrapper.add(content);
        content.setBounds(0,10, content.getPreferredSize().width, content.getPreferredSize().height);

        item.add(contentWrapper, BorderLayout.CENTER);
        item.putClientProperty("username", username);

        if (status) {
            JPanel greenBall = new JPanel();
            greenBall.setBorder(new Util.RoundedBorder(12));
            greenBall.setBackground(Color.green);


            greenBall.setBounds(55, 55, 15, 15);

            contentWrapper.add(greenBall);
            contentWrapper.setComponentZOrder(greenBall, 0);
        }
//                updateIsOnline(chatRoomId.isEmpty()? name: chatRoomId);

        // Add hover effect and click event
        item.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
//                System.out.println(chatRoomId + "   " +chatRoomClicked);
                contentWrapper.setBackground(Color.lightGray); // Change the background color on hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                contentWrapper.setBackground(itemBgColor); // Reset the background color when the mouse exits
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                if (mode.equals("member")) {
                    return;
                }
                if (mode.equals("chosen")) {
                    handleChosenClick(username, status, item);
                    return;
                }
                handleUserClick(username, status);
            }
        });

        return item;
    }
    void handleUserClick(String username, Boolean status) {
        if (usersChosen.contains(username))
            return;
        usersChosen.add(username);
        chosenUsersPanel.add(createListItem(username, status, "chosen"));
        chosenUsersPanel.revalidate();
    }
    void handleChosenClick(String username, Boolean status, JPanel item) {
        usersChosen.remove(username);
        item.getParent().remove(item);
        chosenUsersPanel.revalidate();

    }
    private void deleteHistory() {
        MessageRepo.deleteAllPrivateMessages(currentChatRoomId);
        JOptionPane.showMessageDialog(ChatPanel.getInstance(), "Chat history deleted.");
        ChatPanel.getInstance().loadMessages();
    }
    void unfriend() {
        CurrentUser.getInstance().removeFriend(chatUsername);
        FriendRepo.unfriend(username, chatUsername);
        SidePanel.getInstance().displayChatrooms();
        SidePanel.getInstance().displayFriends();
        displayPrivateContent();
        JOptionPane.showMessageDialog(ChatPanel.getInstance(), "unfriend user: "+chatUsername+" successfully.");
    }
    void block() {
        BlockedUserRepo.blockUser(username, chatUsername);
        ChatPanel.getInstance().initView();
        displayPrivateContent();
//        System.out.println("at SettingsPanel");
        CurrentUser.getInstance().sendMessage("block"+spliter+currentChatRoomId);
    }
    void unblock() {
        System.out.println("at SettingsPanel unblock");
        BlockedUserRepo.unblockUser(username, chatUsername);
        ChatPanel.getInstance().initView();
        displayPrivateContent();
        CurrentUser.getInstance().sendMessage("unblock"+spliter+currentChatRoomId);
    }
    void reportSpam() {

    }
    void addMember() {
        JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Add new members", true);
        dialog.setLayout(new BorderLayout());
        dialog.setResizable(false);
        dialog.setSize(415, 500);

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

        dialog.add(searchPanel, BorderLayout.NORTH);


        // list section
        usersPanel = new JPanel(new GridLayout(0, 1));
        JPanel subPanel = new JPanel(new BorderLayout());
        subPanel.add(usersPanel, BorderLayout.NORTH);

        JScrollPane usersScrollPane = new JScrollPane(subPanel);
        usersScrollPane.getVerticalScrollBar().setUnitIncrement(10);

        chosenUsersPanel = new JPanel(new GridLayout(0, 1));
        JPanel chosenSubPanel = new JPanel(new BorderLayout());
        chosenSubPanel.add(chosenUsersPanel, BorderLayout.NORTH);

        JScrollPane chosenUsersScrollPane = new JScrollPane(chosenSubPanel);
        chosenUsersScrollPane.getVerticalScrollBar().setUnitIncrement(10);

        JPanel outerleft = new JPanel(new BorderLayout());
        JPanel outerright = new JPanel(new BorderLayout());
        outerleft.add(usersScrollPane, BorderLayout.CENTER);
        outerright.add(chosenUsersScrollPane, BorderLayout.CENTER);

        JLabel pick = new JLabel("Pick at least 1 user");
        JLabel chosen = new JLabel("You picked");
        JPanel pickWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel chosenWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pickWrapper.add(pick);
        chosenWrapper.add(chosen);

        outerleft.add(pickWrapper, BorderLayout.NORTH);
        outerright.add(chosenWrapper, BorderLayout.NORTH);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usersPanel.removeAll();
                usersPanel.revalidate(); // Trigger layout update

                String prompt = searchField.getText();
                if (prompt.isEmpty())
                    return;
                searchField.setText("");
                String currentUsername = CurrentUser.getInstance().getUser().username();
                HashMap<String, Boolean> users = UserRepo.findUsers(currentUsername, prompt);
                for (String username : users.keySet()) {
                    if (ChatMemberRepo.isChatMember(currentChatRoomId, username))
                        continue;
                    JPanel item = createListItem(username, users.get(username), "");
                    usersPanel.add(item);
                }
                usersPanel.revalidate(); // Trigger layout update
            }
        });
        // display all users
        String currentUsername = CurrentUser.getInstance().getUser().username();
        HashMap<String, Boolean> users = UserRepo.findUsers(currentUsername, "");
        for (String username : users.keySet()) {
            if (ChatMemberRepo.isChatMember(currentChatRoomId, username))
                continue;
            JPanel item = createListItem(username, users.get(username), "");
            usersPanel.add(item);
        }
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usersPanel.removeAll();
                usersPanel.revalidate(); // Trigger layout update

//                usersPanel.repaint();
                String currentUsername = CurrentUser.getInstance().getUser().username();
                HashMap<String, Boolean> users = UserRepo.findUsers(currentUsername, "");
                for (String username : users.keySet()) {
                    JPanel item = createListItem(username, users.get(username), "");
                    usersPanel.add(item);
                }
                usersPanel.revalidate(); // Trigger layout update
            }
        });

        outerleft.setBounds(0,0, 200, 500);
        outerright.setBounds(200,0, 200, 500);
//        outerleft.setBackground(Color.blue);
//        outerright.setBackground(Color.red);

        JPanel combine = new JPanel();
        combine.setLayout(null);
        combine.setBackground(Color.yellow);

        combine.add(outerleft);
        combine.add(outerright);

        dialog.add(combine, BorderLayout.CENTER);

        JButton create = new JButton("Add members");
        dialog.add(create, BorderLayout.SOUTH);

        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (handleAddMembers()) {
                    dialog.dispose();
                    return;
                }
                JOptionPane.showMessageDialog(dialog, "Pick at least 1 user");
            }
        });

//        dialog.pack();
        dialog.setLocationRelativeTo(ChatPanel.getInstance()); // Center the dialog on the parent frame
        dialog.setVisible(true);
    }
    Boolean handleAddMembers() {
        if (usersChosen.isEmpty()) {
            return false;
        }
        for (String username : usersChosen) {
            ChatMemberRepo.addChatMember(currentChatRoomId, username);
        }
        displayMembers();
        return true;
    }
    private class HoverablePanel extends JPanel {
        private boolean hovered = false;

        public HoverablePanel(String buttonText) {
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    hovered = true;
                    repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    hovered = false;
                    repaint();
                }
                @Override
                public void mouseClicked(MouseEvent e) {
                    performAction(buttonText);
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (hovered) {
                g.setColor(new Color(173, 216, 230)); // Light blue color
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        }


    }
}
