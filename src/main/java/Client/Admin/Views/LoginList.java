package Client.Admin.Views;

import java.awt.*;

import javax.swing.*;
import javax.swing.table.*;

public class LoginList extends JPanel {
    public LoginList() {
        setLayout(new BorderLayout());
        setBackground(Color.white);

        // Add an order list to the top right of the user list part
        JPanel userListPanel = new JPanel(new BorderLayout());

        // Add a user list to the user list part
        String[] columns = { "Login time", "Username", "Name" };

        // Define the table data
        Object[][] data = {
                { "2013-01-01 01:01:01", "TuanTu", "Pham Tran Tuan Tu" },
                { "2013-01-01 01:01:01", "TuanTu", "Pham Tran Tuan Tu" },
                { "2013-01-01 01:01:01", "TuanTu", "Pham Tran Tuan Tu" },
                { "2013-01-01 01:01:01", "TuanTu", "Pham Tran Tuan Tu" },
                { "2013-01-01 01:01:01", "TuanTu", "Pham Tran Tuan Tu" },
                { "2013-01-01 01:01:01", "TuanTu", "Pham Tran Tuan Tu" },
                { "2013-01-01 01:01:01", "TuanTu", "Pham Tran Tuan Tu" },
                { "2013-01-01 01:01:01", "TuanTu", "Pham Tran Tuan Tu" },
                { "2013-01-01 01:01:01", "TuanTu", "Pham Tran Tuan Tu" },
                { "2013-01-01 01:01:01", "TuanTu", "Pham Tran Tuan Tu" },
                { "2013-01-01 01:01:01", "TuanTu", "Pham Tran Tuan Tu" },
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
        table.getColumnModel().getColumn(0).setPreferredWidth(100); // "Thời gian"
        table.getColumnModel().getColumn(1).setPreferredWidth(100); // "Tên Đăng Nhập"
        table.getColumnModel().getColumn(2).setPreferredWidth(100); // "Họ Tên"
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        userListPanel.add(new JScrollPane(table), BorderLayout.CENTER);

        // Add the user list part to the body part
        add(userListPanel, BorderLayout.CENTER);
    }
}

