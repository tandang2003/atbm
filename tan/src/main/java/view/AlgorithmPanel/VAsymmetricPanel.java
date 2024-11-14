package view.AlgorithmPanel;


import controller.MainController;
import model.common.Cipher;

import javax.swing.*;
import java.awt.*;

public class VAsymmetricPanel extends VAlgorithmAbs {

    public VAsymmetricPanel(MainController controller) {
        super(controller);
        name = "Asymmetric";
    }

    @Override
    protected void init() {
        algorithms.addItem(Cipher.RSA);
//        algorithms.addItem(Cipher.AFFINE);
//        algorithms.addItem(Cipher.BLOWFISH);
//        algorithms.addItem(Cipher.DES);
//        algorithms.addItem(Cipher.DESEDE);
//        algorithms.addItem(Cipher.HILL);
//        algorithms.addItem(Cipher.TRANSPOSITION);
//        algorithms.addItem(Cipher.RC2);
//        algorithms.addItem(Cipher.RC4);
//        algorithms.addItem(Cipher.SUBSTITUTION);
//        algorithms.addItem(Cipher.VIGENERE);

        algorithms.setSelectedIndex(0);
        algorithms.setBackground(Color.WHITE);
    }



}
