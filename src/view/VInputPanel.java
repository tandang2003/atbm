package view;


import javax.swing.*;
import java.awt.*;

import static common.Button.*;

public class VInputPanel extends JPanel {
    private JButton start;
    private JCheckBox file;
    private JTextField input, key;

    public VInputPanel() {
        init();
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding around the panel
        setLayout(new GridBagLayout());
        layoutComponents();
        addFileEvent();
    }

    private void layoutComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(10, 0, 10, 0); // Add spacing between components
        gbc.anchor = GridBagConstraints.WEST;
        add(input, gbc);
        add(file, gbc);
        add(start, gbc);
    }

    private void init() {
        start = new JButton(ENCRYPT);
        file = new JCheckBox(FILE);
        input = new JTextField();
        input.setMinimumSize(new Dimension(350, 30));
        input.setPreferredSize(new Dimension(350, 30));
    }

    public void addFileEvent() {
//        file.addActionListener(new LoadFileInputEvent());
    }
}
