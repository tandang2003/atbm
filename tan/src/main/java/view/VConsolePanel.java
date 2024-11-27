package view;


import controller.MainController;
import model.common.Cipher;
import model.common.Hash;
import model.common.ICipherEnum;
import view.console.VConsolePanelAbs;
import view.console.file.VFileAbs;
import view.console.text.VClassicTextPanel;
import view.console.text.VHashTextPanel;

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
        consoleFilePanel = new VFileAbs(controller);
        textPanel = new VClassicTextPanel(controller);
        Border paddingBorder = new EmptyBorder(10, 10, 10, 8);
        Border rightBorder = new MatteBorder(2, 0, 0, 0, Color.BLACK);
        setBorder(BorderFactory.createCompoundBorder(rightBorder, paddingBorder));
        add("Text", textPanel);
        add("File", consoleFilePanel);
    }


    public void repaintPanel(ICipherEnum cipher) {

        if (cipher instanceof Hash) {
            textPanel = new VHashTextPanel(c);
        } else if (cipher instanceof Cipher) {
            textPanel = new VClassicTextPanel(c);
        }
        setComponentAt(0, textPanel); // Replace the component
        setTitleAt(0, "Text"); // Ensure title consistency
        revalidate(); // Revalidate layout
        repaint();
    }
}
