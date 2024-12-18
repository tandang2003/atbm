package view.algorithmPanel;


import controller.MainController;
import model.common.Cipher;

import java.awt.*;

public class VSymmetricPanel extends VAlgorithmAbs {

    public VSymmetricPanel(MainController controller) {
        super(controller);
        name = "Symmetric";
    }

    @Override
    protected void init() {
        algorithms.addItem(Cipher.AES);
//        algorithms.addItem(Cipher.AFFINE);
        algorithms.addItem(Cipher.BLOWFISH);
        algorithms.addItem(Cipher.DES);
        algorithms.addItem(Cipher.DESEDE);
//        algorithms.addItem(Cipher.HILL);
//        algorithms.addItem(Cipher.TRANSPOSITION);
        algorithms.addItem(Cipher.RC2);
        algorithms.addItem(Cipher.RC4);
        algorithms.addItem(Cipher.Camellia);
        algorithms.addItem(Cipher.Twofish);
        algorithms.addItem(Cipher.Serpent);
//        algorithms.addItem(Cipher.SUBSTITUTION);
//        algorithms.addItem(Cipher.VIGENERE);
        algorithms.setBackground(Color.WHITE);
    }


    private void setModeAndPadding() {
        String algorithm = (String) algorithms.getSelectedItem();


    }


}
