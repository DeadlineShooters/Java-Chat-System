package Client.Admin.Views;

import Client.Admin.Repository.SpamReportRepository;
import Client.Admin.Repository.UserRepository;
import Client.Admin.Views.Components.MultiButtonRenderer;
import Client.Models.SpamReport;
import Client.Models.User;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;

public class ReportList extends JPanel {
    protected JTable table;
    protected TableRowSorter<DefaultTableModel> rowSorter;
    protected UserRepository userRepository = new UserRepository();
    protected SpamReportRepository spamReportRepository = new SpamReportRepository();

    public ReportList() {
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
        JLabel label1 = new JLabel("Sender");
        label1.setBackground(Color.white);
        label1.setOpaque(true);
        JTextField textField1 = new JTextField(16);
        panel1.add(label1, BorderLayout.NORTH);
        panel1.add(textField1, BorderLayout.CENTER);

        // Label and text field 2
        JPanel panel2 = new JPanel(new BorderLayout());
        JLabel label2 = new JLabel("Reported user");
        label2.setBackground(Color.white);
        label2.setOpaque(true);
        JTextField textField2 = new JTextField(16);
        panel2.add(label2, BorderLayout.NORTH);
        panel2.add(textField2, BorderLayout.CENTER);

        // search button
        JButton[] searchButtons = new JButton[2];
        searchButtons[0] = new JButton("Search");
        searchButtons[1] = new JButton("Lock account");

        searchBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        searchBar.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        searchBar.add(panel1);
        searchBar.add(Box.createRigidArea(new Dimension(5, 0)));
        searchBar.add(panel2);
        searchBar.add(Box.createRigidArea(new Dimension(5, 0)));
        searchBar.add(Box.createRigidArea(new Dimension(10, 0)));
        searchBar.add(searchButtons[0]);

        searchBar.add(Box.createRigidArea(new Dimension(10, 0)));
        searchBar.add(searchButtons[1]);
        searchButtons[1].setVisible(false);

        add(searchBar, BorderLayout.NORTH);

        JPanel userListPanel = new JPanel(new BorderLayout());

        String[] columns = { "Sender", "Reported user", "Created at", "Lock status" };

        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // All cells are editable
            }
        };

        // Create a new JTable instance
        table = new JTable(model);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        // Set the preferred width of each column
        table.getColumnModel().getColumn(0).setPreferredWidth(100); // "Tên người gửi"
        table.getColumnModel().getColumn(1).setPreferredWidth(100); // "Tên người bị báo cáo"
        table.getColumnModel().getColumn(2).setPreferredWidth(100); // "Thời gian tạo"
        table.getColumnModel().getColumn(3).setMinWidth(0);
        table.getColumnModel().getColumn(3).setMaxWidth(0);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        rowSorter = new TableRowSorter<>(model);
        table.setRowSorter(rowSorter);

        updateTable();

        userListPanel.add(new JScrollPane(table), BorderLayout.CENTER);

        // Add the user list part to the body part
        add(userListPanel, BorderLayout.CENTER);

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                    searchButtons[1].setVisible(true);
                    if ((boolean) table.getValueAt(table.getSelectedRow(), 3)) {
                        searchButtons[1].setText("Unlock account");
                    } else {
                        searchButtons[1].setText("Lock account");
                    }
                }
            }
        });
        searchButtons[0].addActionListener(e -> {
            String sender = textField1.getText().trim();
            String reportedUser = textField2.getText().trim();
            search(sender, reportedUser);
        });

        searchButtons[1].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (table.getSelectedRow() != -1) {
                    int row = table.getSelectedRow();
                    String reportedUser = (String) table.getValueAt(row, 1);
                    boolean isLocked = (boolean) table.getValueAt(row, 3);
                    userRepository.lock(reportedUser, isLocked);
                    JFrame dialog = new JFrame();
                    JOptionPane.showMessageDialog(dialog, "Complete account " + (isLocked ? "unlock!" : "lock!"),
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    updateTable();

                }
            }
        });
    }

    public void updateTable() {
        ArrayList<SpamReport> spamReports = spamReportRepository.getSpamReports();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        for (SpamReport spamReport : spamReports) {
            Object[] row = new Object[4];
            row[0] = spamReport.sender();
            row[1] = spamReport.reportedUser();
            row[2] = spamReport.reportTime();
            row[3] = userRepository.getUser(spamReport.reportedUser()).isLocked();
            model.addRow(row);
        }

        table.setModel(model);
    }

    public void search(String sender, String reportedUser) {
        List<RowFilter<DefaultTableModel, Object>> filters = new ArrayList<RowFilter<DefaultTableModel, Object>>();
        if (sender.length() > 0) {
            RowFilter<DefaultTableModel, Object> usernameFilter = RowFilter.regexFilter("(?i)" + sender, 0);
            filters.add(usernameFilter);
        }
        if (reportedUser.length() > 0) {
            RowFilter<DefaultTableModel, Object> fullNameFilter = RowFilter.regexFilter("(?i)" + reportedUser, 1);
            filters.add(fullNameFilter);
        }
        RowFilter<DefaultTableModel, Object> compoundRowFilter = RowFilter.andFilter(filters);
        rowSorter.setRowFilter(compoundRowFilter);
    }
}
