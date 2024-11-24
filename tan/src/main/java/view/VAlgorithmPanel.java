package view;


import com.sun.tools.javac.Main;
import controller.MainController;
import model.common.Cipher;
import model.common.ICipherEnum;
import view.AlgorithmPanel.*;
import view.font.MyFont;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

import static model.common.Button.FONTSIZE_NORMAL;
import static view.font.MyFont.ROBOTO_REGULAR;

public class VAlgorithmPanel extends JTabbedPane {

    private Font font;

    private VSymmetricPanel symmetricPanel;
    private VAsymmetricPanel asymmetricPanel;
    private VHashPanel hashPanel;
    private VClassicPanel classicPanel;
    private VBlockPanel blockPanel;
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
        blockPanel = new VBlockPanel(controller);
        this.setFont(font);
        addTab(symmetricPanel.getName(), symmetricPanel);
        addTab(asymmetricPanel.getName(), asymmetricPanel);
        addTab(classicPanel.getName(), classicPanel);
        addTab(blockPanel.getName(), blockPanel);
        addTab(hashPanel.getName(), hashPanel);
        controller.setTabbedPane((VAlgorithmAbs) this.getSelectedComponent());

        addChangeListener(e -> {
            Component selectedComponent = this.getSelectedComponent();
            controller.setAlgorithm((ICipherEnum) ((VAlgorithmAbs) selectedComponent).getAlgorithms().getSelectedItem());
            controller.setTabbedPane((VAlgorithmAbs) selectedComponent);

        });
    }

}