package Client.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {
    private JPanel contentPane;
    private JTextField userNameField;
    private JCheckBox showCheckBox;
    private JButton btnLogin;
    private JButton btnCancel;
    private JPasswordField passwordField;
    private JButton btnForgetPwd;
//    private ImageIcon iconTitle = new ImageIcon(HomeScreen.class.getResource("/Image/iconmini.jpg"));


    Login() {
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
//        inputField = new JPanel(new GridLayout(2, 2));
//        inputField.add(new JLabel("Username"));
//        inputField.add(new TextField());
//        inputField.add(new JLabel("Password"));
//        inputField.add(new JTextField());
//        inputField.setSize(50, 100);


        JLabel lblNewLabel_1 = new JLabel("Username");
        lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 15));
        lblNewLabel_1.setBounds(56, 75, 82, 16);
        contentPane.add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("Password");
        lblNewLabel_2.setFont(new Font("Arial", Font.BOLD, 15));
        lblNewLabel_2.setBounds(56, 112, 78, 16);
        contentPane.add(lblNewLabel_2);

        userNameField = new JTextField();
        lblNewLabel_1.setLabelFor(userNameField);
//        userNameField.setColumns(17);
        userNameField.setBounds(157, 73, 220, 25);
        contentPane.add(userNameField);

        passwordField = new JPasswordField();
        passwordField.setBounds(157, 108, 220, 25);
        contentPane.add(passwordField);

        showCheckBox = new JCheckBox("Show Password");
        showCheckBox.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 13));
        showCheckBox.setBounds(155, 140, 130, 21);
        contentPane.add(showCheckBox);
        showCheckBox.addActionListener(ae -> {
            JCheckBox c = (JCheckBox) ae.getSource();
            passwordField.setEchoChar(c.isSelected() ? '\u0000' : (Character) UIManager.get("PasswordField.echoChar"));
        });


    }
    void initBottomPanel() {
        btnForgetPwd = new JButton("Forger Password");
        btnForgetPwd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
//                Controller.getInstance().handleScreen("forgotScreen", true);
                setVisible(false);
            }
        });
//        btnForgot.setForeground(new Color(0, 0, 160));
//        btnForgot.setBackground(new Color(192, 192, 192));
        btnForgetPwd.setContentAreaFilled(false);
        btnForgetPwd.setFont(new Font("Arial", Font.ITALIC, 14));
        btnForgetPwd.setBounds(this.getSize().width/2-149/2, 230, 149, 21);
        btnForgetPwd.setBorder(null);
        contentPane.add(btnForgetPwd);

        btnLogin = new JButton("LOG IN");
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
//                handleClickLogin();
//                setVisible(false);
                dispose();
                SwingUtilities.invokeLater(() -> new ChatAppUI());
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
