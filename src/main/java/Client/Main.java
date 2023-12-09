package Client;

import Client.User.Repositories.UserRepo;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
//        new Thread(new Main()).start();
        UserRepo user = new UserRepo();
        SwingUtilities.invokeLater(() -> new Login());
    }
}
