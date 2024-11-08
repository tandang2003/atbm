package main.model.common;

import javax.swing.*;
import java.awt.*;

public class HillCipherKeyInput extends JFrame {
    public static void main(String[] args) {
        JFrame frame = new JFrame("GridBagLayout Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // First cell - larger component
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 0.7;  // 70% of the vertical space
        gbc.fill = GridBagConstraints.BOTH;

        JPanel largePanel = new JPanel();
        largePanel.setBackground(Color.CYAN);
        largePanel.add(new JLabel("Larger Panel"));
        mainPanel.add(largePanel, gbc);

        // Second cell - smaller components with equal size
        gbc.gridy = 1;
        gbc.weighty = 0.15;  // 15% of the vertical space

        JPanel panel2 = new JPanel();
        panel2.setBackground(Color.LIGHT_GRAY);
        panel2.add(new JLabel("Panel 2"));
        mainPanel.add(panel2, gbc);

        gbc.gridy = 2;

        JPanel panel3 = new JPanel();
        panel3.setBackground(Color.PINK);
        panel3.add(new JLabel("Panel 3"));
        mainPanel.add(panel3, gbc);

        frame.add(mainPanel);
        frame.setVisible(true);
    }
}
