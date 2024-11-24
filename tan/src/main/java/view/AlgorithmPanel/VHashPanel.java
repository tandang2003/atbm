package view.AlgorithmPanel;


import controller.MainController;
import model.common.Cipher;
import model.common.Hash;

public class VHashPanel extends VAlgorithmAbs {

    public VHashPanel(MainController controller) {
        super(controller);
        name = "Hash";
    }

    @Override
    protected void init() {
        algorithms.addItem(Hash.MD2);
        algorithms.addItem(Hash.MD5);
        algorithms.addItem(Hash.SHA_1);
        algorithms.addItem(Hash.SHA_256);
        algorithms.addItem(Hash.SHA_384);
        algorithms.addItem(Hash.SHA_512);
        algorithms.addItem(Hash.SHA_512_224);
        algorithms.addItem(Hash.SHA_512_256);
        algorithms.addItem(Hash.SHA3_224);
        algorithms.addItem(Hash.SHA3_256);
        algorithms.addItem(Hash.SHA3_384);
        algorithms.addItem(Hash.SHA3_512);
    }
}
