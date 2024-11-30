package view.console.text;

import controller.MainController;

import javax.crypto.IllegalBlockSizeException;
import javax.swing.*;

public class VSignTextPanel extends VTextAbs {
    public VSignTextPanel(MainController controller) {
        super(controller);
    }

    @Override
    protected void initPlanText() {
        super.initPlanText();
        planText.setPlaceholder("Enter the text you want to sign here");
        planText.setToolTipText("Enter the text you want to sign here");

    }

    @Override
    protected void initCipherText() {
        super.initCipherText();
        cipherText.setPlaceholder("Signed text will be displayed here");
        cipherText.setToolTipText("Signed text will be displayed here");

    }

    @Override
    protected void initButtonPanel() {
        super.initButtonPanel();
        encryptButton.setText("Sign");
        decryptButton.setText("Verify");
    }

    @Override
    protected void initDecryptText() {
        super.initDecryptText();
        decryptText.setPlaceholder("Enter the signed text you want to verify here");
    }

    @Override
    protected void encrypt() {
        super.encrypt();
        String plainText = planText.getText();
        try {
            String signed = controller.getAlgorithms().encrypt(plainText);
            cipherText.setText(signed);
        } catch (IllegalBlockSizeException e) {
            JOptionPane.showMessageDialog(controller.getFrame(), "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }


    }

    @Override
    protected void decrypt() {
        super.decrypt();
        String plainText = planText.getText();
        String signedText = decryptText.getText();
        try {
            String signed = controller.getAlgorithms().encrypt(plainText);
            cipherText.setText(signed);
            boolean r = controller.getAlgorithms().verify(plainText, signedText);
            if (r) {
                JOptionPane.showMessageDialog(controller.getFrame(), "Match", "Result", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(controller.getFrame(), "Not Match", "Result", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (IllegalBlockSizeException e) {
            JOptionPane.showMessageDialog(controller.getFrame(), "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }
}
