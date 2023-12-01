package Client.Admin.Views;



import Client.Admin.Views.Components.MultiButtonRenderer;

import java.awt.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

public class ReportList extends JPanel {
    public ReportList() {
        setLayout(new BorderLayout());
        setBackground(Color.white);

        // Add a search bar to the body part
        JPanel searchBar = new JPanel();
        searchBar.setBackground(Color.white); // Change to the color you want

        // Set the border
        searchBar.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
        searchBar.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Label and text field 1
        JPanel panel1 = new JPanel(new BorderLayout());
        JLabel label1 = new JLabel("Tên người gửi");
        label1.setBackground(Color.white);
        label1.setOpaque(true);
        JTextField textField1 = new JTextField(16);
        panel1.add(label1, BorderLayout.NORTH);
        panel1.add(textField1, BorderLayout.CENTER);

        // Label and text field 2
        JPanel panel2 = new JPanel(new BorderLayout());
        JLabel label2 = new JLabel("Tên người bị báo cáo");
        label2.setBackground(Color.white);
        label2.setOpaque(true);
        JTextField textField2 = new JTextField(16);
        panel2.add(label2, BorderLayout.NORTH);
        panel2.add(textField2, BorderLayout.CENTER);

        // search button
        JButton[] searchButtons = new JButton[3];
        searchButtons[0] = new JButton("Tìm kiếm");
        searchButtons[1] = new JButton("Danh sách thành viên");
        searchButtons[2] = new JButton("Danh sách admin");

        searchBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        searchBar.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        searchBar.add(panel1);
        searchBar.add(Box.createRigidArea(new Dimension(5, 0)));
        searchBar.add(panel2);
        searchBar.add(Box.createRigidArea(new Dimension(5, 0)));
        for (int i = 0; i < 3; i++) {
            searchBar.add(Box.createRigidArea(new Dimension(10, 0)));
            searchBar.add(searchButtons[i]);
            if (i > 0) {
                searchButtons[i].setVisible(false);
            }
        }

        add(searchBar, BorderLayout.NORTH);

        // Add an order list to the top right of the user list part
        JPanel userListPanel = new JPanel(new BorderLayout());
        JPanel orderListPanel = new JPanel();
        orderListPanel.setLayout(new BoxLayout(orderListPanel, BoxLayout.X_AXIS));
        JComboBox<String> orderList = new JComboBox<>(new String[] { "Sắp xếp theo tên", "Sắp xếp theo ngày tạo" });
        orderList.setMaximumSize(orderList.getPreferredSize()); // This will make the JComboBox not stretch
        orderListPanel.add(Box.createHorizontalGlue()); // This will push the JComboBox to the right
        orderListPanel.add(orderList);

        // Add the order list panel to the user list part
        userListPanel.add(orderListPanel, BorderLayout.NORTH);

        // Add a user list to the user list part
        String[] columns = { "Tên người gửi", "Tên người bị báo cáo", "Thời gian tạo", "Actions" };

        // Define the table data
        Object[][] data = {
                { "Nguyễn Văn A", "Nguyễn Văn D", "2001-01-01 01:01:01", "Khóa tài khoản" },
                { "Nguyễn Văn B", "Nguyễn Văn E", "2001-01-01 01:01:01", "Khóa tài khoản"  },
                { "Nguyễn Văn C", "Nguyễn Văn F", "2001-01-01 01:01:01", "Khóa tài khoản"  },
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
        
        // Set a custom renderer and editor for the last column
        table.getColumnModel().getColumn(3).setCellRenderer(new MultiButtonRenderer());

        // Set the preferred width of each column
        table.getColumnModel().getColumn(0).setPreferredWidth(100); // "Tên người gửi"
        table.getColumnModel().getColumn(1).setPreferredWidth(100); // "Tên người bị báo cáo"
        table.getColumnModel().getColumn(2).setPreferredWidth(120); // "Thời gian tạo"
        table.getColumnModel().getColumn(3).setPreferredWidth(30); // "Actions"
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        userListPanel.add(new JScrollPane(table), BorderLayout.CENTER);

        // Add the user list part to the body part
        add(userListPanel, BorderLayout.CENTER);

        // Add a list selection listener to the table
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                    for (int i = 1; i < 3; i++) {
                        searchButtons[i].setVisible(true);
                    }
                } else {
                    // No row is selected, hide the button
                    for (int i = 1; i < 3; i++) {
                        searchButtons[i].setVisible(false);
                    }
                }
            }
        });
    }
}
