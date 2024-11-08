package main.view;

import main.view.font.MyFont;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import java.awt.*;

import static main.model.common.Button.*;
import static main.view.font.MyFont.ROBOTO_REGULAR;

public class VConsolePanel extends JPanel {
    private JTextArea planeText, cipherText, decryptedText;
    private JButton encryptButton, decryptButton;
    private Font font;

    public VConsolePanel() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS)); // Use GridBagLayout for better control
        Border paddingBorder = new EmptyBorder(10, 10, 10, 8);
        font = MyFont.loadCustomFont(ROBOTO_REGULAR, FONTSIZE_NORMAL);
        // Matte border for the right line (2 pixels on the right, black color)
        Border rightBorder = new MatteBorder(2, 0, 0, 0, Color.BLACK);
        setBorder(BorderFactory.createCompoundBorder(rightBorder, paddingBorder));
        init();
    }

    private void init() {
        planeText = new JTextArea();
        cipherText = new JTextArea();
        decryptedText = new JTextArea();
        encryptButton = new JButton(ENCRYPT);
        encryptButton.setFont(font);
        decryptButton = new JButton(DECRYPT);
        decryptButton.setFont(font);
        initComponents(planeText, PLANT_TEXT);
        add(encryptButton);
        initComponents(cipherText, CIPHER_TEXT);
        add(decryptButton);
        initComponents(decryptedText, DECRYPT_TEXT);
    }

    private void initComponents(JTextArea textArea, String label) {
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // Add a border to the text area
        textArea.setFont(font);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
//        scrollPane.setMinimumSize(new Dimension(400, 300)); // Set consistent size for all text areas
//        scrollPane.setSize(new Dimension(400, 300)); // Set consistent size for all text areas
        scrollPane.setPreferredSize(new Dimension(200, 200)); // Set consistent size for all text areas

        // Create a label for the text area
        JLabel textLabel = new JLabel(label + ":");
        textLabel.setFont(font);
        textLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Panel to hold the label and the text area
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10)); // Add spacing between sections
        panel.add(textLabel);
        panel.add(scrollPane);

        // Add panel to the main.main layout with some insets for spacing
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0); // Add vertical space between components
        add(panel, gbc);
    }

}
