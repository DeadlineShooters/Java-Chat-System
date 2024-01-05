package Client.Admin.Views;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import Client.Admin.Views.Components.LineChart;
import Client.Admin.Views.Components.UserList;
import Client.Models.User;
import Client.Admin.Repository.UserRepository;


public class UserManagement extends JPanel  {
    protected UserRepository userRepository = new UserRepository();

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

    public LineChart getLineChart() {
        return lineChart;
    }

    public void refresh(){
        userList.clearDatePickers();

        userList.updateTable(userRepository.getUsers());
        lineChart.updateDataPoints();
    }
}
