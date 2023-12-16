package Client.Admin.Views;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

import Client.Admin.Repository.ChatRoomRepository;
import Client.Admin.Repository.SessionRepository;
import Client.Models.ChatRoom;
import Client.Models.Session;

public class GroupChatList extends JPanel {
    protected JTable table;
    protected ChatRoomRepository chatRoomRepository = new ChatRoomRepository();
    protected TableRowSorter<DefaultTableModel> rowSorter;

    public String selectedGroupChat;
    public JButton[] searchButtons = new JButton[3];

    public GroupChatList() {
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
        JLabel label1 = new JLabel("Group chat name");
        label1.setBackground(Color.white);
        label1.setOpaque(true);
        JTextField textField1 = new JTextField(16);
        panel1.add(label1, BorderLayout.NORTH);
        panel1.add(textField1, BorderLayout.CENTER);

        // search button
        searchButtons[0] = new JButton("Search");
        searchButtons[1] = new JButton("Member list");
        searchButtons[2] = new JButton("Admin list");

        searchBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        searchBar.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        searchBar.add(panel1);
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

        String[] columns = { "Group chat name", "Created at", "Chat room ID" };

        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // All cells are editable
            }
        };

        // Create a new JTable instance
        table = new JTable(model);

        // Set the preferred width of each column
        table.getColumnModel().getColumn(0).setPreferredWidth(100); // "Tên nhóm"
        table.getColumnModel().getColumn(1).setPreferredWidth(120); // "Thời gian tạo"
        table.getColumnModel().getColumn(2).setMinWidth(0);
        table.getColumnModel().getColumn(2).setMaxWidth(0);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        rowSorter = new TableRowSorter<>(model);
        table.setRowSorter(rowSorter);

        updateTable();

        userListPanel.add(new JScrollPane(table), BorderLayout.CENTER);

        add(userListPanel, BorderLayout.CENTER);

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                selectedGroupChat = (String) table.getValueAt(table.getSelectedRow(), 2);
                if (!event.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                    for (int i = 1; i < 3; i++) {
                        searchButtons[i].setVisible(true);
                    }
                }
            }
        });

        searchButtons[0].addActionListener(e -> {
            String groupChatName = textField1.getText().trim();
            search(groupChatName);
        });
    }

    public void updateTable() {
        ArrayList<ChatRoom> chatRooms = chatRoomRepository.getChatRooms(); 
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        for (ChatRoom chatRoom : chatRooms) {
            Object[] row = new Object[3];
            row[0] = chatRoom.getName();
            row[1] = chatRoom.getCreatedAt();
            row[2] = chatRoom.getId();
            model.addRow(row);
        }
        table.setModel(model); 
    }

    public void search(String groupChatName) {
        List<RowFilter<DefaultTableModel, Object>> filters = new ArrayList<RowFilter<DefaultTableModel, Object>>();
        if (groupChatName.length() > 0) {
            RowFilter<DefaultTableModel, Object> groupChatNameFilter = RowFilter.regexFilter("(?i)" + groupChatName, 0);
            filters.add(groupChatNameFilter);
        }
        RowFilter<DefaultTableModel, Object> compoundRowFilter = RowFilter.andFilter(filters);
        rowSorter.setRowFilter(compoundRowFilter);
    }
}
