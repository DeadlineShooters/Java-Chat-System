package Client.User.Views;

import Client.User.Client;
import Client.User.CurrentUser;
import Client.User.Repositories.SessionRepo;
import Client.User.Views.Components.ChatPanel;
import Client.User.Views.Components.MenuBar;
import Client.User.Views.Components.SettingsPanel;
import Client.User.Views.Components.SidePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Home extends JFrame {
    public Home() {
        // Set up the main frame
        setTitle("Chat App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);

        // Set layout manager
        setLayout(new BorderLayout());

        // Add components to the main frame
        add(new MenuBar(), BorderLayout.NORTH);
        add(SidePanel.getInstance(), BorderLayout.WEST);
        add(ChatPanel.getInstance(), BorderLayout.CENTER);
        add(SettingsPanel.getInstance(), BorderLayout.EAST);

        // Make the frame visible
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
//                int choice = JOptionPane.showConfirmDialog(Home.this,
//                        "Are you sure you want to close the application?",
//                        "Confirm Close", JOptionPane.YES_NO_OPTION);
//
//                if (choice == JOptionPane.YES_OPTION) {
//                    // Perform any cleanup or additional actions before closing
//                    dispose();
//                }
//                UserRepo.setStatus(CurrentUser.getInstance().getUser().username(), false);
                CurrentUser.getInstance().userSession.logoutTime = Util.getCurrentTimestamp();
                SessionRepo.addSession(CurrentUser.getInstance().userSession);
                System.out.println("at Home");
                Client.getInstance().closeEverything();
            }
        });
    }






    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Home());
    }
}
