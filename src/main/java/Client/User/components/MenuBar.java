package Client.User.components;

import javax.swing.*;

public class MenuBar extends JMenuBar {
    public MenuBar() {
        // File menu
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitMenuItem);

        // Help menu
        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutMenuItem = new JMenuItem("About");
        aboutMenuItem.addActionListener(e -> JOptionPane.showMessageDialog(this, "Chat App v1.0"));
        helpMenu.add(aboutMenuItem);

        add(fileMenu);
        add(helpMenu);

    }
}
