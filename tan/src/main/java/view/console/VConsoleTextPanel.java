package view.console;

import model.common.Button;
import view.font.MyFont;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

import static model.common.Button.*;
import static view.font.MyFont.ROBOTO_REGULAR;

public class VConsoleTextPanel extends JPanel {

    private Font font;
    private JTextArea planText, cipherText, decryptText;
    private JScrollPane planTextScrollPane, cipherScrollPane, decryptScrollPane;
    private JButton encryptButton, decryptButton;

    public VConsoleTextPanel() {
        font = MyFont.loadCustomFont(ROBOTO_REGULAR, 14);

        // Initialize text areas with line wrap and font
        planText = createTextArea("Plain Text");
        cipherText = createTextArea("Cipher Text");
        decryptText = createTextArea("Decrypted Text");

        // Initialize scroll panes with titled borders
        planTextScrollPane = createScrollPane(planText, "Plain Text");
        cipherScrollPane = createScrollPane(cipherText, "Cipher Text");
        decryptScrollPane = createScrollPane(decryptText, "Decrypted Text");

        // Initialize buttons with styling
        encryptButton = new JButton("Encrypt");
        decryptButton = new JButton("Decrypt");
        JPanel buttonPanel = createButtonPanel();

        // Arrange components with BorderLayout and BoxLayout for simplicity
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Add scroll panes to main panel
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.add(planTextScrollPane);
        textPanel.add(Box.createVerticalStrut(10));
        textPanel.add(cipherScrollPane);
        textPanel.add(Box.createVerticalStrut(10));
        textPanel.add(decryptScrollPane);

        add(textPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JTextArea createTextArea(String placeholder) {
        JTextArea textArea = new JTextArea(5, 20);
        textArea.setFont(font);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setToolTipText(placeholder);
        return textArea;
    }

    private JScrollPane createScrollPane(JTextArea textArea, String title) {
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), title, TitledBorder.LEFT, TitledBorder.TOP));
        return scrollPane;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panel.add(encryptButton);
        panel.add(decryptButton);
        return panel;
    }

//        setLayout(new GridLayout(1, 2, 0, 20));
//        JPanel leftPanel = new JPanel();
//        leftPanel.setBorder(BorderFactory.createTitledBorder("File"));
//        leftPanel.setLayout(new GridLayout(3, 1, 10, 10));
//        JPanel inputPanel = new JPanel();
//        inputPanel.setLayout(new BorderLayout(10, 10));
//        inputPanel.add(inputFile, BorderLayout.CENTER);
//        inputFile.setEnabled(false);
//        inputFile.setBackground(Color.WHITE);
//        inputPanel.add(inputBrowseFile, BorderLayout.EAST);
//        leftPanel.add(inputPanel);
//
//        JPanel outputPanel = new JPanel();
//        outputPanel.setLayout(new BorderLayout(10, 10));
//        outputPanel.add(outputFile, BorderLayout.CENTER);
//        outputFile.setEnabled(false);
//        outputFile.setBackground(Color.WHITE);
//        outputPanel.add(outputBrowseFile, BorderLayout.EAST);
//        leftPanel.add(outputPanel);
//
//        leftPanel.add(encryptFileButton);
//
//        add(leftPanel);
//
//        JPanel rightPanel = new JPanel();
//        rightPanel.setLayout(new GridLayout(3, 1, 10, 10));
//        rightPanel.setBorder(BorderFactory.createTitledBorder("Text"));
//        planeText = new JTextField();
//        cipherText = new JTextField();
//        encryptTextButton = new JButton(START);
//        inputBrowseText = new JButton(BROWSE);
//        JPanel inputPanelText = new JPanel();
//        inputPanelText.setLayout(new BorderLayout(10, 10));
//        inputPanelText.add(planeText, BorderLayout.CENTER);
//        cipherText.setEnabled(false);
//        cipherText.setBackground(Color.WHITE);
//        inputPanelText.add(inputBrowseText, BorderLayout.EAST);
//        rightPanel.add(inputPanelText);
//        rightPanel.add(cipherText);
//        rightPanel.add(encryptTextButton);
//        add(rightPanel);


}
