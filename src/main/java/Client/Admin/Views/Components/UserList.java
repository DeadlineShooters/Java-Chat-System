package Client.Admin.Views.Components;

import Client.Models.User;
import Client.Admin.Repository.UserRepository;
import org.jdesktop.swingx.JXDatePicker;

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
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

public class UserList extends JPanel {
    protected JPanel searchBar = new JPanel();
    protected JPanel userListPanel = new JPanel(new BorderLayout());
    protected JPanel orderListPanel = new JPanel();
    protected JPanel appOpensSearch = new JPanel();
    protected JPanel datePickerContainer = new JPanel();
    protected UserRepository userRepository = new UserRepository();
    protected ArrayList<RowFilter<Object,Object>> filters = new ArrayList<RowFilter<Object,Object>>();
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
        JComboBox<String> comboBox = new JComboBox<>(new String[]{"Online", "Absent", "Offline"});
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
        orderListPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        JComboBox<String> orderList = new JComboBox<>(new String[]{"Sort by name", "Sort by created time"});
        orderList.setMaximumSize(orderList.getPreferredSize()); // This will make the JComboBox not stretch

//        orderListPanel.add(Box.createHorizontalGlue()); // This will push the JComboBox to the right

        // date picker for new registration find
        JXDatePicker[] pickers = new JXDatePicker[2];
        Date[] previousDates = new Date[2];

        datePickerContainer.add(new JLabel("Created date: "));

        for (int i = 0; i < 2; ++i) {
            JPanel datePanel = new JPanel();

            JXDatePicker picker = new JXDatePicker();
            picker.setDate(Calendar.getInstance().getTime());
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
        previousDates[1] = new Date(pickers[1].getDate().getTime());  // Current date of the second picker

        datePickerContainer.setBackground(Color.white);

        JComboBox<String> filter = new JComboBox<>(new String[]{"=", "<", ">"});
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
        String[] columns = {"Username", "Name", "Address", "Day of birth", "Gender", "Email", "<html>Number of <br>direct friends</html>", "Total friends", "Created time", "Actions"};

        initTable(columns);
        java.util.Date firstUtilDate = pickers[0].getDate();
        java.util.Date secondUtilDate = pickers[1].getDate();
        java.sql.Date firstDate = new java.sql.Date(firstUtilDate.getTime());
        java.sql.Date secondDate = new java.sql.Date(secondUtilDate.getTime());

        ArrayList<User> users = userRepository.getUsersByDateRange(firstDate, secondDate);

        // Update your table with the new list of users
        updateTable(users);
        userListPanel.setBackground(Color.white);

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
            String name = textField1.getText().trim();
            searchByName(name);
        });

        // Inside the constructor after initializing the filter JComboBox and appOpenInput JTextField
        appOpenInput.addActionListener(e -> {
            String selectedItem = (String) filter.getSelectedItem();
            if (!appOpenInput.getText().isEmpty()) {
                int friendsFilterValue = Integer.parseInt(appOpenInput.getText());
                RowFilter<Object, Object> rowFilter = null;
                switch (selectedItem) {
                    case "=":
                        rowFilter = RowFilter.numberFilter(RowFilter.ComparisonType.EQUAL, friendsFilterValue, 6); // Assuming direct friends column index is 7
                        break;
                    case "<":
                        rowFilter = RowFilter.numberFilter(RowFilter.ComparisonType.BEFORE, friendsFilterValue, 6); // Assuming direct friends column index is 7
                        break;
                    case ">":
                        rowFilter = RowFilter.numberFilter(RowFilter.ComparisonType.AFTER, friendsFilterValue, 6); // Assuming direct friends column index is 7
                        break;
                }
                rowSorter.setRowFilter(rowFilter);
            } else {
                rowSorter.setRowFilter(null); // Show all lines when the input field is empty
            }
        });



    }

    protected void initTable(String[] columns){
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
        headerHeight *= 2; // Change this to the factor you want

// Set the new preferred height
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, headerHeight));

        // Set a custom renderer and editor for the last column
        table.getColumnModel().getColumn(9).setCellRenderer(new MultiButtonRenderer());
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
        table.getColumnModel().getColumn(6).setPreferredWidth(100); // "Number of friends"
        table.getColumnModel().getColumn(7).setPreferredWidth(90); // "Number of direct friends"
        table.getColumnModel().getColumn(8).setPreferredWidth(125); // "Number of friends of friend"
        table.getColumnModel().getColumn(9).setPreferredWidth(160); // "Actions"
//        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        table.setBackground(Color.white);
        table.setOpaque(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // This line makes the table horizontally scrollable
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
            row[8] = user.createdAt();
            row[9] = "Update, Delete"; // Actions

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

    public void searchByName(String name) {
        if (name.trim().length() == 0) {
            // If the search field is empty, reset the row filter
            rowSorter.setRowFilter(null);
        } else {
            // Perform name search using RowFilter
            RowFilter<DefaultTableModel, Object> rf = RowFilter.regexFilter("(?i)" + name, 0); // search for username
            rowSorter.setRowFilter(rf);
        }
    }


}

