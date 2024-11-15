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

        algorithms.setSelectedIndex(0);
        algorithms.setBackground(Color.WHITE);
    }



}
