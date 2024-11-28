package view.algorithmPanel;


import controller.MainController;
import model.common.Cipher;

import java.awt.*;

public class VAsymmetricPanel extends VAlgorithmAbs {

    public VAsymmetricPanel(MainController controller) {
        super(controller);
        name = "Asymmetric";
    }

    @Override
    protected void init() {
        algorithms.addItem(Cipher.RSA);
        algorithms.setBackground(Color.WHITE);
    }



}
