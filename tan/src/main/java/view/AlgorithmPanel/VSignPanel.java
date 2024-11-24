package view.AlgorithmPanel;

import controller.MainController;
import model.common.Hash;
import model.common.KeyPairAlgorithm;

public class VSignPanel extends VAlgorithmAbs {
    public VSignPanel(MainController controller) {
        super(controller);
        name = "Sign";
    }


    @Override
    public void init() {
        algorithms.addItem(KeyPairAlgorithm.RSA);
        algorithms.addItem(KeyPairAlgorithm.DSA);
    }
}
