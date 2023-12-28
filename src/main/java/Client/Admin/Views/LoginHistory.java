package Client.Admin.Views;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.*;

import Client.Models.Session;
import Client.Admin.Repository.SessionRepository;

public class LoginHistory extends JPanel {
    protected JTable table;
    protected TableRowSorter<DefaultTableModel> rowSorter;
    protected SessionRepository sessionRepository = new SessionRepository();
    public String username;
    public JButton returnButton = new JButton("Return");

    public LoginHistory() {
        setLayout(new BorderLayout());
        setBackground(Color.white);

        // Add the return button to the top left corner
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(returnButton, BorderLayout.WEST);
        add(topPanel, BorderLayout.NORTH);

        JPanel userListPanel = new JPanel(new BorderLayout());

        String[] columns = { "Login time", "Logout time" };
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);

        table.getColumnModel().getColumn(0).setPreferredWidth(100); // "Thời gian đăng nhập"
        table.getColumnModel().getColumn(1).setPreferredWidth(100); // "Thời gian đăng xuất"
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        rowSorter = new TableRowSorter<>(model);
        table.setRowSorter(rowSorter);

        updateTable();

        userListPanel.add(new JScrollPane(table), BorderLayout.CENTER);

        add(userListPanel, BorderLayout.CENTER);
    }

    public void updateTable() {
        ArrayList<Session> sessions = sessionRepository.getSession(username);
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        for (Session session : sessions) {
            Object[] row = new Object[2];
            row[0] = session.loginTime();
            row[1] = session.logoutTime();
            model.addRow(row);
        }
        table.setModel(model);
    }
}
