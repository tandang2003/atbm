package view;


import controller.MainController;
import observer.alphabetObserver.AlphaSubject;

import javax.swing.*;
import java.awt.*;

public class VMainPanel extends JPanel {
    private VConsolePanel vConsolePanel;
    private VToolPanel vToolPanel;
    private VAlgorithmPanel vAlgorithmPanel;
    private MainController mainController;
    public VMainPanel(MainController mainController) {
        this.mainController = mainController;
        init();
    }

    private void init() {
        vConsolePanel = new VConsolePanel();
        vAlgorithmPanel = new VAlgorithmPanel(mainController);
        vToolPanel = new VToolPanel(mainController);

        setLayout(new BorderLayout());
        add(vConsolePanel, BorderLayout.SOUTH);
        add(vToolPanel, BorderLayout.WEST);
        add(vAlgorithmPanel, BorderLayout.CENTER);
    }

}
