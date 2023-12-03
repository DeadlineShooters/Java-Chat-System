package Client.Admin.Views;

import java.awt.*;

import javax.swing.*;

import Client.Admin.Views.Components.ActiveUserList;
import Client.Admin.Views.Components.LineChart;

public class ActiveUserScreen extends JPanel {
    LineChart lineChart = new LineChart("Chart of the number of active people by year", "Month", "People who have opened the app");
    ActiveUserList list = new ActiveUserList();

    public ActiveUserScreen() {
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
        add(list, gbc);
    }

}
