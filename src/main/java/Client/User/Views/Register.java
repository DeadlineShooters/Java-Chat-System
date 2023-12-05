package Client.User.Views;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Register extends JFrame {
    private JPanel contentPane;
    String[] labels = {"Username", "Email", "Password", "Confirm Password"};
    JTextField[] textFields = new JTextField[labels.length];
//    private ImageIcon iconTitle = new ImageIcon(HomeScreen.class.getResource("/Image/iconmini.jpg"));


    public Register() {
//        Image icon = iconTitle.getImage();
//        setIconImage(icon);
        super("Register");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setSize(450, 301);

        contentPane = new JPanel(new BorderLayout());
        setContentPane(contentPane);


        JLabel title = new JLabel("REGISTER", SwingConstants.CENTER);
        title.setForeground(new Color(0, 0, 160));
        title.setFont(new Font("Arial", Font.BOLD, 23));
        title.setBounds(178, 15, 99, 40);
        title.setBorder(new EmptyBorder(20, 0, 20, 0));
        contentPane.add(title, BorderLayout.NORTH);

        inputPanel();

        buttonPanel();

        pack();
        setLocationRelativeTo(null); // Center the frame on the screens
        setVisible(true);
    }
    void inputPanel() {

        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10); // Add some padding

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i;
            gbc.anchor = GridBagConstraints.WEST;
            panel.add(new JLabel(labels[i]), gbc);

            gbc.gridx = 1;
            gbc.anchor = GridBagConstraints.WEST;
            textFields[i] = new JTextField();
            textFields[i].setPreferredSize(new Dimension(200, 30));
            panel.add(textFields[i], gbc);
        }

        contentPane.add(panel, BorderLayout.CENTER);
    }
    void buttonPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBorder(new EmptyBorder(20, 0, 20, 0));

        JButton registerBtn = new JButton("Register");
        registerBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String[] values = new String[labels.length];
                for (int i = 0; i < labels.length; i++) {
                    values[i] = textFields[i].getText();
                    if (values[i].isEmpty()) {
                        JOptionPane.showMessageDialog(contentPane, "Please fill in all fields!");
                        return;
                    }
                }

                if (!values[2].equals(values[3])) {
                    JOptionPane.showMessageDialog(contentPane, "Passwords don't match!");
                    return;
                }

                dispose();
                SwingUtilities.invokeLater(() -> new Login());
            }
        });

        registerBtn.setFont(new Font("Arial", Font.BOLD, 13));
        panel.add(registerBtn);
        contentPane.add(panel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Register());
    }
}