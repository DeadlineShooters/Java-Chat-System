package Client;

import Client.User.Repositories.UserRepo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends JFrame {
    private JPanel contentPane;
    String[] labels = {"Username", "Email", "Password", "Confirm Password"};
    JTextComponent[] inputFields = new JTextComponent[labels.length];
    JPasswordField pwdField = new JPasswordField();
    JPasswordField confirmPwdField = new JPasswordField();
//    private ImageIcon iconTitle = new ImageIcon(HomeScreen.class.getResource("/Image/iconmini.jpg"));


    public Register() {
//        Image icon = iconTitle.getImage();
//        setIconImage(icon);
        super("Register");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        contentPane = new JPanel(new BorderLayout());
        setSize(450, 380);
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
        backBtn.setFocusPainted(false);
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

        int i = 0;
        for (; i < labels.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i;
            gbc.anchor = GridBagConstraints.WEST;
            panel.add(new JLabel(labels[i]), gbc);

            gbc.gridx = 1;
            gbc.anchor = GridBagConstraints.WEST;
            if (i == 2) {
                inputFields[i] = pwdField;
            } else if (i == 3) {
                inputFields[i] = confirmPwdField;
            } else {
                inputFields[i] = new JTextField();
            }
            inputFields[i].setPreferredSize(new Dimension(200, 30));
            panel.add(inputFields[i], gbc);
        }
        // Show password
        JCheckBox showCheckBox = new JCheckBox("Show Password");
        showCheckBox.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 12));
        showCheckBox.setBounds(155, 140, 130, 21);
//        contentPane.add(showCheckBox);
        showCheckBox.addActionListener(ae -> {
            JCheckBox c = (JCheckBox) ae.getSource();
            pwdField.setEchoChar(c.isSelected() ? '\u0000' : (Character) UIManager.get("PasswordField.echoChar"));
            confirmPwdField.setEchoChar(c.isSelected() ? '\u0000' : (Character) UIManager.get("PasswordField.echoChar"));
        });

        gbc.gridx = 1;
        gbc.gridy = i;
        panel.add(showCheckBox, gbc);

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
                    values[i] = inputFields[i].getText();
                    if (values[i].isEmpty()) {
                        JOptionPane.showMessageDialog(contentPane, "Please fill in all fields!");
                        return;
                    }
                }

                if (!isValidEmail(values[1])) {
                    JOptionPane.showMessageDialog(contentPane, "Invalid email.");
                    return;
                }


                if (UserRepo.emailExisted(values[1])) {
                    JOptionPane.showMessageDialog(contentPane, "Email has been used");
                    return;
                }
                if (UserRepo.usernameExisted(values[0])) {
                    JOptionPane.showMessageDialog(contentPane, "The username has been used");
                    return;
                }
//                if (!values[2].equals(values[3])) {
//                    JOptionPane.showMessageDialog(contentPane, "Passwords don't match!");
//                    return;
//                }
//                if (UserRepo.add(values[0], values[1], User.encryptPassword(values[2]))) {
//                    JOptionPane.showMessageDialog(contentPane, "Account created!");
//                    dispose();
//                    SwingUtilities.invokeLater(() -> new Login());
//                }
            }
        });

        registerBtn.setFont(new Font("Arial", Font.BOLD, 13));
        panel.add(registerBtn);
        contentPane.add(panel, BorderLayout.SOUTH);
    }

    private static final String EMAIL_REGEX =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    private static final Pattern pattern = Pattern.compile(EMAIL_REGEX);

    public static boolean isValidEmail(String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Register());
    }
}