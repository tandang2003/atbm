package view.algorithmPanel;


import controller.MainController;
import model.common.ICipherEnum;
import observer.algorithm.ObserverAlgorithm;
import view.font.MyFont;

import javax.swing.*;
import java.awt.*;

import static model.common.Button.FONTSIZE_NORMAL;
import static view.font.MyFont.ROBOTO_BOLD;

public abstract class VAlgorithmAbs extends JPanel implements ObserverAlgorithm {
    protected String name;

    @Override
    public String getName() {
        return name;
    }

    protected JComboBox<ICipherEnum> algorithms;
    protected Font font;
    private DetailAlgorithmPanel algorithmPanel;
    private MainController controller;

    public VAlgorithmAbs(MainController controller) {
        this.controller = controller;
        controller.register(this);
        algorithms = new JComboBox<>();
        font = MyFont.loadCustomFont(ROBOTO_BOLD, FONTSIZE_NORMAL);
        algorithms.setFont(font);
        algorithms.setEditable(false);
//        algorithms.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        algorithmPanel = new DetailAlgorithmPanel(controller);
        init();
        designLayout();
        algorithms.setSelectedIndex(0);
        algorithmPanel.rebuildPanel((ICipherEnum) algorithms.getSelectedItem());
    }

    protected abstract void init();

    protected void designLayout() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JPanel tempAlgoPanel = new JPanel();
        tempAlgoPanel.setLayout(new BorderLayout());
        tempAlgoPanel.add(algorithms, BorderLayout.CENTER);
        tempAlgoPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0), "Algorithms"));
        add(tempAlgoPanel, BorderLayout.NORTH);
        add(algorithmPanel, BorderLayout.CENTER);

//        algorithms.addActionListener(e -> {
//            controller.setAlgorithm((ICipherEnum) algorithms.getSelectedItem());
//            algorithmPanel.rebuildPanel((ICipherEnum) algorithms.getSelectedItem());
//            try {
//                ((VMainPanel) (getParent().getParent())).getvConsolePanel().repaintPanel((VAlgorithmAbs) ((VAlgorithmPanel) getParent()).getSelectedComponent());
//            } catch (Exception ex) {
//                System.out.println("Error");
//            }
//        });
    }

    ;


    @Override
    public void update(VAlgorithmAbs algorithmAbs, ICipherEnum cipher) {
        algorithmPanel.genKeyPair();
    }
}
