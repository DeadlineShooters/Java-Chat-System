package Client.Admin.Views;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.*;

import Client.Admin.Repository.ChatMemberRepository;
import Client.Admin.Repository.GroupAdminRepository;
import Client.Models.User;

public class GroupMemberList extends JPanel {
    protected JTable table;
    protected JButton[] searchButtons = new JButton[3];
    protected TableRowSorter<DefaultTableModel> rowSorter;
    protected ChatMemberRepository chatMemberRepository = new ChatMemberRepository();
    protected GroupAdminRepository groupAdminRepository = new GroupAdminRepository();
    public String chatRoomId;
    public JButton returnButton = new JButton("Return");

    public GroupMemberList() {
        setLayout(new BorderLayout());
        setBackground(Color.white);

        // Add the return button to the top left corner
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(returnButton, BorderLayout.WEST);

        // Add a search bar to the body part
        JPanel searchBar = new JPanel();
        searchBar.setBackground(Color.white); // Change to the color you want

        // Set the border
        searchBar.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
        searchBar.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Label and text field 1
        JPanel panel1 = new JPanel(new BorderLayout());
        JLabel label1 = new JLabel("Name");
        label1.setBackground(Color.white);
        label1.setOpaque(true);
        JTextField textField1 = new JTextField(16);
        panel1.add(label1, BorderLayout.NORTH);
        panel1.add(textField1, BorderLayout.CENTER);

        // Label and text field 2
        JPanel panel2 = new JPanel(new BorderLayout());
        JLabel label2 = new JLabel("Username");
        label2.setBackground(Color.white);
        label2.setOpaque(true);
        JTextField textField2 = new JTextField(16);
        panel2.add(label2, BorderLayout.NORTH);
        panel2.add(textField2, BorderLayout.CENTER);

        // Label and combo box
        JPanel panel3 = new JPanel(new BorderLayout());
        JLabel label3 = new JLabel("Status");
        label3.setBackground(Color.white);
        label3.setOpaque(true);
        JComboBox<String> comboBox = new JComboBox<>(new String[] { "All", "Online", "Offline" });
        panel3.add(label3, BorderLayout.NORTH);
        panel3.add(comboBox, BorderLayout.CENTER);

        // search button
        searchButtons[0] = new JButton("Search");
        searchButtons[1] = new JButton("Member list");
        searchButtons[2] = new JButton("Admin list");

        searchBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        searchBar.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        searchBar.add(panel1);
        searchBar.add(Box.createRigidArea(new Dimension(5, 0)));
        searchBar.add(panel2);
        searchBar.add(Box.createRigidArea(new Dimension(5, 0)));
        searchBar.add(panel3);
        searchBar.add(Box.createRigidArea(new Dimension(5, 0)));
        for (int i = 0; i < 3; i++) {
            searchBar.add(Box.createRigidArea(new Dimension(10, 0)));
            searchBar.add(searchButtons[i]);
        }

        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));
        northPanel.add(topPanel);
        northPanel.add(searchBar);
        add(northPanel, BorderLayout.NORTH);

        JPanel userListPanel = new JPanel(new BorderLayout());

        String[] columns = { "Username", "Name", "Address", "Day of birth", "Gender", "Email", "Status" };

        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // All cells are editable
            }
        };

        table = new JTable(model);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);

        table.getColumnModel().getColumn(0).setPreferredWidth(100); // "Tên Đăng Nhập"
        table.getColumnModel().getColumn(1).setPreferredWidth(120); // "Họ Tên"
        table.getColumnModel().getColumn(2).setPreferredWidth(240); // "Địa Chỉ"
        table.getColumnModel().getColumn(3).setPreferredWidth(60); // "Ngày Sinh"
        table.getColumnModel().getColumn(4).setPreferredWidth(40); // "Giới Tính"
        table.getColumnModel().getColumn(5).setPreferredWidth(150); // "Email"
        table.getColumnModel().getColumn(6).setPreferredWidth(50); // "Status"
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        rowSorter = new TableRowSorter<>(model);
        table.setRowSorter(rowSorter);

        updateTable(searchButtons[1].getText());

        userListPanel.add(new JScrollPane(table), BorderLayout.CENTER);

        // Add the user list part to the body part
        add(userListPanel, BorderLayout.CENTER);
    }

    public void updateTable(String type) {
        ArrayList<User> chatMembers = new ArrayList<User>();
        if (type.equals(searchButtons[1].getText())) {
            chatMembers = chatMemberRepository.getChatMembers(chatRoomId);
        }
        else {
            chatMembers = groupAdminRepository.getGroupAdmins(chatRoomId);
        }
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        for (User user : chatMembers) {
            Object[] row = new Object[7];
            row[0] = user.username();
            row[1] = user.name();
            row[2] = user.address();
            row[3] = user.dob();
            row[4] = user.gender();
            row[5] = user.email();
            row[6] = user.status() ? "Online" : "Offline";
            model.addRow(row);
        }
        table.setModel(model);
    }

    public void search(String username, String fullName, String status) {
        List<RowFilter<DefaultTableModel, Object>> filters = new ArrayList<RowFilter<DefaultTableModel, Object>>();
        if (username.length() > 0) {
            RowFilter<DefaultTableModel, Object> usernameFilter = RowFilter.regexFilter("(?i)" + username, 0);
            filters.add(usernameFilter);
        }
        if (fullName.length() > 0) {
            RowFilter<DefaultTableModel, Object> fullNameFilter = RowFilter.regexFilter("(?i)" + fullName, 1);
            filters.add(fullNameFilter);
        }
        if (!status.equals("All")) {
            RowFilter<DefaultTableModel, Object> statusFilter = RowFilter.regexFilter("(?i)" + status, 6);
            filters.add(statusFilter);
        }

        RowFilter<DefaultTableModel, Object> compoundRowFilter = RowFilter.andFilter(filters);
        rowSorter.setRowFilter(compoundRowFilter);
    }
}
