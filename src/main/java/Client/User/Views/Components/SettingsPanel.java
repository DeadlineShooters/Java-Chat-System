package Client.User.Views.Components;

import Client.User.CurrentUser;
import Client.User.Repositories.FriendRepo;
import Client.User.Views.Util;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SettingsPanel extends JPanel {
    private static SettingsPanel settingsPanel;
    final String[] buttonTexts = {"Unfriend", "Block", "Report spam", "Delete history", "Create group with this person"};
    String chatUsername = null;
    String username = CurrentUser.getInstance().getUser().username();
    String currentChatRoomId = null;
    public static SettingsPanel getInstance() {
        if (settingsPanel == null)
            settingsPanel = new SettingsPanel();
        return settingsPanel;
    }
    private SettingsPanel() {
        super(new BorderLayout(0, 15));
        setPreferredSize(new Dimension(300, this.getPreferredSize().height));


    }
    void initPrivateChat(String chatRoomId, String chatUsername) {
        this.removeAll();
        this.chatUsername = chatUsername;
        this.currentChatRoomId = chatRoomId;
        JButton searchButton = new JButton();
        ImageIcon searchIcon = Util.createImageIcon("searchIcon.png", 15, 15);
        searchButton.setIcon(searchIcon);

        // // Create add user button
        // JButton addUserButton = new JButton();
        // ImageIcon addIcon = Util.createRoundedImageIcon("add-user.png", 30);
        // addUserButton.setIcon(addIcon);

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

        // Add the button panel to the settings panel
        this.add(topPanel, BorderLayout.NORTH);
        this.add(bottomPanel, BorderLayout.CENTER);
        this.revalidate();
    }
//    final String[] buttonTexts = {"Unfriend", "Block", "Report spam", "Delete history", "Create group with this person"};
    public void performActionPrivate(String buttonText) {
        if (buttonText.equals(buttonTexts[0])) {
            String confirmMsg = "Are you sure you want to unfriend?";
            if (!showConfirmation(confirmMsg))
                return;
            unfriend();
        } else if (buttonText.equals(buttonTexts[1])) {
            String confirmMsg = "Are you sure you want to block this user";
            if (!showConfirmation(confirmMsg))
                return;
            block();
        } else if (buttonText.equals(buttonTexts[2])) {

        } else if (buttonText.equals(buttonTexts[3])) {
            String confirmMsg = "Are you sure you want to delete history?";
            if (!showConfirmation(confirmMsg))
                return;
            deleteHistory();
        } else if (buttonText.equals(buttonTexts[4])) {

        }
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
    }

    private void deleteHistory() {

    }

    void unfriend() {
        CurrentUser.getInstance().removeFriend(chatUsername);
        FriendRepo.unfriend(username, chatUsername);
        SidePanel.getInstance().displayChatrooms();
        SidePanel.getInstance().displayFriends();
        initPrivateChat(currentChatRoomId, chatUsername);
    }
    void block() {

    }
    void reportSpam() {

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
                    performActionPrivate(buttonText);
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Settings Panel");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(new SettingsPanel());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
