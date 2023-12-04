package Client.Admin.Views.Components;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;
import java.awt.*;
import com.github.lgooddatepicker.components.DateTimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;

public class ActiveUserList extends UserList {

    public ActiveUserList() {
        super();
        remove(searchBar);
        datePickerContainer.getParent().remove(datePickerContainer);

        appOpensSearch.getParent().remove(appOpensSearch);
        // Modify the columns array to include only the desired columns
        String[] columns = { "Username", "App Opens", "Chats with People", "Group Chats" };
        // Define the data for the active users
        Object[][] data = {
                { "TuanTu", "158", "15", "4" },
        };
        // Create a new DefaultTableModel instance with the modified columns and data
        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // All cells are not editable
            }
        };
        // Set the model for the table component
        table.setModel(model);
        // Adjust the preferred width of each column
        table.getColumnModel().getColumn(0).setPreferredWidth(100); // "Username"
        table.getColumnModel().getColumn(1).setPreferredWidth(100); // "App Opens"
        table.getColumnModel().getColumn(2).setPreferredWidth(120); // "Chats with People"
        table.getColumnModel().getColumn(3).setPreferredWidth(120); // "Group Chats"


        JPanel userListContainer = new JPanel();
        remove(userListPanel);
        userListContainer.add(userListPanel);
        add(userListContainer, BorderLayout.CENTER);

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

        appOpensSearch.add(new JLabel("App opens:"));
        appOpensSearch.add(filter);
        appOpensSearch.add(appOpenInput);

        orderListPanel.add(appOpensSearch);
        appOpensSearch.setBackground(Color.white);

        // add time picker
        JPanel timePickerPanel = new JPanel();
        JLabel startLabel = new JLabel("Start Datetime:");
        JLabel endLabel = new JLabel("End Datetime:");
        // Create a DateTimePicker with default settings.
        DateTimePicker startPicker = new DateTimePicker();
        // Create a DateTimePicker with custom settings.
        DateTimePicker endPicker = new DateTimePicker();
        TimePickerSettings timeSettings = new TimePickerSettings();
        timeSettings.setDisplaySpinnerButtons(true);
        timeSettings.setDisplayToggleTimeMenuButton(false);
//        endPicker.setTimePickerSettings(timeSettings);
        // Add the components to the panel.
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
        userListPanel.add(filterContainer, BorderLayout.NORTH);

    }

    protected void removeDatePickerContainer(){

    }
}
