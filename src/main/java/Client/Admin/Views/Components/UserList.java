package Client.Admin.Views.Components;

import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class UserList extends JPanel {
    // search button
    public JButton[] searchButtons = new JButton[4];
    
    public UserList() {
        setLayout(new BorderLayout());
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
        JComboBox<String> comboBox = new JComboBox<>(new String[] { "Online", "Absent", "Offline" });
        panel3.add(label3, BorderLayout.NORTH);
        panel3.add(comboBox, BorderLayout.CENTER);

        searchButtons[0] = new JButton("Search");
        searchButtons[1] = new JButton("Login history");
        searchButtons[2] = new JButton("Friend list");
        searchButtons[3] = new JButton("Lock account");

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
        orderListPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JComboBox<String> orderList = new JComboBox<>(new String[] { "Sort by name", "Sort by created time" });
        orderList.setMaximumSize(orderList.getPreferredSize()); // This will make the JComboBox not stretch

//        orderListPanel.add(Box.createHorizontalGlue()); // This will push the JComboBox to the right

        // date picker for new registration find
        JPanel datePickerContainer = new JPanel();

        for (int i = 0; i < 2; ++i){
            JPanel datePanel = new JPanel();

            JXDatePicker picker = new JXDatePicker();
            picker.setDate(Calendar.getInstance().getTime());
            picker.setFormats(new SimpleDateFormat("dd.MM.yyyy"));

            datePanel.add(picker);

            datePickerContainer.add(datePanel);

            if (i == 0){
                JLabel toText = new JLabel("to");
                datePickerContainer.add(toText);
            }
            datePanel.setBackground(Color.white);
            datePanel.setOpaque(true);

        }
        datePickerContainer.setBackground(Color.white);

        JPanel appOpensSearch = new JPanel();
        JComboBox<String> filter = new JComboBox<>(new String[] { "=", "<", ">" });
        JTextField appOpenInput = new JTextField(5);

        // Apply a document filter to accept only numeric input
        ((PlainDocument) appOpenInput.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                    throws BadLocationException {
                if (string.matches("[0-9]+")) { // Only allow numeric characters
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                if (text.matches("[0-9]+")) { // Only allow numeric characters
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });

        appOpensSearch.add(new JLabel("Number of direct friends:"));
        appOpensSearch.add(filter);
        appOpensSearch.add(appOpenInput);
        appOpensSearch.setBackground(Color.white);

        orderListPanel.add(appOpensSearch);

        orderListPanel.add(datePickerContainer);
        orderListPanel.add(orderList);
        orderListPanel.setBackground(Color.white);

        // Add the order list panel to the user list part
        userListPanel.add(orderListPanel, BorderLayout.NORTH);

        // Add a user list to the user list part
        String[] columns = { "Username", "Name", "Address", "Day of birth", "Gender", "Email", "Number of direct friends", "Number of friends of friends", "Actions" };

        // Define the table data

        Object[][] data = {
                { "TuanTu", "Pham Tran Tuan Tu", "34 Nguyen Dinh Chieu, Q1, TP Ho Chi Minh", "05/01/2003", "Nam",
                        "pttuantu@gmail.com","5", "5", "Update, Delete" },


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
        userListPanel.setBackground(Color.white);

        // Set a custom renderer and editor for the last column
        table.getColumnModel().getColumn(8).setCellRenderer(new MultiButtonRenderer());
        // center element inside the number column's cell
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(7).setCellRenderer(centerRenderer);

        // Set the preferred width of each column
        table.getColumnModel().getColumn(0).setPreferredWidth(100); // "Tên Đăng Nhập"
        table.getColumnModel().getColumn(1).setPreferredWidth(120); // "Họ Tên"
        table.getColumnModel().getColumn(2).setPreferredWidth(250); // "Địa Chỉ"
        table.getColumnModel().getColumn(3).setPreferredWidth(80); // "Ngày Sinh"
        table.getColumnModel().getColumn(4).setPreferredWidth(60); // "Giới Tính"
        table.getColumnModel().getColumn(5).setPreferredWidth(150); // "Email"
        table.getColumnModel().getColumn(6).setPreferredWidth(138); // "Number of friends"
        table.getColumnModel().getColumn(7).setPreferredWidth(155); // "Number of friends of friend"
        table.getColumnModel().getColumn(8).setPreferredWidth(160); // "Actions"
//        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        table.setBackground(Color.white);
        table.setOpaque(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // This line makes the table horizontally scrollable
        table.getTableHeader().setResizingAllowed(false); // disable column resizing
        table.getTableHeader().setReorderingAllowed(false); // disable column reordering

        JScrollPane tableScrollPane = new JScrollPane(table);

        tableScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        tableScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);


        tableScrollPane.setBackground(Color.white);
        tableScrollPane.setOpaque(true);
        userListPanel.add(tableScrollPane, BorderLayout.CENTER);
        add(userListPanel, BorderLayout.CENTER);

        // Add a list selection listener to the table
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                    for (int i = 1; i < 4; i++) {
                        searchButtons[i].setVisible(true);
                    }
                // } else {
                //     // No row is selected, hide the button
                //     for (int i = 1; i < 4; i++) {
                //         searchButtons[i].setVisible(false);
                //     }
                }
            }
        });
    }

}

