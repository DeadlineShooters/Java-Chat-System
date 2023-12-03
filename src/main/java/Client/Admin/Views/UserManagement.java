package Client.Admin.Views;

import java.awt.*;

import javax.swing.*;

import Client.Admin.Views.Components.LineChart;
import Client.Admin.Views.Components.UserList;



public class UserManagement extends JPanel {
    protected LineChart lineChart = new LineChart("Newly registered users per year",
            "Month",
            "The number of new registrations");
    protected UserList userList = new UserList();

    public UserManagement() {
        setBackground(new Color(0xECEDEF));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // -------- Add chart ---------

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0.7; // lineChart takes more height
        gbc.fill = GridBagConstraints.BOTH;
        add(lineChart, gbc);

        gbc.gridy = 1;
        gbc.weighty = 0.3; // listPanel takes less height
        add(userList, gbc);
    }

    public UserList getUserList() {
        return userList;
    }
}
