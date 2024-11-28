package view.algorithmPanel;


import controller.MainController;
import model.common.Cipher;

public class VBlockPanel extends VAlgorithmAbs {

    public VBlockPanel(MainController controller) {
        super(controller);
        name = "Block";
    }

    @Override
    protected void init() {
        algorithms.addItem(Cipher.AES);
        algorithms.addItem(Cipher.BLOWFISH);
        algorithms.addItem(Cipher.DES);
        algorithms.addItem(Cipher.DESEDE);
        algorithms.addItem(Cipher.RC2);
        algorithms.addItem(Cipher.RC4);

    }


//    @Override
//    protected void designLayout() {
//
//    }

}
