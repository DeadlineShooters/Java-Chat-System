import javax.swing.*;
import javax.swing.table.*;

import java.awt.*;

class MultiButtonRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 0)); // Set the layout to GridLayout
        String[] buttons = ((String) value).split(", ");
        for (String buttonText : buttons) {
            JButton button = new JButton(buttonText);
            panel.add(button);
        }
        return panel;
    }
}

public class Home extends JFrame {
    public Home() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 720);
        setLocationRelativeTo(null);

        Sidebar sidebar = new Sidebar();
        UserList userList = new UserList();
        LoginList loginList = new LoginList();
        GroupChatList groupChatList = new GroupChatList();
        GroupMemberList groupMemberList = new GroupMemberList();

        getContentPane().add(sidebar, BorderLayout.WEST);
        // getContentPane().add(userList, BorderLayout.CENTER);
        // getContentPane().add(loginList, BorderLayout.CENTER);
        getContentPane().add(groupMemberList, BorderLayout.CENTER);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Home();
        });
    }
}
