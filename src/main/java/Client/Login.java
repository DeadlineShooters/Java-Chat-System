package Client;

import Client.Admin.AdminApp;
import Client.Admin.Repository.SessionRepository;
import Client.Models.User;
import Client.User.CurrentUser;
import Client.User.Repositories.UserRepo;
import Client.User.UserApp;
import org.apache.commons.lang3.RandomStringUtils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

public class Login extends JFrame {
    private JPanel contentPane;
    protected SessionRepository sessionRepository = new SessionRepository();
    // private ImageIcon iconTitle = new
    // ImageIcon(HomeScreen.class.getResource("/Image/iconmini.jpg"));
    JTextField userNameField;
    JPasswordField passwordField;

    public Login() {
        // Image icon = iconTitle.getImage();
        // setIconImage(icon);
        super("Login");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 340);

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

        userNameField = new JTextField();
        usernameLabel.setLabelFor(userNameField);
        // userNameField.setColumns(17);
        userNameField.setBounds(157, 73, 220, 25);
        contentPane.add(userNameField);

        passwordField = new JPasswordField();
        passwordField.setBounds(157, 108, 220, 25);
        contentPane.add(passwordField);

        userNameField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performLogin();
            }
        });

        passwordField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performLogin();
            }
        });

        // Forget password
        JButton btnForgotPwd = new JButton("Forgot Password");
        btnForgotPwd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Controller.getInstance().handleScreen("forgotScreen", true);
                forgotPwdDialog();
            }
        });
        // btnForgot.setForeground(new Color(0, 0, 160));
        // btnForgot.setBackground(new Color(192, 192, 192));
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
        // Login
        JButton btnLogin = new JButton("LOG IN");
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // handleClickLogin();
                // setVisible(false);
                performLogin();
            }
        });

        btnLogin.setFont(new Font("Arial", Font.BOLD, 13));
        btnLogin.setBounds(this.getSize().width / 2 - 99 / 2, 220, 99, 34);
        contentPane.add(btnLogin);

        // Register
        JPanel panel = new JPanel();
        panel.setBounds(this.getSize().width / 2 - 215 / 2, 265, 215, 30);
        contentPane.add(panel);

        JLabel label = new JLabel("Do not have an account?");
        label.setFont(new Font("Arial", Font.ITALIC, 11));
        panel.add(label);

        JButton btnRegister = new JButton("Register");
        btnRegister.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                SwingUtilities.invokeLater(() -> new Register());
            }
        });
        // btnForgot.setForeground(new Color(0, 0, 160));
        // btnForgot.setBackground(new Color(192, 192, 192));
        btnRegister.setContentAreaFilled(false);
        btnRegister.setFont(new Font("Arial", Font.ITALIC, 12));
        btnRegister.setForeground(Color.BLUE);
        btnRegister.setBorder(null);
        panel.add(btnRegister);
    }

    void forgotPwdDialog() {
        JDialog dialog = new JDialog(this, "Forgot Password", true);
        dialog.setLayout(new BorderLayout());
        dialog.setResizable(false);

        // input email
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add some padding
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(new JLabel("Email"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        JTextField emailField = new JTextField();
        emailField.setPreferredSize(new Dimension(200, 30));
        panel.add(emailField, gbc);

        dialog.add(panel, BorderLayout.CENTER);

        // send button
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton sendBtn = new JButton("Send new password");
        btnPanel.add(sendBtn);
        btnPanel.setBorder(new EmptyBorder(0, 0, 10, 0));
        dialog.add(btnPanel, BorderLayout.SOUTH);

        sendBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // check empty
                String email = emailField.getText();
                if (email.isEmpty())
                    return;

                User user = UserRepo.findByEmail(email);
                if (user == null) {
                    JOptionPane.showMessageDialog(contentPane, "Email not found!");
                    // dialog.dispose();
                    return;
                }
                String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
                String newPassword = RandomStringUtils.random(6, characters);
                System.out.println(newPassword);

                UserRepo.updatePassword(user.username(), newPassword);

                final String fromEmail = "phamtrantuantu27@gmail.com";
                final String password = "qtpj mula vpkz gftg";
                final String toEmail = user.email();

                Properties props = new Properties();
                props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP Host
                props.put("mail.smtp.port", "587"); // TLS Port
                props.put("mail.smtp.auth", "true"); // enable authentication
                props.put("mail.smtp.starttls.enable", "true"); // enable STARTTLS

                Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(fromEmail, password);
                    }
                });

                MimeMessage message = new MimeMessage(session);
                try {
                    message.setFrom(new InternetAddress(fromEmail));
                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));

                    message.setSubject("Reset password request");
                    String htmlContent = "<html>" +
                            "<body>" +
                            "<p>Dear " + user.username() + ",</p>" +
                            "<p>We have reset your password as per your request. Your new password is: <b>"
                            + newPassword + "</b></p>"
                            +
                            "<p>Please use this password to log in to your account. For security reasons, we recommend that you change this password immediately after logging in.</p>"
                            +
                            "<p>If you did not request a password reset, please contact our support team immediately.</p>"
                            +
                            "<p>Best Regards,</p>" +
                            "</body>" +
                            "</html>";
                    message.setContent(htmlContent, "text/html");

                    Transport.send(message);
                } catch (MessagingException e1) {
                    e1.printStackTrace();
                }

                // if email existed, update the password in the db
                JOptionPane.showMessageDialog(contentPane, "A new password has been sent to your email.");
                dialog.dispose();

            }
        });

        dialog.pack();
        dialog.setLocationRelativeTo(this); // Center the dialog on the parent frame
        dialog.setVisible(true);
    }

    void performLogin() {
        String username = userNameField.getText();
        String password = passwordField.getText();
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(contentPane, "Please fill in all fields!");
            return;
        }

        if (username.equals("admin") && password.equals("admin")) {
            dispose();
            SwingUtilities.invokeLater(() -> new AdminApp());
        } else {
            // check is locked
            if (UserRepo.isLocked(username)) {
                JOptionPane.showMessageDialog(contentPane, "This account has been locked!", "Alert",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Authentication
            if (!UserRepo.authen(username, password)) {
                JOptionPane.showMessageDialog(contentPane, "Wrong username or password!", "Alert",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            User user = UserRepo.getOne(username);
            CurrentUser.getInstance().setUser(user);
            dispose();
            new Thread(new UserApp()).start();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Login());
    }
}
