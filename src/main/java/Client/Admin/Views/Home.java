package Client.Admin.Views;

import Client.Admin.Views.Components.UserList;

import javax.swing.*;
import javax.swing.table.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Home extends JFrame {
    CardLayout cardLayout = new CardLayout();
    JPanel bodyPanel = new JPanel(cardLayout);

    public Home() {
        getContentPane().setBackground(Color.gray);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 720);
        setLocationRelativeTo(null);

        Sidebar sidebar = new Sidebar();
        UserManagement userManagement = new UserManagement();
        LoginList loginList = new LoginList();
        GroupChatList groupChatList = new GroupChatList();
        GroupMemberList groupMemberList = new GroupMemberList();
        ReportList reportList = new ReportList();
        FriendList friendList = new FriendList();
        LoginHistory loginHistory = new LoginHistory();

        getContentPane().add(sidebar, BorderLayout.WEST);
        bodyPanel.add(userManagement, "Client.Admin.Views.UserList");
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