package Client.User.Views.Components;

import Client.Models.Message;
import Client.User.CurrentUser;
import Client.User.Repositories.MessageRepo;
import Client.User.Views.Util;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ChatPanel extends JPanel {
//    private JList messagesArea;
    private JTextArea messagesArea;
    private JTextArea inputArea;
    private JButton sendButton;
    private static ChatPanel chatPanel;
    JPanel overFlowPane;
    static int row = 0;
    static String chatRoomId;

    public static ChatPanel getInstance() {
        if (chatPanel == null) {
            chatPanel = new ChatPanel();
        }
        return chatPanel;
    }
     private ChatPanel() {
         super(new BorderLayout());
         setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

         JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
//         topPanel.setSize(this.getPreferredSize().width, 600);
         JLabel topBar = new JLabel();
//         topBar.setText("   " + username);
         topBar.setFont(new Font("Arial", Font.BOLD, 14));

         ImageIcon profileImage = Util.createRoundedImageIcon("user-avatar.jpg", 50);
         topBar.setIcon(profileImage);
         topPanel.add(topBar);
         topPanel.setBackground(Color.lightGray);
         add(topPanel, BorderLayout.NORTH);

//         DefaultListModel<String> listModel = new DefaultListModel<>();
//         messagesArea = new JList(listModel);
//         messagesArea.setCellRenderer(new CustomMsgCell());

//         messagesArea = new JTextArea();
//         messagesArea.setEditable(false);
//         messagesArea.setLineWrap(true);  // Enable text wrapping
//         messagesArea.setWrapStyleWord(true);  // Wrap at word boundaries
//         messagesArea.
         overFlowPane = new JPanel();
//         overFlowPane.setLayout(new GridLayout(0, 1));
         overFlowPane.setLayout(new GridBagLayout());

//         overFlowPane.setBackground(Color.pink);

         JPanel subPanel = new JPanel(new BorderLayout());
         subPanel.add(overFlowPane, BorderLayout.NORTH);



//         JScrollPane chatScrollPane = new JScrollPane(messagesArea);
         JScrollPane chatScrollPane = new JScrollPane(subPanel);
         chatScrollPane.setBorder(new EmptyBorder(0,0,0,0));
         chatScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
         add(chatScrollPane, BorderLayout.CENTER);

         JPanel inputPanel = new JPanel(new BorderLayout());
         inputArea = new JTextArea();
         sendButton = new JButton("Send");
         inputArea.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
         inputPanel.add(inputArea, BorderLayout.CENTER);
         inputPanel.add(sendButton, BorderLayout.EAST);
         inputPanel.setBackground(Color.white);
         inputPanel.setBorder(new EmptyBorder(5,5,5,5));

         JButton recvBtn = new JButton("Receive");
         inputPanel.add(recvBtn, BorderLayout.WEST);

         recvBtn.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
//                 addMsg("1234567890123456789012345678901234567890123456789012345678901234567890123456789", false);
             }
         });

         sendButton.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 sendMessage();
             }
         });

         add(inputPanel, BorderLayout.SOUTH);


    }
    static public void startChatting(String chatRoomId) {
        ChatPanel.chatRoomId = chatRoomId;
        ArrayList<Message> messages = MessageRepo.getAllMessages(chatRoomId);



    }
    static void loadMessages(String username) {

    }

    public void sendMessage() {
        String text = inputArea.getText();
        if (text.isEmpty()) {
            return;
        }
        Message message = new Message(chatRoomId, CurrentUser.getInstance().getUser().username(), text, "", Util.getCurrentTimestamp());
        addMsg(message);
        inputArea.setText("");
//        CurrentUser.getInstance().sendMessage(message);
    }
    public void addMsg(Message message) {
        JTextArea chatBox = new JTextArea();
//        chatBox.setColumns(30);

        boolean isMyMessage = message.username() == CurrentUser.getInstance().getUser().username();
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
        hold.setBorder(new EmptyBorder(10, 10, 10,10));
        hold.setLayout(new BorderLayout());
//        hold.setBackground(Color.red);

        LocalDateTime currentDateTime = LocalDateTime.now();

        // Format the date and time using a specific pattern
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        String formattedDateTime = currentDateTime.format(formatter);
        JLabel timeStamp = new JLabel(formattedDateTime);



        JLabel ava = new JLabel();
        ava.setIcon(Util.createRoundedImageIcon("user-avatar.jpg", 40));
        ava.setBorder(new EmptyBorder(0, 0, 0,10));
        if (!isMyMessage)
            hold.add(ava, BorderLayout.WEST);
        if (isLong)
            hold.add(wrapper, BorderLayout.CENTER);
        else
            hold.add(wrapper, BorderLayout.EAST);


        GridBagConstraints c = new GridBagConstraints();

        c.gridy = row;
        c.gridx = isMyMessage?1:0;
        c.weightx = 0.5;
        //c.insets = new Insets(10,10,10,10);  //top padding
//        c.gridwidth = GridBagConstraints.REMAINDER;
        c.fill = GridBagConstraints.HORIZONTAL;
        overFlowPane.add(hold, c);

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
    void addNotSelfMsg(String message) {
        JPanel item = new JPanel(new GridLayout(1, 2));

        item.setBackground(Color.red);
        item.setBorder(new LineBorder(Color.black, 1));

        JTextArea chatBox = new JTextArea();

        chatBox.setLineWrap(true);  // Enable text wrapping
        chatBox.setWrapStyleWord(true);  // Wrap at word boundaries

        chatBox.setText("You: " + message);
        chatBox.setBackground(Color.yellow);

        JPanel temp = new JPanel();
        item.add(temp);
        item.add(chatBox);
        overFlowPane.add(item);
        overFlowPane.revalidate();
    }
}