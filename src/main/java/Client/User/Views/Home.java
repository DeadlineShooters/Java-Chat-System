package Client.User.Views;

import Client.User.Views.Components.ChatPanel;
import Client.User.Views.Components.FriendsPanel;
import Client.User.Views.Components.MenuBar;
import Client.User.Views.Components.SettingsPanel;

import javax.swing.*;
import java.awt.*;

public class Home extends JFrame {
    public Home() {
        // Set up the main frame
        setTitle("Chat App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

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
        SwingUtilities.invokeLater(() -> new Home());
    }
}
