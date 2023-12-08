package Client;

import javax.swing.*;

public class Main implements Runnable {
    public static void main(String[] args) {
//        new Thread(new Main()).start();
        SwingUtilities.invokeLater(() -> new Login());
    }
    @Override
    public void run() {
        SwingUtilities.invokeLater(() -> new Login());
    }
}
