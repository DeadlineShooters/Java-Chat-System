package Client.User.Views.Components;

import Client.Models.Message;
import Client.User.CurrentUser;
import Client.User.Repositories.BlockedUserRepo;
import Client.User.Repositories.ChatRoomRepo;
import Client.User.Repositories.MessageRepo;
import Client.User.Views.Util;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ChatPanel extends JPanel {
    private JTextArea inputArea;
    private JButton sendButton;
    private static ChatPanel chatPanel;
    JScrollPane chatScrollPane;
    JPanel overFlowPane = null, topPanel = null;
    static int row = 0;
    String chatRoomId = null;
    String name = null;
    String myUsername = CurrentUser.getInstance().getUser().username();
    String spliter = "<21127089>";

    public static ChatPanel getInstance() {
        if (chatPanel == null) {
            chatPanel = new ChatPanel();
        }
        return chatPanel;
    }
     private ChatPanel() {
         super(new BorderLayout());
         setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
    }
    void displayTopPanel(String name) {
        topPanel.removeAll();
        topPanel.revalidate();
//         topPanel.setSize(this.getPreferredSize().width, 600);
        JLabel topBar = new JLabel();
        topBar.setText("   " + name);
        topBar.setFont(new Font("Arial", Font.BOLD, 14));

        ImageIcon profileImage = Util.createRoundedImageIcon("user-avatar.jpg", 50);
        topBar.setIcon(profileImage);
        topPanel.add(topBar);
        topPanel.setBackground(Color.lightGray);
        topPanel.revalidate();
        topPanel.repaint();

    }
    void initView() {
        this.removeAll();

//        topPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        displayTopPanel(name);
        this.add(topPanel, BorderLayout.NORTH);

        overFlowPane = new JPanel();
//         overFlowPane.setLayout(new GridLayout(0, 1));
        overFlowPane.setLayout(new GridBagLayout());

//         overFlowPane.setBackground(Color.pink);

        JPanel subPanel = new JPanel(new BorderLayout());
        subPanel.add(overFlowPane, BorderLayout.NORTH);

        chatScrollPane = new JScrollPane(subPanel);
        chatScrollPane.setBorder(new EmptyBorder(0,0,0,0));
        chatScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        chatScrollPane.getVerticalScrollBar().setUnitIncrement(15);
        this.add(chatScrollPane, BorderLayout.CENTER);


        JLabel blockedNotify = new JLabel();
        blockedNotify.setFont(new Font("Arial", Font.ITALIC, 13));
        blockedNotify.setForeground(Color.gray);

        JPanel inputPanel = new JPanel(new BorderLayout());

        // Create an empty JPanel with FlowLayout to act as a placeholder for centering
        JPanel centeringPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centeringPanel.setBorder(new EmptyBorder(0,0,20,0));
        centeringPanel.add(blockedNotify);

        inputPanel.add(centeringPanel, BorderLayout.CENTER);

        this.add(inputPanel, BorderLayout.SOUTH);
        loadMessages();
        if (BlockedUserRepo.isBlocked(name, myUsername)) {
            blockedNotify.setText("You have been blocked by this user ");
            return;
        } else if (BlockedUserRepo.isBlocked(myUsername, name)) {
//            System.out.println("at ChatPanel");
            blockedNotify.setText("You blocked this user ");
            return;
        }
        inputArea = new JTextArea();
        sendButton = new JButton("Send");
        inputArea.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        inputPanel.add(inputArea, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        inputPanel.setBackground(Color.white);
        inputPanel.setBorder(new EmptyBorder(5,5,5,5));
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
        inputArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sendMessage();
                    e.consume(); // Consume the Enter key event to prevent adding a new line
                }
            }
        });
        this.add(inputPanel, BorderLayout.SOUTH);
        this.revalidate();
    }
    public void startChatting(String chatRoomId, String name) {
        this.chatRoomId = chatRoomId;
        this.name = name;
//        System.out.println("at ChatPanel, startChatting: "+chatRoomId);

        CurrentUser.getInstance().sendMessage("joinRoom"+spliter+myUsername+spliter+""+spliter+""+spliter+chatRoomId);
        initView();

        if (ChatRoomRepo.isGroupChat(chatRoomId)) {
            SettingsPanel.getInstance().initGroupChat(chatRoomId, name);

        } else {
//            System.out.println("afdas");
            SettingsPanel.getInstance().initPrivateChat(chatRoomId, name);
        }
//        System.out.println(chatRoomId);
        loadMessages();

    }
    void loadMessages() {
        if (chatRoomId == null)
            return;
        overFlowPane.removeAll();
        ArrayList<Message> messages = MessageRepo.getAllMessages(chatRoomId);
        for (Message msg : messages) {
            addMsg(msg);
        }
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JScrollBar verticalScrollBar = chatScrollPane.getVerticalScrollBar();
                verticalScrollBar.setValue(verticalScrollBar.getMaximum());
                chatScrollPane.revalidate();
//                chatScrollPane.repaint();
            }
        });
