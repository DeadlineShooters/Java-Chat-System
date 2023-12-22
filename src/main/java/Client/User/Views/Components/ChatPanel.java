package Client.User.Views.Components;

import Client.User.CurrentUser;
import Client.User.Views.Util;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.Objects;

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
         JLabel username = new JLabel(CurrentUser.getInstance().getUser().username());
         ImageIcon profileImage = createRoundedImageIcon("user-avatar.jpg", 30);

//         ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("user-avatar.jpg")));
//         Image img = icon.getImage().getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH);
//         ImageIcon profileImage = new ImageIcon(img);
         username.setIcon(profileImage);
         topPanel.add(username);
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
    public ImageIcon createRoundedImageIcon(String path, int PROFILE_IMAGE_SIZE) {
        ImageIcon originalIcon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource(path)));
        Image img = originalIcon.getImage();
        BufferedImage roundedImage = new BufferedImage(PROFILE_IMAGE_SIZE, PROFILE_IMAGE_SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = roundedImage.createGraphics();

        // Create a circular clipping shape
        Ellipse2D.Double clip = new Ellipse2D.Double(0, 0, PROFILE_IMAGE_SIZE, PROFILE_IMAGE_SIZE);
        g2d.setClip(clip);

        // Draw the original image onto the clipped area
        g2d.drawImage(img, 0, 0, PROFILE_IMAGE_SIZE, PROFILE_IMAGE_SIZE, null);
        g2d.dispose();

        return new ImageIcon(roundedImage);
    }
}
class CustomMsgCell extends JLabel implements ListCellRenderer<String> {
    @Override
    public Component getListCellRendererComponent(
            JList<? extends String> list, String username, int index,
            boolean isSelected, boolean cellHasFocus) {


        ImageIcon profileImage = Util.createRoundedImageIcon("profile_image.jpg", 30);
        // To create square profile image

        setIcon(profileImage);
        setText(username);

        setOpaque(true);
        setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
        setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());


        Border border = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.lightGray);
        setBorder(BorderFactory.createCompoundBorder(border,
                BorderFactory.createEmptyBorder(0, 10, 0, 0)));


        // Set height for each cell
        setPreferredSize(new Dimension(getPreferredSize().width, 60));

        return this;
    }
}
