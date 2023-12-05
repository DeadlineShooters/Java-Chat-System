package Client.User.Views.Components;

import javax.swing.*;
import java.awt.*;

public class SettingsPanel extends JPanel {
//    private JTabbedPane settingsTabbedPane;
    public SettingsPanel() {
        super(new BorderLayout());
//        settingsTabbedPane = new JTabbedPane();
        setBorder(BorderFactory.createTitledBorder("Settings"));
        setPreferredSize(new Dimension(200, this.getPreferredSize().height));

//        add(settingsTabbedPane, BorderLayout.CENTER);

        // Add tabs to the settings panel
//        settingsTabbedPane.addTab("General", new JPanel());
//        settingsTabbedPane.addTab("Notifications", new JPanel());

    }
}
