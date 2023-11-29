package Client.Admin.Views;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

public class LoginHistory extends JPanel {
    public LoginHistory() {
        setLayout(new BorderLayout());
        setBackground(Color.white);

        JButton returnButton = new JButton("Return");
        returnButton.addActionListener(e -> {
            // Add your return logic here
        });

        // Add the return button to the top left corner
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(returnButton, BorderLayout.WEST);
        add(topPanel, BorderLayout.NORTH);

        // Add an order list to the top right of the user list part
        JPanel userListPanel = new JPanel(new BorderLayout());

        // Add a user list to the user list part
        String[] columns = { "Thời gian đăng nhập", "Thời gian đăng xuất", "Thiết bị" };

        // Define the table data
        Object[][] data = {
                { "2001-01-01 01:01:01", "2001-01-01 02:02:02", "Windows" },
                { "2001-01-01 01:01:01", "2001-01-01 02:02:02", "Windows" },
                { "2001-01-01 01:01:01", "2001-01-01 02:02:02", "Windows" }
        };

        // Create a new DefaultTableModel instance
        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // All cells are editable
            }
        };

        // Create a new JTable instance
        JTable table = new JTable(model);

        // Set the preferred width of each column
        table.getColumnModel().getColumn(0).setPreferredWidth(100); // "hời gian đăng nhập"
        table.getColumnModel().getColumn(1).setPreferredWidth(100); // "Thời gian đăng xuất"
        table.getColumnModel().getColumn(2).setPreferredWidth(80); // "Thiết bị"
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        userListPanel.add(new JScrollPane(table), BorderLayout.CENTER);

        // Add the user list part to the body part
        add(userListPanel, BorderLayout.CENTER);
    }
}
