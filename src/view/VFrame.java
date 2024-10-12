package view;

import javax.swing.*;
import java.awt.*;

public class VFrame extends JFrame {
    VMainPanel vMainPanel;

    public VFrame() {
        vMainPanel = new VMainPanel();
        add(vMainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(800, 700));
        setPreferredSize(new Dimension(800, 700));
        setLocationRelativeTo(null);
        setVisible(true);

    }

    public static void main(String[] args) {
        new VFrame();
    }
}
