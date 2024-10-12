package view;

import javax.swing.*;

import java.awt.*;

import static common.Button.*;

public class VConsolePanel extends JPanel {
    private JTextArea planeText, cipherText, key;

    public VConsolePanel() {
        setLayout(new BoxLayout(this,BoxLayout.X_AXIS)); // Use GridBagLayout for better control
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding around the panel

        init();
    }

    private void init() {
        planeText = new JTextArea();
        cipherText = new JTextArea();
        key = new JTextArea();

        initComponents(planeText, PLANE_TEXT);
        initComponents(key, KEY);
        initComponents(cipherText, CIPHER_TEXT);
    }

    private void initComponents(JTextArea textArea, String label) {
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // Add a border to the text area

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setMinimumSize(new Dimension(200, 100)); // Set consistent size for all text areas
        scrollPane.setPreferredSize(new Dimension(300, 200)); // Set consistent size for all text areas

        // Create a label for the text area
        JLabel textLabel = new JLabel(label + ":");
        textLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Panel to hold the label and the text area
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10)); // Add spacing between sections
        panel.add(textLabel);
        panel.add(scrollPane);

        // Add panel to the main layout with some insets for spacing
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0); // Add vertical space between components

        add(panel, gbc);
    }

}
