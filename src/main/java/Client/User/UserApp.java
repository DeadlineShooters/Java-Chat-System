package Client.User;

import Client.User.Views.Home;

import javax.swing.*;

public class UserApp implements Runnable {
	@Override
	public void run() {
		SwingUtilities.invokeLater(() -> {
			new Home();
		});
		new Thread(Client.getInstance()).start();
	}
}