//        chatScrollPane.repaint();
    }
    public void receiveMessage(String msgReceived) {
//        String text = inputArea.getText();
//        System.out.println("ayy: "+msgReceived);

        System.out.println("at ChatPanel "+msgReceived);
        String[] msgSplit = msgReceived.split(spliter);
        if (msgSplit[0].equals("online")) {
            String username = msgSplit[1];
            String chatRoomId = msgSplit[2];
            System.out.println("at ChatPanel: "+username+" is online");
//            chatRoomPoint
//            CurrentUser.getInstance().updateFriendStatus(username, true);
//            System.out.println(username+ ":: " + CurrentUser.getInstance().getFriends().get(username));
//            SidePanel.getInstance().displayChatrooms();
            String privateChatId = ChatRoomRepo.findPrivateChatId(CurrentUser.getInstance().getUser().username(), username);
            if (privateChatId != null)
                SidePanel.getInstance().updateIsOnline(privateChatId);
//            SidePanel.getInstance().displayFriends();
//            SidePanel.getInstance().updateIsOnline(username);
            return;
        }
        if (msgSplit[0].equals("offline")) {
            String username = msgSplit[1];
            String chatRoomId = msgSplit[2];
            System.out.println(username+" is offline");
//            SidePanel.getInstance().displayChatrooms();
//            SidePanel.getInstance().displayFriends();
            String privateChatId = ChatRoomRepo.findPrivateChatId(CurrentUser.getInstance().getUser().username(), username);
            if (privateChatId != null)
                SidePanel.getInstance().updateIsOffline(privateChatId);
//            SidePanel.getInstance().displayFriends();
//            SidePanel.getInstance().updateIsOffline(username);
            return;
        }
        if (msgSplit[0].equals("block") || msgSplit[0].equals("unblock")) {
            if (chatRoomId == null)
                return;
            System.out.println("at ChatPanel block: " + msgSplit[0]);
            initView();
            SettingsPanel.getInstance().displayPrivateContent();
            return;
        }
        if (msgSplit[0].equals("addFriend")) {
            System.out.println("at ChatPanel add friend: " + msgSplit[1]);
            CurrentUser.getInstance().addFriend(msgSplit[1]);
            SidePanel.getInstance().displayChatrooms();
            SidePanel.getInstance().displayFriends();
            return;
        }
        if (msgSplit[0].equals("unfriend")) {
            System.out.println("at ChatPanel unfriend: " + msgSplit[1]);
            CurrentUser.getInstance().removeFriend(msgSplit[1]);
            SidePanel.getInstance().displayChatrooms();
            SidePanel.getInstance().displayFriends();
            return;
        }
        if (msgSplit[0].equals("editGroupName")) {
            // editGroupName - chatRoomId - newGroupName
            SidePanel.getInstance().displayChatrooms();
            if (chatRoomId != null && chatRoomId.equals(msgSplit[1])) {
                System.out.println("at ChatPanel, editgroupname");
                displayTopPanel(msgSplit[2]);
                SettingsPanel.getInstance().initGroupChat(chatRoomId, msgSplit[2]);
            }
            return;

        }
        if (msgSplit[0].equals("createGroup") || msgSplit[0].equals("addMember")) {
            SidePanel.getInstance().displayChatrooms();
            return;
        }
        if (msgSplit[0].equals("updateMemberList")) {
            SettingsPanel.getInstance().displayMembers();
            return;
        }
        if (msgSplit[0].equals("removedFromGroup")) {
            // removedFromGroup - chatRoomId
            SidePanel.getInstance().displayChatrooms();
            System.out.println("ayyyy " + chatRoomId + "    "+msgSplit[1]);
            if (msgSplit[1].equals(chatRoomId)) {
                System.out.println("at ChatPanel, removedfromgroup");
                this.removeAll();
                this.revalidate();
                this.repaint();
                SettingsPanel.getInstance().removeAll();
                SettingsPanel.getInstance().revalidate();
                SettingsPanel.getInstance().repaint();
                chatRoomId = null;
                SidePanel.getInstance().chatRoomClicked = "";
            }
            return;
        }
        if (msgSplit[0].equals("assignAdmin")) {
            SettingsPanel.getInstance().displayAdmins();
            return;
        }

        Message message = new Message(chatRoomId, msgSplit[1], msgSplit[2], "", 0, Util.stringToTimestamp(msgSplit[3]));

        addMsg(message);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JScrollBar verticalScrollBar = chatScrollPane.getVerticalScrollBar();
                verticalScrollBar.setValue(verticalScrollBar.getMaximum());
                chatScrollPane.revalidate();
            }
        });
    }
    public void sendMessage() {
        if (chatRoomId.isEmpty()) return;
        String content = inputArea.getText();
        if (content.isEmpty()) {
            return;
        }

        Timestamp sentAt = Util.getCurrentTimestamp();
        Message message = new Message(chatRoomId, myUsername, content, "", chatScrollPane.getVerticalScrollBar().getValue(), sentAt);
        addMsg(message);
        inputArea.setText("");
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JScrollBar verticalScrollBar = chatScrollPane.getVerticalScrollBar();
                verticalScrollBar.setValue(verticalScrollBar.getMaximum());
                chatScrollPane.revalidate();
//                chatScrollPane.repaint();
            }
        });
        MessageRepo.saveMessage(message);
        if (ChatRoomRepo.isGroupChat(chatRoomId))
            CurrentUser.getInstance().userSession.groupsChattedCount++;
        else
            CurrentUser.getInstance().userSession.usersChattedCount++;

