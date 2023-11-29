import java.awt.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

public class UserList extends JPanel {
    public UserList() {
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
        JLabel label1 = new JLabel("Tên người dùng");
        label1.setBackground(Color.white);
        label1.setOpaque(true);
        JTextField textField1 = new JTextField(16);
        panel1.add(label1, BorderLayout.NORTH);
        panel1.add(textField1, BorderLayout.CENTER);

        // Label and text field 2
        JPanel panel2 = new JPanel(new BorderLayout());
        JLabel label2 = new JLabel("Tên đăng nhập");
        label2.setBackground(Color.white);
        label2.setOpaque(true);
        JTextField textField2 = new JTextField(16);
        panel2.add(label2, BorderLayout.NORTH);
        panel2.add(textField2, BorderLayout.CENTER);

        // Label and combo box
        JPanel panel3 = new JPanel(new BorderLayout());
        JLabel label3 = new JLabel("Trạng thái");
        label3.setBackground(Color.white);
        label3.setOpaque(true);
        JComboBox<String> comboBox = new JComboBox<>(new String[] { "Hoạt động", "Tạm vắng", "Ngoại tuyến" });
        panel3.add(label3, BorderLayout.NORTH);
        panel3.add(comboBox, BorderLayout.CENTER);

        // search button
        JButton[] searchButtons = new JButton[4];
        searchButtons[0] = new JButton("Tìm kiếm");
        searchButtons[1] = new JButton("Lịch sử đăng nhập");
        searchButtons[2] = new JButton("Danh sách bạn bè");
        searchButtons[3] = new JButton("Khóa tài khoản");

        searchBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        searchBar.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        searchBar.add(panel1);
        searchBar.add(Box.createRigidArea(new Dimension(5, 0)));
        searchBar.add(panel2);
        searchBar.add(Box.createRigidArea(new Dimension(5, 0)));
        searchBar.add(panel3);
        for (int i = 0; i < 4; i++) {
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
        String[] columns = { "Tên đăng Nhập", "Họ tên", "Địa chỉ", "Ngày sinh", "Giới tính", "Email", "Actions" };

        // Define the table data
        Object[][] data = {
                { "HTVinh", "Huynh Tan Vinh", "135 Tran Hung Dao, Q1, TP Ho Chi Minh", "01/01/1111", "Nam",
                        "htvinh201@gmail.com", "Update, Delete" },
                { "TAKhoi", "Tran Anh Khoi", "135 Tran Hung Dao, Q1, TP Ho Chi Minh", "01/01/1111", "Nam",
                        "takhoi@gmail.com", "Update, Delete" },
                { "TAKhoi", "Tran Anh Khoi", "135 Tran Hung Dao, Q1, TP Ho Chi Minh", "01/01/1111", "Nam",
                        "takhoi@gmail.com", "Update, Delete" }
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
        table.getColumnModel().getColumn(6).setCellRenderer(new MultiButtonRenderer());

        // Set the preferred width of each column
        table.getColumnModel().getColumn(0).setPreferredWidth(100); // "Tên Đăng Nhập"
        table.getColumnModel().getColumn(1).setPreferredWidth(120); // "Họ Tên"
        table.getColumnModel().getColumn(2).setPreferredWidth(250); // "Địa Chỉ"
        table.getColumnModel().getColumn(3).setPreferredWidth(80); // "Ngày Sinh"
        table.getColumnModel().getColumn(4).setPreferredWidth(60); // "Giới Tính"
        table.getColumnModel().getColumn(5).setPreferredWidth(150); // "Email"
        table.getColumnModel().getColumn(6).setPreferredWidth(140); // "Actions"
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        userListPanel.add(new JScrollPane(table), BorderLayout.CENTER);

        // Add the user list part to the body part
        add(userListPanel, BorderLayout.CENTER);

        // Add a list selection listener to the table
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                    for (int i = 1; i < 4; i++) {
                        searchButtons[i].setVisible(true);
                    }
                } else {
                    // No row is selected, hide the button
                    for (int i = 1; i < 4; i++) {
                        searchButtons[i].setVisible(false);
                    }
                }
            }
        });
    }
}
