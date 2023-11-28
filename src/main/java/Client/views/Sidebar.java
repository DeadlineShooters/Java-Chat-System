import java.awt.*;

import javax.swing.*;

public class Sidebar extends Box {
    public Sidebar() {
        super(BoxLayout.Y_AXIS);
        setPreferredSize(new Dimension(256, 0));
        setBackground(Color.gray);
        add(Box.createRigidArea(new Dimension(0, 10)));
        String[] menuItems = { "Danh sách người dùng", "Danh sách đăng nhập", "Danh sách nhóm chat",
                "Danh sách báo cáo" };
        for (int i = 0; i < 4; i++) {
            JButton button = new JButton(menuItems[i]);
            button.setMaximumSize(new Dimension(Integer.MAX_VALUE, button.getMinimumSize().height));
            button.setFont(new Font("Nunito", Font.BOLD, 20));
            button.setBorderPainted(false);
            button.setFocusPainted(false);
            button.setContentAreaFilled(false);
            button.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Change cursor to hand cursor when hovering over the
                                                              // button
            add(button);

            // Add space between buttons
            if (i < 4) {
                add(Box.createRigidArea(new Dimension(0, 20)));
            }
        }
    }
}
