package Client.Admin.Views;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
//import java.util.Calendar;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;

import Client.Admin.Views.Components.LineChart;
import Client.Admin.Views.Components.UserList;
import org.jdesktop.swingx.JXDatePicker;
//import org.jdesktop.swingx.JXDatePicker;


public class UserManagement extends JPanel {
    public UserManagement() {
        setBackground(new Color(0xECEDEF));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // -------- Add chart ---------
        LineChart lineChart = new LineChart();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0.7; // lineChart takes more height
        gbc.fill = GridBagConstraints.BOTH;
        add(lineChart, gbc);

        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.setBackground(Color.white);
        UserList userList = new UserList();
        listPanel.add(userList, BorderLayout.CENTER);

        gbc.gridy = 1;
        gbc.weighty = 0.3; // listPanel takes less height
        add(listPanel, gbc);
    }
}
