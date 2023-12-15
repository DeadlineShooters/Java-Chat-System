package Client.Admin.Views.Components;

import javax.swing.*;

import org.jdesktop.swingx.JXDatePicker;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import Client.Admin.Repository.UserRepository;

public class UpdateFrame extends JFrame {
    protected UserRepository userRepository = new UserRepository();
    public JTextField[] textFields = new JTextField[5];
    JXDatePicker datePicker = new JXDatePicker();
    public JButton submitButton = new JButton("Submit");

    UpdateFrame(String username, String fullName, String address, Date dob, String gender, String email) {
        String[] texts = { username, fullName, address, gender, email };
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        JLabel[] labels = new JLabel[6];
        labels[0] = new JLabel("Username: ");
        labels[1] = new JLabel("Full name: ");
        labels[2] = new JLabel("Address: ");
        labels[3] = new JLabel("Gender: ");
        labels[4] = new JLabel("Email: ");
        labels[5] = new JLabel("Date of birth: ");
        JPanel[] panels = new JPanel[6];
        for (int i = 0; i < 6; i++) {
            panels[i] = new JPanel();
            panels[i].setLayout(new FlowLayout(FlowLayout.LEFT));
            labels[i].setPreferredSize(new Dimension(80, 20));
            panels[i].add(labels[i]);
            if (i != 5) {
                textFields[i] = new JTextField(texts[i]);
                textFields[i].setPreferredSize(new Dimension(200, 20));
                panels[i].add(textFields[i]);
            } else {
                datePicker.setFormats(new SimpleDateFormat("dd.MM.yyyy"));
                datePicker.setDate(dob);
                panels[i].setLayout(new FlowLayout(FlowLayout.LEFT));
                panels[i].add(datePicker);
            }
            add(panels[i]);
        }
        textFields[0].setEditable(false);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        buttonPanel.add(submitButton);

        add(buttonPanel);
        // getRootPane().setDefaultButton(submitButton);
        pack();
        setLocationRelativeTo(null); // Center the frame
        setVisible(true);
    }
}
