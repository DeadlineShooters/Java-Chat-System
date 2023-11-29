package Client.Admin.Views;

import javax.swing.*;
import javax.swing.table.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    CardLayout cardLayout = new CardLayout();
    JPanel bodyPanel = new JPanel(cardLayout);

    public Home() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 720);
        setLocationRelativeTo(null);

        Sidebar sidebar = new Sidebar();
        UserList userList = new UserList();
        LoginList loginList = new LoginList();
        GroupChatList groupChatList = new GroupChatList();
        GroupMemberList groupMemberList = new GroupMemberList();
        ReportList reportList = new ReportList();
        FriendList friendList = new FriendList();
        LoginHistory loginHistory = new LoginHistory();

        getContentPane().add(sidebar, BorderLayout.WEST);
        bodyPanel.add(userList, "Client.Admin.Views.UserList");
        bodyPanel.add(loginList, "Client.Admin.Views.LoginList");
        bodyPanel.add(groupChatList, "Client.Admin.Views.GroupChatList");
        bodyPanel.add(reportList, "Client.Admin.Views.ReportList");
        getContentPane().add(bodyPanel, BorderLayout.CENTER);

        // switch body panel
        cardLayout.show(bodyPanel, "Client.Admin.Views.UserList");
        String[] menuItems = { "Client.Admin.Views.UserList", "Client.Admin.Views.LoginList", "Client.Admin.Views.GroupChatList", "Client.Admin.Views.ReportList" };
        for (int i = 0; i < 4; i++) {
            final int index = i;
            sidebar.getButton(i).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    cardLayout.show(bodyPanel, menuItems[index]);
                }
            });
        }
        setVisible(true);
    }


}
