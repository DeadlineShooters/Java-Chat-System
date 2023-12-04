package Client.Admin;

import Client.Admin.Views.Home;

import javax.swing.*;

public class AdminApp {
    public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new Home();
		});
	}
}
