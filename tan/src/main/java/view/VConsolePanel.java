package view;


import controller.MainController;
import view.console.VConsoleFilePanel;
import view.console.VConsoleTextPanel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;


public class VConsolePanel extends JTabbedPane {
    private VConsoleTextPanel textPanel;
    private VConsoleFilePanel consoleFilePanel;


    public VConsolePanel(MainController controller) {
        setMinimumSize(new Dimension(1000, 300));
        setSize(new Dimension(1000, 300));
//        font = MyFont.loadCustomFont(ROBOTO_REGULAR, FONTSIZE_NORMAL);
        consoleFilePanel = new VConsoleFilePanel(controller);
        textPanel = new VConsoleTextPanel(controller);
        Border paddingBorder = new EmptyBorder(10, 10, 10, 8);
        Border rightBorder = new MatteBorder(2, 0, 0, 0, Color.BLACK);
        setBorder(BorderFactory.createCompoundBorder(rightBorder, paddingBorder));
        add("Text", textPanel);
        add("File", consoleFilePanel);
    }


}
