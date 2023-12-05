package Client.User;

import Client.User.Views.Login;

import javax.swing.*;

public class UserApp {
    public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new Login();
		});
	}
}
