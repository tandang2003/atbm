package view;


import controller.MainController;
import model.common.ICipherEnum;
import view.algorithmPanel.*;
import view.font.MyFont;

import javax.swing.*;
import java.awt.*;

import static model.common.Button.FONTSIZE_NORMAL;
import static view.font.MyFont.ROBOTO_REGULAR;

public class VAlgorithmPanel extends JTabbedPane {
    private Font font;
    private VSignPanel signPanel;
    private MainController controller;

    public VAlgorithmPanel(MainController controller) {
        font = MyFont.loadCustomFont(ROBOTO_REGULAR, FONTSIZE_NORMAL);
        this.controller = controller;
        init();
    }

    private void init() {
        setPreferredSize(new Dimension(1000, 350));
        signPanel = new VSignPanel(controller);
        this.setFont(font);
        addTab(signPanel.getName(), signPanel);
    }

}