package Client.User.components;

import javax.swing.*;
import java.awt.*;

public class ChatPanel extends JPanel {
    private JTextArea chatArea;
    private JTextArea inputArea;
    private JButton sendButton;
     public ChatPanel() {
         super(new BorderLayout());
         chatArea = new JTextArea();
         inputArea = new JTextArea();
         sendButton = new JButton("Send");
         JScrollPane chatScrollPane = new JScrollPane(chatArea);
         add(chatScrollPane, BorderLayout.CENTER);

         JPanel inputPanel = new JPanel(new BorderLayout());
         inputArea.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
         inputPanel.add(inputArea, BorderLayout.CENTER);
         inputPanel.add(sendButton, BorderLayout.EAST);

         add(inputPanel, BorderLayout.SOUTH);

    }
}
