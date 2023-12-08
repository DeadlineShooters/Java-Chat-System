package Client.User;

import Client.User.Views.Home;

import javax.swing.*;

public class UserApp implements Runnable {
    public static void main(String[] args) {
//		SwingUtilities.invokeLater(() -> {
//			new Home();
//		});
//		new Thread(new Client()).start();
		new Thread(new UserApp()).start();
	}

	@Override
	public void run() {
		SwingUtilities.invokeLater(() -> {
			new Home();
		});
		new Thread(new Client()).start();
	}
}
