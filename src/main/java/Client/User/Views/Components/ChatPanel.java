package Client.User.Views.Components;

import Client.User.CurrentUser;
import Client.User.Views.Util;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatPanel extends JPanel {
//    private JList messagesArea;
    private JTextArea messagesArea;
    private JTextArea inputArea;
    private JButton sendButton;
    private static ChatPanel chatPanel;

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

         messagesArea = new JTextArea();
         messagesArea.setEditable(false);

         JScrollPane chatScrollPane = new JScrollPane(messagesArea);
         chatScrollPane.setBorder(new EmptyBorder(0,0,0,0));
         add(chatScrollPane, BorderLayout.CENTER);

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

         add(inputPanel, BorderLayout.SOUTH);


    }
    static void loadMessages(String username) {

    }

    public void sendMessage() {
        String message = inputArea.getText();
        if (!message.isEmpty()) {
            addSelfMsg(message);
            inputArea.setText("");
            CurrentUser.getInstance().sendMessage(message);
        }
    }
    public void addSelfMsg(String message) {
        messagesArea.append("You: " + message + "\n");
    }
    void addNotSelfMsg() {

    }
}