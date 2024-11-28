package view.console.text;

import controller.MainController;
import model.algorithms.modernEncryption.BcryptHashAlgorithm;
import view.custom.TextAreaCus;

import javax.crypto.IllegalBlockSizeException;
import javax.swing.*;

public class VHashTextPanel extends VTextAbs {

    public VHashTextPanel(MainController controller) {
        super(controller);
    }

    @Override
    protected void initPlanText() {
        planText = new TextAreaCus("Text");
        planText.setRows(5);
        planText.setColumns(20);
        planText.setLineWrap(true);
        planText.setWrapStyleWord(true);
        planText.setToolTipText("Enter the text you want to hash here");
        planScroll = new JScrollPane(planText);
    }

    @Override
    protected void initCipherText() {
        cipherText = new TextAreaCus("Hashed text");
        cipherText.setRows(5);
        cipherText.setColumns(20);
        cipherText.setLineWrap(true);
        cipherText.setWrapStyleWord(true);
        cipherText.setToolTipText("Hashed text will be displayed here");
        cipherScroll = new JScrollPane(cipherText);
    }

    @Override
    protected void initDecryptText() {
        decryptText = new TextAreaCus("Enter the hashed text you want to check here");
        decryptText.setRows(5);
        decryptText.setColumns(20);
        decryptText.setLineWrap(true);
        decryptText.setWrapStyleWord(true);
        decryptText.setToolTipText("Enter the text you want to check here");
        decryptScroll = new JScrollPane(decryptText);
    }


    @Override
    protected void decrypt() {
        super.decrypt();
        String hashedText = cipherText.getText();
        String plainedText = decryptText.getText();
        boolean isMatch = hashedText.equals(plainedText);
        if (controller.getAlgorithms() instanceof BcryptHashAlgorithm) {
            String plainText = planText.getText();
            isMatch = controller.getAlgorithms().verify(plainText, plainedText);
        }
        if (isMatch) {
            JOptionPane.showMessageDialog(controller.getFrame(), "Match", "Result", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(controller.getFrame(), "Not Match", "Result", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    @Override
    protected void initButtonPanel() {
        super.initButtonPanel();
        encryptButton.setText("Hash");
        decryptButton.setText("Check");
    }

    @Override
    protected void encrypt() {
        super.encrypt();
        String plainText = planText.getText();
        String cipherText = "";
        try {
            cipherText = controller.getAlgorithms().encrypt(plainText);
        } catch (IllegalBlockSizeException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        this.cipherText.setText(cipherText);
    }
}
