package view.console.text;

import controller.MainController;

import javax.crypto.IllegalBlockSizeException;
import javax.swing.*;

public class VClassicTextPanel extends VTextAbs {
    public VClassicTextPanel(MainController controller) {
        super(controller);
    }


    @Override
    protected void encrypt() {
        String plainText = planText.getText();
        String cipherText = "";
        try {
            cipherText = controller.getAlgorithms().encrypt(plainText);
        } catch (IllegalBlockSizeException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        this.cipherText.setText(cipherText);
    }

    @Override
    protected void decrypt() {
        String cipherText = this.cipherText.getText();
        String decryptText = "";
        try {
            decryptText = controller.getAlgorithms().decrypt(cipherText);
        } catch (IllegalBlockSizeException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        this.decryptText.setText(decryptText);

    }


}
