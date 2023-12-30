package Client.User.Views.Components;

import javax.swing.*;
import java.awt.event.WindowEvent;

public class MenuBar extends JMenuBar {
    public MenuBar() {
        // File menu
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(e -> closeWindow((JFrame) SwingUtilities.getWindowAncestor(this)));
        fileMenu.add(exitMenuItem);

        // Help menu
        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutMenuItem = new JMenuItem("About");
        aboutMenuItem.addActionListener(e -> JOptionPane.showMessageDialog(this, "Chat App v1.0"));
        helpMenu.add(aboutMenuItem);

        add(fileMenu);
        add(helpMenu);

    }
    private static void closeWindow(JFrame frame) {
        WindowEvent windowClosing = new WindowEvent(frame, WindowEvent.WINDOW_CLOSING);
        frame.dispatchEvent(windowClosing);
    }
}