//        System.out.println(chatScrollPane.getVerticalScrollBar().getValue());

        CurrentUser.getInstance().sendMessage("message"+spliter+myUsername+spliter+content+spliter+sentAt+spliter+chatRoomId);
    }
    public void addMsg(Message message) {
        JTextArea chatBox = new JTextArea();
//        chatBox.setColumns(30);

        boolean isMyMessage = message.username().equals(myUsername);
        boolean isLong = message.content().length() > 40;
//        if (isMyMessage)
        if (isLong) {
            chatBox.setLineWrap(true);  // Enable text wrapping
            chatBox.setWrapStyleWord(true);  // Wrap at word boundaries
        }

        chatBox.setText(message.content());

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.add(chatBox, BorderLayout.CENTER);
        wrapper.setBorder(new Util.RoundedBorder(10));
        wrapper.setBackground(Color.white);


        JPanel hold = new JPanel();

        hold.setBorder(new EmptyBorder(isMyMessage?10:0, 10, 0,10));
        hold.setLayout(new BorderLayout());
//        hold.setBackground(Color.gray);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String sentAt = sdf.format(message.sentAt());
        JLabel timeStamp = new JLabel(sentAt);
        timeStamp.setForeground(Color.gray);
        timeStamp.setFont(new Font("Arial", Font.ITALIC, 10));

        JPanel outer = new JPanel();
        outer.setLayout(new BoxLayout(outer, BoxLayout.Y_AXIS));

        if (!isMyMessage) {
            JPanel usernameWrapper = new JPanel();
            if (isMyMessage) {
                usernameWrapper.setLayout(new FlowLayout(FlowLayout.RIGHT));
                usernameWrapper.setBorder(new EmptyBorder(0, 0, 0, 10));
            } else {
                usernameWrapper.setLayout(new FlowLayout(FlowLayout.LEFT));
                usernameWrapper.setBorder(new EmptyBorder(0, 57, 0, 0));
            }
            JLabel username = new JLabel(message.username());
            usernameWrapper.add(username);
            outer.add(usernameWrapper);
        }


        JPanel timeWrapper = new JPanel();
        if (isMyMessage) {
            timeWrapper.setLayout(new FlowLayout(FlowLayout.RIGHT));
            timeWrapper.setBorder(new EmptyBorder(0, 0, 0,10));
        } else {
            timeWrapper.setLayout(new FlowLayout(FlowLayout.LEFT));
            timeWrapper.setBorder(new EmptyBorder(0, 57, 0,0));
        }
        timeWrapper.add(timeStamp);



        outer.add(hold);
        outer.add(timeWrapper);
//        outer.setBackground(Color.red);

        JLabel ava = new JLabel();
        ava.setIcon(Util.createRoundedImageIcon("user-avatar.jpg", 40));
        ava.setBorder(new EmptyBorder(0, 0, 0,10));
        if (!isMyMessage) {
            if (isLong) {
                hold.add(ava, BorderLayout.WEST);
                hold.add(wrapper, BorderLayout.CENTER);
            } else {
                JPanel help = new JPanel(new BorderLayout());
                help.add(ava, BorderLayout.WEST);
                help.add(wrapper, BorderLayout.CENTER);
                hold.add(help, BorderLayout.WEST);
            }
        } else {
            if (isLong) {
                hold.add(wrapper, BorderLayout.CENTER);
            } else {
                hold.add(wrapper, BorderLayout.EAST);

            }
        }


        GridBagConstraints c = new GridBagConstraints();

        c.gridy = row++;
        c.gridx = isMyMessage?1:0;
        c.weightx = 0.5;
        //c.insets = new Insets(10,10,10,10);  //top padding
//        c.gridwidth = GridBagConstraints.REMAINDER;
        c.fill = GridBagConstraints.HORIZONTAL;
        overFlowPane.add(outer, c);

        c.gridy = row++;
        c.gridx = isMyMessage?0:1;
        c.weightx = 0.5;
//        c.gridwidth = 1;
        c.fill = GridBagConstraints.HORIZONTAL;

        JTextArea temp = new JTextArea();
//        JPanel temp = new JPanel();
        temp.setBackground(null);
        if (isLong) {
            temp.setLineWrap(true);  // Enable text wrapping
            temp.setWrapStyleWord(true);  // Wrap at word boundaries
        }
        overFlowPane.add(temp, c);
        overFlowPane.revalidate();
//        scrollToBottom();
    }
    void scrollToBottom() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JScrollBar verticalScrollBar = chatScrollPane.getVerticalScrollBar();
                verticalScrollBar.setValue(verticalScrollBar.getMaximum());
                chatScrollPane.revalidate();
            }
        });
    }


}