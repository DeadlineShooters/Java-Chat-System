package Client.Admin.Views.Components;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import Client.Models.UserActivity;
import com.github.lgooddatepicker.components.DateTimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;

public class ActiveUserList extends UserList {
    private DateTimePicker startPicker;
    private DateTimePicker endPicker;
    private Timestamp[] previousDatetimes = new Timestamp[2];
    private JTextField usernameTextField;



    public ActiveUserList() {
        remove(searchBar);
        remove(userListPanel);
        buttonPanel.getParent().remove(buttonPanel);
        orderListRightPanel.removeAll();


        String[] columns = {"Username", "App Opens", "Number of people chatted", "Number of groups chatted", "Date created"};
        initTable(columns);

        // App Opens Search
        JPanel appOpensSearch = createNumericSearchPanel("App opens:", 1);
        orderListRightPanel.add(appOpensSearch);

        // People Chatted Search
        JPanel peopleChattedSearch = createNumericSearchPanel("Number of people chatted:", 2);
        orderListRightPanel.add(peopleChattedSearch);

        // Groups Chatted Search
        JPanel groupsChattedSearch = createNumericSearchPanel("Number of groups chatted:", 3);
        orderListRightPanel.add(groupsChattedSearch);

        JPanel usernamePanel = createTextInputPanel("Username:");
        orderListRightPanel.add(usernamePanel);


        appOpensSearch.setBackground(Color.white);
        peopleChattedSearch.setBackground(Color.white);
        groupsChattedSearch.setBackground(Color.white);

        // add time picker
        JPanel timePickerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JLabel startLabel = new JLabel("Start Datetime:");
        JLabel endLabel = new JLabel("End Datetime:");
        // Create a DateTimePicker with default settings.
        startPicker = new DateTimePicker();
        // Create a DateTimePicker with custom settings.
        endPicker = new DateTimePicker();
        TimePickerSettings timeSettings = new TimePickerSettings();
        timeSettings.setDisplaySpinnerButtons(true);
        timeSettings.setDisplayToggleTimeMenuButton(false);
//        endPicker.setTimePickerSettings(timeSettings);
        // Add the components to the panel.
        timePickerPanel.add(new JLabel("Active from"));
        timePickerPanel.add(startLabel);
        timePickerPanel.add(startPicker);
        timePickerPanel.add(endLabel);
        timePickerPanel.add(endPicker);
        // Add the panel to the frame.
        orderListPanel.getParent().remove(orderListPanel);
        JPanel filterContainer = new JPanel(new GridLayout(2, 1));
        filterContainer.add(timePickerPanel);
        filterContainer.add(orderListPanel);


        timePickerPanel.setBackground(Color.white);

        // Add an EmptyBorder with top padding (20 pixels in this example)
        filterContainer.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        filterContainer.setBackground(Color.white);

        userListPanel.add(filterContainer, BorderLayout.NORTH);
        add(userListPanel, BorderLayout.NORTH);

        previousDatetimes[0] = null;
        previousDatetimes[1] = null;



        initActionListeners();

    }
    private JPanel createTextInputPanel(String label) {
        JPanel panel = new JPanel();
        usernameTextField = new JTextField(16);

        panel.add(new JLabel(label));
        panel.add(usernameTextField);
        panel.setBackground(Color.white);

        return panel;
    }

