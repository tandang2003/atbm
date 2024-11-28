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

    private VSymmetricPanel symmetricPanel;
    private VAsymmetricPanel asymmetricPanel;
    private VHashPanel hashPanel;
    private VClassicPanel classicPanel;
//    private VBlockPanel blockPanel;
    private VSignPanel signPanel;
    private MainController controller;

    public VAlgorithmPanel(MainController controller) {
        font = MyFont.loadCustomFont(ROBOTO_REGULAR, FONTSIZE_NORMAL);
        this.controller = controller;
        init();
    }

    private void init() {

        setPreferredSize(new Dimension(1000, 350));
//        setMaximumSize(new Dimension(Integer.MAX_VALUE, 500));
        symmetricPanel = new VSymmetricPanel(controller);
        asymmetricPanel = new VAsymmetricPanel(controller);
        hashPanel = new VHashPanel(controller);
        classicPanel = new VClassicPanel(controller);
//        blockPanel = new VBlockPanel(controller);
        signPanel = new VSignPanel(controller);
        this.setFont(font);
        addTab(symmetricPanel.getName(), symmetricPanel);
        addTab(asymmetricPanel.getName(), asymmetricPanel);
        addTab(classicPanel.getName(), classicPanel);
//        addTab(blockPanel.getName(), blockPanel);
        addTab(hashPanel.getName(), hashPanel);
        addTab(signPanel.getName(), signPanel);
        controller.setTabbedPane((VAlgorithmAbs) this.getSelectedComponent());
        addChangeListener(e -> {
            Component selectedComponent = this.getSelectedComponent();
            controller.setAlgorithm((ICipherEnum) ((VAlgorithmAbs) selectedComponent).getAlgorithms().getSelectedItem());
            controller.setTabbedPane((VAlgorithmAbs) selectedComponent);
            ((VMainPanel) this.getParent()).getvConsolePanel().repaintPanel((VAlgorithmAbs) selectedComponent);
        });
    }

}