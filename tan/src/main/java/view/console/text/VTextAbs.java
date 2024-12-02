package view.console.text;

import controller.MainController;
import view.console.VConsolePanelAbs;
import view.custom.TextAreaCus;

import javax.swing.*;

public abstract class VTextAbs extends VConsolePanelAbs {
    protected TextAreaCus planText, cipherText, decryptText;
    protected JScrollPane planScroll, cipherScroll, decryptScroll;

    public VTextAbs(MainController controller) {
        super(controller);
    }


    protected void initPlanText() {
        planText = new TextAreaCus("Plain Text");
        planText.setRows(5);
        planText.setColumns(20);
        planText.setLineWrap(true);
        planText.setWrapStyleWord(true);
        planText.setToolTipText("Enter the text you want to encrypt here");
        planScroll = new JScrollPane(planText);

    }

    protected void initCipherText() {
        cipherText = new TextAreaCus("Cipher Text");
        cipherText.setRows(5);
        cipherText.setColumns(20);
        cipherText.setLineWrap(true);
        cipherText.setWrapStyleWord(true);
        cipherText.setToolTipText("Encrypted text will be displayed here");
        cipherScroll = new JScrollPane(cipherText);
    }

    protected void initDecryptText() {

        decryptText = new TextAreaCus("Decrypted Text");
        decryptText.setRows(5);
        decryptText.setColumns(20);
        decryptText.setLineWrap(true);
        decryptText.setWrapStyleWord(true);
        decryptText.setToolTipText("Decrypted text will be displayed here");
        decryptScroll = new JScrollPane(decryptText);

    }

    @Override
    protected void initInputPanel() {
        initPlanText();
        initCipherText();
        initDecryptText();

        inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.add(planScroll);
        inputPanel.add(Box.createVerticalStrut(10));
        inputPanel.add(cipherScroll);
        inputPanel.add(Box.createVerticalStrut(10));
        inputPanel.add(decryptScroll);
    }

    @Override
    protected void encrypt() throws ClassNotFoundException {
        if (planText.getText().isEmpty()) {
            JOptionPane.showMessageDialog(controller.getFrame(), "Please enter the text you want to encrypt", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            controller.getAlgorithms().validation();
        } catch (ClassNotFoundException e) {
            throw e;
        }
    }

    @Override
    protected void decrypt() throws ClassNotFoundException {
        try {
            controller.getAlgorithms().validation();
        } catch (ClassNotFoundException e) {
            throw e;
        }
    }
}
