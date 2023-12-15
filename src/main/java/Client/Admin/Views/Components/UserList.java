package Client.Admin.Views.Components;

import Client.Models.User;
import Client.Admin.Repository.UserRepository;
import org.jdesktop.swingx.JXDatePicker;
import org.w3c.dom.events.MouseEvent;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Vector;
import java.util.ArrayList;
import java.util.Calendar;

public class UserList extends JPanel {
    protected JPanel searchBar = new JPanel();
    protected JPanel userListPanel = new JPanel(new BorderLayout());
    protected JPanel orderListPanel = new JPanel();
    protected JPanel appOpensSearch = new JPanel();
    protected JPanel datePickerContainer = new JPanel();
    protected UserRepository userRepository = new UserRepository();
    protected ArrayList<RowFilter<Object, Object>> filters = new ArrayList<RowFilter<Object, Object>>();
    protected TableRowSorter<DefaultTableModel> rowSorter;

    protected JTable table;
    // search button
    public JButton[] searchButtons = new JButton[4];

    public UserList() {
        setLayout(new BorderLayout());
        // Add a search bar to the body part

        searchBar.setBackground(Color.white); // Change to the color you want

        // Set the border
        searchBar.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
        searchBar.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Label and text field 1
        JPanel panel1 = new JPanel(new BorderLayout());
        JLabel label1 = new JLabel("Username");
        label1.setBackground(Color.white);
        label1.setOpaque(true);
        JTextField textField1 = new JTextField(16);
        panel1.add(label1, BorderLayout.NORTH);
        panel1.add(textField1, BorderLayout.CENTER);

        // Label and text field 2
        JPanel panel2 = new JPanel(new BorderLayout());
        JLabel label2 = new JLabel("Full name");
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
        JComboBox<String> comboBox = new JComboBox<>(new String[] { "All", "Online", "Absent", "Offline" });
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

        orderListPanel.setLayout(new BoxLayout(orderListPanel, BoxLayout.X_AXIS));
        orderListPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        // Create Update and Delete buttons
        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");

        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.add(addButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(updateButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(deleteButton);
        buttonPanel.setBackground(Color.white);

        // Add an order list to the top right of the user list part
        JPanel orderListRightPanel = new JPanel();
        orderListRightPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JComboBox<String> orderList = new JComboBox<>(new String[] { "Sort by name", "Sort by created time" });
        orderList.setMaximumSize(orderList.getPreferredSize()); // This will make the JComboBox not stretch

        // orderListPanel.add(Box.createHorizontalGlue()); // This will push the
        // JComboBox to the right

        // date picker for new registration find
        JXDatePicker[] pickers = new JXDatePicker[2];
        Date[] previousDates = new Date[2];

        datePickerContainer.add(new JLabel("Created date: "));

        for (int i = 0; i < 2; ++i) {
            JPanel datePanel = new JPanel();

            JXDatePicker picker = new JXDatePicker();
            
            picker.setDate(new java.util.Date());
            picker.setFormats(new SimpleDateFormat("dd.MM.yyyy"));

            datePanel.add(picker);
            datePickerContainer.add(datePanel);

            if (i == 0) {
                JLabel toText = new JLabel("to");
                datePickerContainer.add(toText);
            }
            datePanel.setBackground(Color.white);
            datePanel.setOpaque(true);

            pickers[i] = picker;
            previousDates[i] = new java.sql.Date(picker.getDate().getTime());
        }

        // Add a property change listener to each date picker
        previousDates[0] = userRepository.getOldestDate();
        for (int i = 0; i < 2; ++i) {
            final int index = i;
            pickers[i].addActionListener(e -> {
                java.util.Date firstUtilDate = pickers[0].getDate();
                java.util.Date secondUtilDate = pickers[1].getDate();

                java.sql.Date firstDate = new java.sql.Date(firstUtilDate.getTime());
                java.sql.Date secondDate = new java.sql.Date(secondUtilDate.getTime());

                if (secondDate.before(firstDate)) {
                    pickers[index].setDate(previousDates[index]);
                } else {
                    previousDates[index] = new java.sql.Date(pickers[index].getDate().getTime());
                    // Call getUsersByDateRange() with the new date range
                    ArrayList<User> users = userRepository.getUsersByDateRange(firstDate, secondDate);

                    // Update your table with the new list of users
                    updateTable(users);
                }

            });
        }

        // set the first date picker to have the oldest date
        pickers[0].setDate(userRepository.getOldestDate());

        previousDates[0] = new Date(pickers[0].getDate().getTime()); // Current date of the first picker
        previousDates[1] = new Date(pickers[1].getDate().getTime()); // Current date of the second picker

        datePickerContainer.setBackground(Color.white);

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

        orderListRightPanel.add(appOpensSearch);
        orderListRightPanel.add(datePickerContainer);
        orderListRightPanel.add(orderList);
        orderListRightPanel.setBackground(Color.white);

        orderListPanel.add(buttonPanel);
        orderListPanel.add(orderListRightPanel);
        orderListPanel.setBackground(Color.white);

        // Add the order list panel to the user list part
        userListPanel.add(orderListPanel, BorderLayout.NORTH);

        // Add a user list to the user list part
        String[] columns = { "Username", "Full name", "Address", "Day of birth", "Gender", "Email",
                "<html><center>Number<br>of direct<br>friends", "<html><center>Number<br>of friends<br>of friends",
                "Status",
                "Created at" };

        // Create a new DefaultTableModel instance
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // All cells are editable
            }
        };

        table = new JTable(model);

        // Get the table header
        JTableHeader header = table.getTableHeader();

        // Get the existing height
        int headerHeight = header.getPreferredSize().height;

        // Increase the height
        headerHeight *= 3; // Change this to the factor you want

        // Set the new preferred height
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, headerHeight));

        java.util.Date firstUtilDate = pickers[0].getDate();
        java.util.Date secondUtilDate = pickers[1].getDate();
        java.sql.Date firstDate = new java.sql.Date(firstUtilDate.getTime());
        java.sql.Date secondDate = new java.sql.Date(secondUtilDate.getTime());

        ArrayList<User> users = userRepository.getUsersByDateRange(firstDate, secondDate);

        // Update your table with the new list of users
        updateTable(users);
        userListPanel.setBackground(Color.white);

        // Set a custom renderer and editor for the last column
        // center element inside the number column's cell
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(7).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(8).setCellRenderer(centerRenderer);

        // Set the preferred width of each column
        table.getColumnModel().getColumn(0).setPreferredWidth(100); // "Tên Đăng Nhập"
        table.getColumnModel().getColumn(1).setPreferredWidth(100); // "Họ Tên"
        table.getColumnModel().getColumn(2).setPreferredWidth(210); // "Địa Chỉ"
        table.getColumnModel().getColumn(3).setPreferredWidth(75); // "Ngày Sinh"
        table.getColumnModel().getColumn(4).setPreferredWidth(50); // "Giới Tính"
        table.getColumnModel().getColumn(5).setPreferredWidth(150); // "Email"
        table.getColumnModel().getColumn(6).setPreferredWidth(65); // "Number of friends"
        table.getColumnModel().getColumn(7).setPreferredWidth(65); // "Number of friends of friend"
        table.getColumnModel().getColumn(8).setPreferredWidth(55); // "Status"
        table.getColumnModel().getColumn(9).setPreferredWidth(130); // "Created at"
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        table.setBackground(Color.white);
        table.setOpaque(true);
        // table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // This line makes the table
        // horizontally scrollable
        table.getTableHeader().setResizingAllowed(false); // disable column resizing
        table.getTableHeader().setReorderingAllowed(false); // disable column reordering

        // set sorter for table
        rowSorter = new TableRowSorter<>(model);
        table.setRowSorter(rowSorter);

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
                }
            }
        });

        orderList.addActionListener(e -> {
            String selectedSort = (String) orderList.getSelectedItem();
            if ("Sort by name".equals(selectedSort)) {
                sortByName();
            } else if ("Sort by created time".equals(selectedSort)) {
                sortByCreatedTime();
            }
        });

        searchButtons[0].addActionListener(e -> {
            String username = textField1.getText().trim();
            String fullName = textField2.getText().trim();
            String status = comboBox.getSelectedItem().toString();
            search(username, fullName, status);
        });
        // Inside the constructor after initializing the filter JComboBox and
        // appOpenInput JTextField
        appOpenInput.addActionListener(e -> {
            String selectedItem = (String) filter.getSelectedItem();
            if (!appOpenInput.getText().isEmpty()) {
                int friendsFilterValue = Integer.parseInt(appOpenInput.getText());
                RowFilter<Object, Object> rowFilter = null;
                switch (selectedItem) {
                    case "=":
                        rowFilter = RowFilter.numberFilter(RowFilter.ComparisonType.EQUAL, friendsFilterValue, 6);
                        break;
                    case "<":
                        rowFilter = RowFilter.numberFilter(RowFilter.ComparisonType.BEFORE, friendsFilterValue, 6);
                        break;
                    case ">":
                        rowFilter = RowFilter.numberFilter(RowFilter.ComparisonType.AFTER, friendsFilterValue, 6);
                        break;
                }
                rowSorter.setRowFilter(rowFilter);
            } else {
                rowSorter.setRowFilter(null); // Show all lines when the input field is empty
            }
        });

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AddFrame addFrame = new AddFrame();
                addFrame.submitButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String username = addFrame.textFields[0].getText();
                        String password = addFrame.textFields[1].getText();
                        String email = addFrame.textFields[2].getText();
                        Timestamp createdAt = new Timestamp(System.currentTimeMillis());
                        userRepository.insert(username, password, email, createdAt);
                        addFrame.dispose();
                        updateTable(userRepository.getUsersByDateRange(firstDate,
                                new java.sql.Date((new java.util.Date()).getTime())));
                        pickers[1].setDate(new java.util.Date());
                    }
                });
            }
        });

        // update list
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (table.getSelectedRow() != -1) {
                    int row = table.getSelectedRow();
                    String username = (String) table.getValueAt(row, 0);
                    String fullName = (String) table.getValueAt(row, 1);
                    String address = (String) table.getValueAt(row, 2);
                    Date dob = (Date) table.getValueAt(row, 3);
                    String gender = (String) table.getValueAt(row, 4);
                    String email = (String) table.getValueAt(row, 5);
                    UpdateFrame updateFrame = new UpdateFrame(username, fullName, address, dob, gender, email);
                    updateFrame.submitButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            String username = updateFrame.textFields[0].getText();
                            String fullName = updateFrame.textFields[1].getText();
                            String address = updateFrame.textFields[2].getText();
                            String gender = updateFrame.textFields[3].getText();
                            String email = updateFrame.textFields[4].getText();
                            Timestamp dob = new Timestamp(updateFrame.datePicker.getDate().getTime());
                            userRepository.update(username, fullName, address, dob, gender, email);
                            updateFrame.dispose();
                            updateTable(userRepository.getUsersByDateRange(firstDate,
                                    new java.sql.Date((new java.util.Date()).getTime())));
                            pickers[1].setDate(new java.util.Date());
                        }
                    });
                }
            }
        });

        // remove user account
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (table.getSelectedRow() != -1) {
                    int row = table.getSelectedRow();
                    String username = (String) table.getValueAt(row, 0);

                    userRepository.remove(username);
                    updateTable(userRepository.getUsersByDateRange(firstDate, secondDate));
                }
            }
        });

    }

    public JPanel getOrderListPanel() {
        return orderListPanel;
    }

    public void updateTable(ArrayList<User> users) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        for (User user : users) {
            Object[] row = new Object[10];
            row[0] = user.username();
            row[1] = user.name();
            row[2] = user.address();
            row[3] = user.dob();
            row[4] = user.gender();
            row[5] = user.email();
            // Fetch the number of friends for the user
            int numberOfFriends = userRepository.fetchNumberOfFriends(user.username());
            row[6] = numberOfFriends;

            // Fetch the number of friends of friends for the user
            int numberOfFriendsOfFriends = userRepository.fetchNumberOfFriendsOfFriends(user.username());
            row[7] = numberOfFriendsOfFriends + numberOfFriends;
            row[8] = user.status();
            row[9] = user.createdAt();

            model.addRow(row);
        }

        // Update the table model
        table.setModel(model);
    }

    // Sort the table data by name
    public void sortByName() {
        ArrayList<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING)); // sort based on username
        rowSorter.setSortKeys(sortKeys);
        rowSorter.sort();
    }

    // Sort the table data by created time
    public void sortByCreatedTime() {
        ArrayList<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(8, SortOrder.ASCENDING));
        rowSorter.setSortKeys(sortKeys);
        rowSorter.sort();
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
            RowFilter<DefaultTableModel, Object> statusFilter = RowFilter.regexFilter("(?i)" + status, 8);
            filters.add(statusFilter);
        }

        RowFilter<DefaultTableModel, Object> compoundRowFilter = RowFilter.andFilter(filters);
        rowSorter.setRowFilter(compoundRowFilter);
    }

}
