package Client.Admin.Views.Components;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class MultiButtonRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                   int row, int column) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 0)); // Set the layout to GridLayout
        String[] buttons = ((String) value).split(", ");
        for (String buttonText : buttons) {
            JButton button = new JButton(buttonText);
            panel.add(button);
        }
        return panel;
    }
}
