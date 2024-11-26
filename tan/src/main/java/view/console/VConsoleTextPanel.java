package view.console;

import controller.MainController;
import view.console.text.VClassicTextPanel;
import view.console.text.VTextAbs;
import view.font.MyFont;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

import static view.font.MyFont.ROBOTO_REGULAR;

public class VConsoleTextPanel extends JPanel {

    private Font font;
    private JTextArea planText, cipherText, decryptText;
    private JScrollPane planTextScrollPane, cipherScrollPane, decryptScrollPane;
    private JButton encryptButton, decryptButton;
    private MainController controller;
    private JPanel vClassicTextPanel;

    public VConsoleTextPanel(MainController controller) {
        this.controller = controller;
        this.vClassicTextPanel = new VClassicTextPanel();
        font = MyFont.loadCustomFont(ROBOTO_REGULAR, 14);

        JPanel buttonPanel = createButtonPanel();
        add(vClassicTextPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

//    private JTextArea createTextArea(String placeholder) {
//        JTextArea textArea = new JTextArea(5, 20);
//        textArea.setFont(font);
//        textArea.setLineWrap(true);
//        textArea.setWrapStyleWord(true);
//        textArea.setToolTipText(placeholder);
//        return textArea;
//    }
//
//    private JScrollPane createScrollPane(JTextArea textArea, String title) {
//        JScrollPane scrollPane = new JScrollPane(textArea);
//        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
//        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//        scrollPane.setBorder(BorderFactory.createTitledBorder(
//                BorderFactory.createEtchedBorder(), title, TitledBorder.LEFT, TitledBorder.TOP));
//        return scrollPane;
//    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        encryptButton = new JButton("Encrypt");
        decryptButton = new JButton("Decrypt");
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panel.add(encryptButton);
        panel.add(decryptButton);
        return panel;
    }

}
