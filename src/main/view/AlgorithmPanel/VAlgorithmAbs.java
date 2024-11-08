package main.view.AlgorithmPanel;

import main.model.common.Button;
import main.view.font.MyFont;

import javax.swing.*;
import java.awt.*;

import static main.model.common.Button.FONTSIZE_NORMAL;
import static main.view.font.MyFont.ROBOTO_BOLD;

public abstract class VAlgorithmAbs extends JPanel {
    protected String name;
    protected JComboBox<String> algorithms;
    protected Font font;

    public VAlgorithmAbs() {
        algorithms = new JComboBox<>();
        font = MyFont.loadCustomFont(ROBOTO_BOLD, FONTSIZE_NORMAL);
        algorithms.setFont(font);
        init();
        designLayout();
        setPreferredSize(new java.awt.Dimension(100, 150));
//        setBackground(new java.awt.Color(255, 255, 255));
    }

    protected abstract void init();

    protected abstract void designLayout();

    public String getName() {
        return name;
    }
}
