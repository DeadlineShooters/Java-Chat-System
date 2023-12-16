package Client.Admin.Views;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.*;

import Client.Models.Session;
import Client.Admin.Repository.SessionRepository;
import Client.Admin.Repository.UserRepository;

public class LoginList extends JPanel {
    protected JTable table;
    protected TableRowSorter<DefaultTableModel> rowSorter;
    protected UserRepository userRepository = new UserRepository();
    protected SessionRepository sessionRepository = new SessionRepository();

    public LoginList() {
        setLayout(new BorderLayout());
        setBackground(Color.white);

        // Add an order list to the top right of the user list part
        JPanel userListPanel = new JPanel(new BorderLayout());

        // Add a user list to the user list part
        String[] columns = { "Login time", "Username", "Name" };

        // Create a new DefaultTableModel instance
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // All cells are editable
            }
        };

        table = new JTable(model);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(0).setPreferredWidth(100); // "Thời gian"
        table.getColumnModel().getColumn(1).setPreferredWidth(100); // "Tên Đăng Nhập"
        table.getColumnModel().getColumn(2).setPreferredWidth(100); // "Họ Tên"
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        rowSorter = new TableRowSorter<>(model);
        table.setRowSorter(rowSorter);

        updateTable();

        userListPanel.add(new JScrollPane(table), BorderLayout.CENTER);

        // Add the user list part to the body part
        add(userListPanel, BorderLayout.CENTER);
    }

    public void updateTable() {
        ArrayList<Session> sessions = sessionRepository.getSessions();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        for (Session session : sessions) {
            Object[] row = new Object[3];
            row[0] = session.loginTime();
            row[1] = session.username();
            row[2] = userRepository.getUser(session.username()).name(); 
            model.addRow(row);
        }
        table.setModel(model);
    }
}
