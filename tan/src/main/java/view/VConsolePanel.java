package view;


import controller.MainController;
import model.algorithms.modernEncryption.BcryptHashAlgorithm;
import view.algorithmPanel.*;
import view.console.VConsolePanelAbs;
import view.console.file.VEncryptFilePanel;
import view.console.file.VHashFilePanel;
import view.console.file.VSignFilePanel;
import view.console.text.VClassicTextPanel;
import view.console.text.VHashTextPanel;
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
        consoleFilePanel = new VEncryptFilePanel(controller);
        textPanel = new VClassicTextPanel(controller);
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
        if (cipher instanceof VHashPanel) {
            textPanel = new VHashTextPanel(c);
        } else if (cipher instanceof VSignPanel) {
            textPanel = new VSignTextPanel(c);
        } else if (
                cipher instanceof VClassicPanel ||
                        cipher instanceof VAsymmetricPanel ||
                        cipher instanceof VSymmetricPanel ||
                        cipher instanceof VBlockPanel ||
                        cipher instanceof VSignPanel) {
            textPanel = new VClassicTextPanel(c);
        }
        setComponentAt(0, textPanel); // Replace the component
        setTitleAt(0, "Text"); // Ensure title consistency
        revalidate(); // Revalidate layout
        repaint();
    }

    private void filePanel(VAlgorithmAbs cipher) {
        if (cipher instanceof VClassicPanel ||
                cipher instanceof VAsymmetricPanel ||
                (cipher instanceof VHashPanel && c.getAlgorithms() instanceof BcryptHashAlgorithm)) {
            if (getTabCount() == 2) {
                removeTabAt(1);
            }
            ;
            return;
        } else if (cipher instanceof VHashPanel) {
            add("File", consoleFilePanel);
            consoleFilePanel = new VHashFilePanel(c);
        } else if (cipher instanceof VSignPanel) {
            add("File", consoleFilePanel);
            consoleFilePanel = new VSignFilePanel(c);
        } else {
            add("File", consoleFilePanel);
            consoleFilePanel = new VEncryptFilePanel(c);
        }
        setComponentAt(1, consoleFilePanel); // Replace the component
        setTitleAt(1, "File"); // Ensure title consistency
        revalidate(); // Revalidate layout
        repaint();
    }
}
