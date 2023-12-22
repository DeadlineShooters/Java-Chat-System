package Client.User.Views.Components;

import Client.Models.Message;
import Client.User.CurrentUser;
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
    JPanel overFlowPane;
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
    void initView() {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
//         topPanel.setSize(this.getPreferredSize().width, 600);
        JLabel topBar = new JLabel();
         topBar.setText("   " + name);
        topBar.setFont(new Font("Arial", Font.BOLD, 14));

        ImageIcon profileImage = Util.createRoundedImageIcon("user-avatar.jpg", 50);
        topBar.setIcon(profileImage);
        topPanel.add(topBar);
        topPanel.setBackground(Color.lightGray);
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
        this.add(chatScrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new BorderLayout());
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

//        JButton receivebtn = new JButton("Receive");
//        inputPanel.add(receivebtn, BorderLayout.WEST);
//        receivebtn.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String content = inputArea.getText();
//                if (content.isEmpty()) {
//                    return;
//                }
//
//                Timestamp sentAt = Util.getCurrentTimestamp();
//                Message message = new Message(chatRoomId, "nguyen tuan kiet", content, "", sentAt);
//                addMsg(message);
//                inputArea.setText("");
//            }
//        });
        this.add(inputPanel, BorderLayout.SOUTH);
        this.revalidate();
    }
    public void startChatting(String chatRoomId, String name) {
        this.chatRoomId = chatRoomId;
        this.name = name;

        CurrentUser.getInstance().sendMessage(myUsername+spliter+""+spliter+""+spliter+chatRoomId+spliter+"joinRoom");
        initView();
//        System.out.println(chatRoomId);
        loadMessages();

    }
    void loadMessages() {
        ArrayList<Message> messages = MessageRepo.getAllMessages(chatRoomId);
        for (Message msg : messages) {
            addMsg(msg);
        }
    }
    public void receiveMessage(String msgReceived) {
//        String text = inputArea.getText();
        System.out.println("ayy: "+msgReceived);

        String[] msgSplit = msgReceived.split(spliter);
        Message message = new Message(chatRoomId, msgSplit[0], msgSplit[1], "", Util.stringToTimestamp(msgSplit[2]));

        addMsg(message);
    }
    public void sendMessage() {
        if (chatRoomId.isEmpty()) return;
        String content = inputArea.getText();
        if (content.isEmpty()) {
            return;
        }

        Timestamp sentAt = Util.getCurrentTimestamp();
        Message message = new Message(chatRoomId, myUsername, content, "", sentAt);
        addMsg(message);
        inputArea.setText("");
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JScrollBar verticalScrollBar = chatScrollPane.getVerticalScrollBar();
                verticalScrollBar.setValue(verticalScrollBar.getMaximum());
                chatScrollPane.revalidate();
                chatScrollPane.repaint();
            }
        });
        MessageRepo.saveMessage(message);

        CurrentUser.getInstance().sendMessage(myUsername+spliter+content+spliter+sentAt+spliter+chatRoomId);
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

        hold.setBorder(new EmptyBorder(isMyMessage?10:0, 10, 0,20));
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
                usernameWrapper.setBorder(new EmptyBorder(0, 0, 0, 20));
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
            timeWrapper.setBorder(new EmptyBorder(0, 0, 0,20));
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
    }


}