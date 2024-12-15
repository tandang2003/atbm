package view;


import controller.MainController;
import view.console.VConsolePanelAbs;
import view.console.file.VSignFilePanel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;


public class VConsolePanel extends JTabbedPane {
    private VConsolePanelAbs consoleFilePanel;
    private MainController c;

    public VConsolePanel(MainController controller) {
        this.c = controller;
        setMinimumSize(new Dimension(1000, 300));
        setSize(new Dimension(1000, 300));
        consoleFilePanel = new VSignFilePanel(controller);
        Border paddingBorder = new EmptyBorder(10, 10, 10, 8);
        Border rightBorder = new MatteBorder(2, 0, 0, 0, Color.BLACK);
        setBorder(BorderFactory.createCompoundBorder(rightBorder, paddingBorder));
        add("File", consoleFilePanel);
    }

}
