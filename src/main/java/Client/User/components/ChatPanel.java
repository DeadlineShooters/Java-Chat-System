package Client.User.components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatPanel extends JPanel {
    private JTextArea chatArea;
    private JTextArea inputArea;
    private JButton sendButton;
     public ChatPanel() {
         super(new BorderLayout());
         setBorder(new EmptyBorder(0, 0, 5, 0));
         chatArea = new JTextArea();
         inputArea = new JTextArea();
         sendButton = new JButton("Send");
         JScrollPane chatScrollPane = new JScrollPane(chatArea);
         add(chatScrollPane, BorderLayout.CENTER);

         JPanel inputPanel = new JPanel(new BorderLayout());
         inputArea.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
         inputPanel.add(inputArea, BorderLayout.CENTER);
         inputPanel.add(sendButton, BorderLayout.EAST);

         sendButton.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 sendMessage();
             }
         });

         add(inputPanel, BorderLayout.SOUTH);

    }

    void sendMessage() {

    }
}
