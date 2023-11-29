package Client.Admin.Views;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class Sidebar extends Box {
    List<JButton> buttons = new ArrayList<>();
    public Sidebar() {
        super(BoxLayout.Y_AXIS);
        setPreferredSize(new Dimension(256, 0));
        setBackground(Color.gray);
        add(Box.createRigidArea(new Dimension(0, 10)));
        String[] menuItems = { "Danh sách người dùng", "Danh sách đăng nhập", "Danh sách nhóm chat",
                "Danh sách báo cáo" };
        for (int i = 0; i < 4; i++) {
            JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            panel.setOpaque(false); // panel transparent
            JButton button = new JButton(menuItems[i]);
            button.setForeground(Color.white);
            button.setMaximumSize(new Dimension(Integer.MAX_VALUE, button.getMinimumSize().height));
            button.setFont(new Font("Nunito", Font.BOLD, 20));
            button.setBorderPainted(false);
            button.setFocusPainted(false);
            button.setContentAreaFilled(false);
            button.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Change cursor to hand cursor when hovering over the
                                                              // button
            panel.add(button); // Add button to the panel
            panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, button.getPreferredSize().height)); // Set panel's maximum size to fit button
            add(panel); // Add panel to the vertical Box
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
