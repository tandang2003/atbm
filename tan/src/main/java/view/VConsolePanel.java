package view;


import controller.MainController;
import view.algorithmPanel.*;
import view.console.VConsolePanelAbs;
import view.console.file.VSignFilePanel;
import view.console.text.VSignTextPanel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;


public class VConsolePanel extends JTabbedPane {
    private VConsolePanelAbs textPanel;
    private VConsolePanelAbs consoleFilePanel;
    private MainController c;

    public VConsolePanel(MainController controller) {
        this.c = controller;
        setMinimumSize(new Dimension(1000, 300));
        setSize(new Dimension(1000, 300));
        consoleFilePanel = new VSignFilePanel(controller);
        textPanel = new VSignTextPanel(controller);
        Border paddingBorder = new EmptyBorder(10, 10, 10, 8);
        Border rightBorder = new MatteBorder(2, 0, 0, 0, Color.BLACK);
        setBorder(BorderFactory.createCompoundBorder(rightBorder, paddingBorder));
        add("Text", textPanel);
        add("File", consoleFilePanel);
    }


    public void repaintPanel(VAlgorithmAbs view) {
        textPanel(view);

        filePanel(view);
    }

    private void textPanel(VAlgorithmAbs cipher) {
        setComponentAt(0, textPanel); // Replace the component
        setTitleAt(0, "Text"); // Ensure title consistency
        revalidate(); // Revalidate layout
        repaint();
    }

    private void filePanel(VAlgorithmAbs cipher) {
        add("File", consoleFilePanel);
        consoleFilePanel = new VSignFilePanel(c);
        setComponentAt(1, consoleFilePanel); // Replace the component
        setTitleAt(1, "File"); // Ensure title consistency
        revalidate(); // Revalidate layout
        repaint();
    }
}
