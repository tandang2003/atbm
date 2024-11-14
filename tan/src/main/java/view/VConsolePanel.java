package view;


import view.console.VConsoleFilePanel;
import view.console.VConsoleTextPanel;
import view.font.MyFont;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;

import static model.common.Button.*;
import static view.font.MyFont.ROBOTO_REGULAR;


public class VConsolePanel extends JTabbedPane {
//    private Font font;
    private VConsoleTextPanel textPanel;
    private VConsoleFilePanel consoleFilePanel;


    public VConsolePanel() {
        setMinimumSize(new Dimension(1000, 300));
        setSize(new Dimension(1000, 300));
//        font = MyFont.loadCustomFont(ROBOTO_REGULAR, FONTSIZE_NORMAL);
        consoleFilePanel = new VConsoleFilePanel();
        textPanel = new VConsoleTextPanel();
        Border paddingBorder = new EmptyBorder(10, 10, 10, 8);
        Border rightBorder = new MatteBorder(2, 0, 0, 0, Color.BLACK);
        setBorder(BorderFactory.createCompoundBorder(rightBorder, paddingBorder));
        add("Text", textPanel);
        add("File", consoleFilePanel);
    }


}
