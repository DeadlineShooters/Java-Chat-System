package Client;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
//        new Thread(new Main()).start();
//        new UserRepo();
        SwingUtilities.invokeLater(() -> new Login());
    }
}
