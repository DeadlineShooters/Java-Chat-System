package Client.Admin.Views;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

        JPanel userListPanel = new JPanel(new BorderLayout());

        JPanel reloadPanel = new JPanel();
        reloadPanel.setBackground(Color.white);

        reloadPanel.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
        reloadPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        ImageIcon icon = new ImageIcon(getClass().getResource("/Image/reload.png"));
        Image img = icon.getImage().getScaledInstance(16, 16, java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(img);
        JButton reloadButton = new JButton(icon);
        reloadButton.setMargin(new Insets(2, 2, 2, 2));
        reloadPanel.add(reloadButton);

        add(reloadPanel, BorderLayout.NORTH);

        String[] columns = { "Login time", "Username", "Name" };
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
        ArrayList<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(0, SortOrder.DESCENDING)); // 0 is the index of "Login time"
        rowSorter.setSortKeys(sortKeys);
        rowSorter.sort();

        updateTable();

        userListPanel.add(new JScrollPane(table), BorderLayout.CENTER);

        // Add the user list part to the body part
        add(userListPanel, BorderLayout.CENTER);

        reloadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateTable();
            }
        });
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
