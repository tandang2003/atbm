package view.algorithmPanel;


import controller.MainController;
import model.common.Cipher;
import view.font.MyFont;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;

import static model.common.Button.FONTSIZE_NORMAL;
import static view.font.MyFont.ROBOTO_REGULAR;

public class VClassicPanel extends VAlgorithmAbs {
    private JPanel keyAndDesPanel, keyPanel, alphabetsPanel, foreignPanel;
    private Font keyFont;
    private DetailAlgorithmPanel algorithmPanel;

    public VClassicPanel(MainController controller) {
        super(controller);
        name = "Classic";
    }

    @Override
    protected void init() {
        keyFont = MyFont.loadCustomFont(ROBOTO_REGULAR, FONTSIZE_NORMAL);
        algorithms.addItem(Cipher.AFFINE);
        algorithms.addItem(Cipher.HILL);
        algorithms.addItem(Cipher.TRANSPOSITION);
        algorithms.addItem(Cipher.SUBSTITUTION);
        algorithms.addItem(Cipher.VIGENERE);
    }


    private void setEvent() {
        algorithms.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                this.algorithmPanel.rebuildPanel((Cipher) this.algorithms.getSelectedItem());
            }
        });
    }


}