    private JPanel createNumericSearchPanel(String label, int column) {
        JPanel panel = new JPanel();
        JComboBox<String> filter = new JComboBox<>(new String[]{"=", "<", ">"});
        JTextField input = new JTextField(5);

        // Apply a document filter to accept only numeric input
        ((PlainDocument) input.getDocument()).setDocumentFilter(new DocumentFilter() {
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

        panel.add(new JLabel(label));
        panel.add(filter);
        panel.add(input);
        panel.setBackground(Color.white);

        // Add an action listener to the input field
        input.addActionListener(e -> {
            String selectedItem = (String) filter.getSelectedItem();
            if (!input.getText().isEmpty()) {
                int filterValue = Integer.parseInt(input.getText());
                RowFilter<Object, Object> rowFilter = null;
                switch (selectedItem) {
                    case "=":
                        rowFilter = RowFilter.numberFilter(RowFilter.ComparisonType.EQUAL, filterValue, column);
                        break;
                    case "<":
                        rowFilter = RowFilter.numberFilter(RowFilter.ComparisonType.BEFORE, filterValue, column);
                        break;
                    case ">":
                        rowFilter = RowFilter.numberFilter(RowFilter.ComparisonType.AFTER, filterValue, column);
                        break;
                }
                rowSorter.setRowFilter(rowFilter);
            } else {
                rowSorter.setRowFilter(null); // Show all lines when the input field is empty
            }
        });

        return panel;
    }

    @Override
    protected void initTable(String[] columns) {
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // All cells are not editable
            }
        };

        table = new JTable(model);

        rowSorter = new TableRowSorter<>(model);
        table.setRowSorter(rowSorter);

        // Adjust the preferred width of each column
        table.getColumnModel().getColumn(0).setPreferredWidth(100); // "Username"
        table.getColumnModel().getColumn(1).setPreferredWidth(100); // "App Opens"
        table.getColumnModel().getColumn(2).setPreferredWidth(165); // "Chats with People"
        table.getColumnModel().getColumn(3).setPreferredWidth(165); // "Group Chats"

        // Add the table to the panel
        JPanel userListContainer = new JPanel(new BorderLayout()); // Use BorderLayout here
        userListContainer.add(new JScrollPane(table), BorderLayout.CENTER);

        add(userListContainer, BorderLayout.CENTER); // Add userListContainer to BorderLayout.CENTER
    }


    public void updateTableActivity(ArrayList<UserActivity> activities) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        for (UserActivity activity : activities) {
            Object[] row = new Object[5]; // 4 columns specific to ActiveUserList

            // Populate columns for ActiveUserList
            row[0] = activity.username();
            row[1] = activity.app_opens();
            row[2] = activity.users_chat_count();
            row[3] = activity.groups_chat_count();
            row[4] = userRepository.getCreatedDate(activity.username());

            model.addRow(row);
        }

        table.setModel(model);
    }


    private void initActionListeners() {


        ArrayList<UserActivity> activities;

        // on start, datetime pickers are empty so display all user activities
        activities = sessionRepository.getUsersActivity(userRepository.getUsers());
        updateTableActivity(activities);


        onChangeDateTimePicker(startPicker, "start");
        onChangeDateTimePicker(endPicker, "end");

        usernameTextField.addActionListener(e -> {
            String username = usernameTextField.getText().trim();
            if (!username.isEmpty()) {
                // Apply the username filter to the table
                RowFilter<Object, Object> usernameFilter = RowFilter.regexFilter("(?i)" + username, 0);
                rowSorter.setRowFilter(usernameFilter);
            } else {
                // If the username is empty, show all rows
                rowSorter.setRowFilter(null);
            }
        });
    }

    private void onChangeDateTimePicker(DateTimePicker picker, String choice) {
        picker.addDateTimeChangeListener(e -> {
            LocalDateTime startDateTime = startPicker.getDateTimeStrict();
            LocalDateTime endDateTime = endPicker.getDateTimeStrict();


            Timestamp startTime = startDateTime != null ? Timestamp.valueOf(startDateTime) : null;
            Timestamp endTime = endDateTime != null ? Timestamp.valueOf(endDateTime) : null;
            ArrayList<UserActivity> activitiesTemp;

            System.out.println(startTime + " " + endTime);
            if (startTime == null || endTime == null) {

                activitiesTemp = sessionRepository.getUsersActivity(userRepository.getUsers());
            }
            else if (endDateTime.isBefore(startDateTime)) {

                // Reset endPicker to the previous choice
                if (choice == "start"){
                    if (previousDatetimes[0] == null){
                        startPicker.clear();
                    } else {
                        startPicker.setDateTimePermissive(previousDatetimes[0].toLocalDateTime());
                    }
                } else {
                    if (previousDatetimes[1] == null){
                        endPicker.clear();
                    } else {
                        endPicker.setDateTimePermissive(previousDatetimes[1].toLocalDateTime());
                    }
                }
                JOptionPane.showMessageDialog(null, "End datetime cannot be before start datetime.");
                return;
            }
            else {

                activitiesTemp = sessionRepository.getUsersActivity(startTime, endTime);
                for (UserActivity activity : activitiesTemp){
                    System.out.println(activity.username());
                }
            }


            updateTableActivity(activitiesTemp);
        });
    }

}
