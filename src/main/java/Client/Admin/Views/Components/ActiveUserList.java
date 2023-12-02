package Client.Admin.Views.Components;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ActiveUserList extends UserList {

    public ActiveUserList() {
        super();
        remove(searchBar);
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
//        table.getColumnModel().getColumn(0).setPreferredWidth(100); // "Username"
//        table.getColumnModel().getColumn(1).setPreferredWidth(120); // "Name"
//        table.getColumnModel().getColumn(2).setPreferredWidth(60); // "Status"
//        table.getColumnModel().getColumn(3).setPreferredWidth(140); // "Actions"
    }
}
