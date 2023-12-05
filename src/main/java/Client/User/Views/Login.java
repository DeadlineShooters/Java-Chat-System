package Client.User.Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {
    private JPanel contentPane;
//    private ImageIcon iconTitle = new ImageIcon(HomeScreen.class.getResource("/Image/iconmini.jpg"));


    public Login() {
//        Image icon = iconTitle.getImage();
//        setIconImage(icon);
        super("Login");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 301);

        contentPane = new JPanel();

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel title = new JLabel("LOG IN");
        title.setForeground(new Color(0, 0, 160));
        title.setFont(new Font("Arial", Font.BOLD, 23));
        title.setBounds(178, 15, 99, 40);
        contentPane.add(title);

        initInputField();

        initBottomPanel();

        setLocationRelativeTo(null); // Center the frame on the screens
        setVisible(true);
    }

    void initInputField() {


        // username and password
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        usernameLabel.setBounds(56, 75, 82, 16);
        contentPane.add(usernameLabel);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        passwordLabel.setBounds(56, 112, 78, 16);
        contentPane.add(passwordLabel);

        JTextField userNameField = new JTextField();
        usernameLabel.setLabelFor(userNameField);
        //userNameField.setColumns(17);
        userNameField.setBounds(157, 73, 220, 25);
        contentPane.add(userNameField);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(157, 108, 220, 25);
        contentPane.add(passwordField);

        // Forget password
        JButton btnForgotPwd = new JButton("Forgot Password");
        btnForgotPwd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Controller.getInstance().handleScreen("forgotScreen", true);
                setVisible(false);
            }
        });
        //btnForgot.setForeground(new Color(0, 0, 160));
        //btnForgot.setBackground(new Color(192, 192, 192));
        btnForgotPwd.setContentAreaFilled(false);
        btnForgotPwd.setFont(new Font("Arial", Font.ITALIC, 12));
        btnForgotPwd.setForeground(Color.BLUE);
        btnForgotPwd.setBounds(257, 140, 149, 21);
        btnForgotPwd.setBorder(null);
        contentPane.add(btnForgotPwd);

        // Show password
        JCheckBox showCheckBox = new JCheckBox("Show Password");
        showCheckBox.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 12));
        showCheckBox.setBounds(155, 140, 130, 21);
        contentPane.add(showCheckBox);
        showCheckBox.addActionListener(ae -> {
            JCheckBox c = (JCheckBox) ae.getSource();
            passwordField.setEchoChar(c.isSelected() ? '\u0000' : (Character) UIManager.get("PasswordField.echoChar"));
        });


    }
    void initBottomPanel() {

        JPanel panel = new JPanel();
        panel.setBounds(this.getSize().width/2-215/2, 230, 215, 30);
        contentPane.add(panel);


        JLabel label = new JLabel("Do not have an account?");
//        label.setBounds(100, 230, 149, 21);
        label.setFont(new Font("Arial", Font.ITALIC, 11));
        panel.add(label);

        JButton btnRegister = new JButton("Register");
        btnRegister.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
//                Controller.getInstance().handleScreen("forgotScreen", true);
                setVisible(false);
            }
        });
//        btnForgot.setForeground(new Color(0, 0, 160));
//        btnForgot.setBackground(new Color(192, 192, 192));
        btnRegister.setContentAreaFilled(false);
        btnRegister.setFont(new Font("Arial", Font.ITALIC, 11));
        btnRegister.setForeground(Color.BLUE);
//        btnForgetPwd.setBounds(this.getSize().width/2-149/2, 230, 149, 21);
        btnRegister.setBorder(null);
        panel.add(btnRegister);

        JButton btnLogin = new JButton("LOG IN");
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
//                handleClickLogin();
//                setVisible(false);
                dispose();
                SwingUtilities.invokeLater(() -> new Home());
            }
        });

        btnLogin.setFont(new Font("Arial", Font.BOLD, 13));
        btnLogin.setBounds(this.getSize().width/2-99/2, 190, 99, 34);
        contentPane.add(btnLogin);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Login());
    }
}
