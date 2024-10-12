package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.beans.EventHandler;

public class VMainPanel extends JPanel {
    VConsolePanel vConsolePanel;
    VToolPanel vToolPanel;
    VInputPanel vInputPanel;

    public VMainPanel() {
        init();
    }

    private void init() {
        vConsolePanel = new VConsolePanel();
        vToolPanel = new VToolPanel();
        vInputPanel = new VInputPanel();
        setLayout(new BorderLayout());
        add(vConsolePanel, BorderLayout.SOUTH);
        add(vToolPanel, BorderLayout.WEST);
        add(vInputPanel, BorderLayout.CENTER);
    }

}
