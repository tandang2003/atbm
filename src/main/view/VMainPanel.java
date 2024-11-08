package main.view;

import javax.swing.*;
import java.awt.*;

public class VMainPanel extends JPanel {
    VConsolePanel vConsolePanel;
    VToolPanel vToolPanel;
    VAlgorithmPanel vAlgorithmPanel;

    public VMainPanel() {
        init();
    }

    private void init() {
        vConsolePanel = new VConsolePanel();
        vToolPanel = new VToolPanel();
        vAlgorithmPanel = new VAlgorithmPanel();

        setLayout(new BorderLayout());
        add(vConsolePanel, BorderLayout.SOUTH);
        add(vToolPanel, BorderLayout.WEST);
        add(vAlgorithmPanel, BorderLayout.CENTER);
    }

}
