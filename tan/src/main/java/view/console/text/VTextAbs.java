package view.console.text;

import view.custom.TextAreaCus;

import javax.swing.*;

public abstract class VTextAbs extends JPanel{
    protected TextAreaCus planText, cipherText, decryptText;

    public VTextAbs() {
        initPlanText();
        initCipherText();
        initDecryptText();
        init();
    }

    protected void initPlanText() {
        planText = new TextAreaCus("Plain Text");
        planText.setRows(5);
        planText.setColumns(20);
        planText.setLineWrap(true);
        planText.setWrapStyleWord(true);
        planText.setToolTipText("Enter the text you want to encrypt here");
    }

    protected void initCipherText() {
        cipherText = new TextAreaCus("Cipher Text");
        cipherText.setRows(5);
        cipherText.setColumns(20);
        cipherText.setLineWrap(true);
        cipherText.setWrapStyleWord(true);
        cipherText.setToolTipText("Encrypted text will be displayed here");
    }

    protected void initDecryptText() {
        decryptText = new TextAreaCus("Decrypted Text");
        decryptText.setRows(5);
        decryptText.setColumns(20);
        decryptText.setLineWrap(true);
        decryptText.setWrapStyleWord(true);
        decryptText.setToolTipText("Decrypted text will be displayed here");
    }

    protected void init() {
        // Arrange components with BorderLayout and BoxLayout for simplicity
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(planText);
        add(Box.createVerticalStrut(10));
        add(cipherText);
        add(Box.createVerticalStrut(10));
        add(decryptText);
    }

    ;

}
