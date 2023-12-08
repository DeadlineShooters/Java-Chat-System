package Client.Admin.Views.Components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;

import Client.Admin.Repository.UserRepository;

public class InputFrame extends JFrame {
    protected UserRepository userRepository = new UserRepository();
    public JTextField[] textFields = new JTextField[3];
    public JButton submitButton = new JButton("Submit");

    InputFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        JLabel[] labels = new JLabel[3];
        labels[0] = new JLabel("Username: ");
        labels[1] = new JLabel("Full name: ");
        labels[2] = new JLabel("Email: ");
        JPanel[] panels = new JPanel[3];
        for (int i = 0; i < 3; i++) {
            panels[i] = new JPanel();
            labels[i].setPreferredSize(new Dimension(80, 20)); // Set the preferred size of the label
            textFields[i] = new JTextField();
            textFields[i].setPreferredSize(new Dimension(200, 20));
            panels[i].setLayout(new BoxLayout(panels[i], BoxLayout.X_AXIS));
            panels[i].add(labels[i]);
            panels[i].add(textFields[i]);
            panels[i].setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
            add(panels[i]);
        }
        
        add(submitButton);
        pack();
        setLocationRelativeTo(null); // Center the frame
        setVisible(true);
    }
}
