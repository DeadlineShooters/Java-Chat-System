package Client.User.Views.Components;

import Client.User.Views.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SettingsPanel extends JPanel {
    public SettingsPanel() {
        super(new BorderLayout(0, 15));
        setPreferredSize(new Dimension(300, this.getPreferredSize().height));

        JButton searchButton = new JButton();
        ImageIcon searchIcon = Util.createRoundedImageIcon("searchIcon.png", 30);
        searchButton.setIcon(searchIcon);

        // Create add user button
        JButton addUserButton = new JButton();
        ImageIcon addIcon = Util.createRoundedImageIcon("add-user.png", 30);
        addUserButton.setIcon(addIcon);

        // Create a panel to hold the buttons
        JPanel topPanel = new JPanel();
        topPanel.add(searchButton);
        topPanel.add(addUserButton);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));

        String[] buttonTexts = {"Unfriend", "Block", "Report Spam", "Delete History"};

        for (String buttonText : buttonTexts) {
            HoverablePanel textPanel = new HoverablePanel();



            textPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
            bottomPanel.add(textPanel);

            JLabel label = new JLabel(buttonText);
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setFont(new Font(label.getFont().getName(), label.getFont().getStyle(), 18)); // Change 18 to your desired font size

            textPanel.add(label);

            bottomPanel.add(Box.createVerticalStrut(5)); // set vertical gap between buttons

            // Set the preferred height of the button based on the text height
            int preferredHeight = getFontMetrics(label.getFont()).getHeight() + 18; // gets text's height + 10 is for padding
            textPanel.setPreferredSize(new Dimension(Integer.MAX_VALUE, preferredHeight));
            textPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, preferredHeight));
        }

        // Add the button panel to the settings panel
        add(topPanel, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.CENTER);
    }

    private static class HoverablePanel extends JPanel {
        private boolean hovered = false;

        public HoverablePanel() {
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    hovered = true;
                    repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    hovered = false;
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (hovered) {
                g.setColor(new Color(173, 216, 230)); // Light blue color
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        }


    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Settings Panel");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(new SettingsPanel());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
