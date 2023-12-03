package Client.Admin.Views;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class Sidebar extends Box {
    List<JButton> buttons = new ArrayList<>();

    public Sidebar() {
        super(BoxLayout.Y_AXIS);
        setPreferredSize(new Dimension(200, 0));
//        setBackground(Color.gray);
        add(Box.createRigidArea(new Dimension(0, 10)));
        String[] menuItems = { "User List", "Login List", "Group Chat List",
                "Report List", "Manage Active Users" };
                String[] icons = {"user.png", "login.png", "groupchat.png", "report.png", "active-user.png"};
        for (int i = 0; i < icons.length; i++) {
            JPanel panel = new JPanel(new GridBagLayout()); // Use GridBagLayout for the panel
            panel.setOpaque(false); // panel transparent
            JButton button = new JButton("<html><p style='width: 140px'>" + menuItems[i] + "</p></html>");
            button.setForeground(Color.white);
            button.setFont(new Font("Segoe UI Variable Text", Font.BOLD, 18));
            button.setBorderPainted(false);
            button.setFocusPainted(false);
            button.setContentAreaFilled(false);
            button.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Change cursor to hand cursor when hovering over the
                                                              // button
            button.setHorizontalAlignment(SwingConstants.LEFT); // Align text to the left
            ImageIcon icon = new ImageIcon(getClass().getResource("/Image/" + icons[i]));
            Image img = icon.getImage().getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
            icon = new ImageIcon(img);
            button.setIcon(icon);
            button.setIconTextGap(16);
            add(button);
            buttons.add(button);

            // Add space between buttons
            if (i < 4) {
                add(Box.createRigidArea(new Dimension(0, 20)));
            }
        }

        setBackground(new Color(0x36598E));
        setOpaque(true);
    }

    public JButton getButton(int index) {
        return buttons.get(index);
    }
}

