package Client;

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

        contentPane = new JPanel(new BorderLayout());
        setSize(450, 340);
        setContentPane(contentPane);

        topPanel();

        inputPanel();

        buttonPanel();

//        pack();
        setLocationRelativeTo(null); // Center the frame on the screens
        setVisible(true);
    }
    void topPanel() {
        JButton backBtn = new JButton("Back");
        backBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                SwingUtilities.invokeLater(() -> new Login());
            }
        });
        //btnForgot.setForeground(new Color(0, 0, 160));
        //btnForgot.setBackground(new Color(192, 192, 192));
//        backBtn.setContentAreaFilled(false);
        backBtn.setFont(new Font("Arial", Font.ITALIC, 11));
        backBtn.setBounds(20, 20, 60, 20);
//        backBtn.setBorder(null);
        contentPane.add(backBtn);


        JLabel title = new JLabel("REGISTER", SwingConstants.CENTER);
        title.setForeground(new Color(0, 0, 160));
        title.setFont(new Font("Arial", Font.BOLD, 23));
        title.setBounds(178, 15, 99, 40);
        title.setBorder(new EmptyBorder(20, 0, 20, 0));
        contentPane.add(title, BorderLayout.NORTH);
    }
    void inputPanel() {

        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Add some padding

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


                if (values[0] == "email") {
                    JOptionPane.showMessageDialog(contentPane, "Email has been used");
                    return;
                }
                if (values[1] == "username") {
                    JOptionPane.showMessageDialog(contentPane, "The username has been used");
                    return;
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