package Client.User;

import Client.User.components.ChatPanel;
import Client.User.components.FriendsPanel;
import Client.User.components.MenuBar;
import Client.User.components.SettingsPanel;

import javax.swing.*;
import java.awt.*;

public class ChatAppUI extends JFrame {




    public ChatAppUI() {
        // Set up the main frame
        setTitle("Chat App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Create components


        // Set layout manager
        setLayout(new BorderLayout());

        // Add components to the main frame
        add(new MenuBar(), BorderLayout.NORTH);
        add(new FriendsPanel(), BorderLayout.WEST);
        add(new ChatPanel(), BorderLayout.CENTER);
        add(new SettingsPanel(), BorderLayout.EAST);

        // Make the frame visible
        setVisible(true);
    }






    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ChatAppUI());
    }
}
