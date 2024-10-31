package view;


import view.Event.impl.LoadKeyEvent;

import javax.swing.*;
import java.awt.*;

import static model.common.Algorithms.*;
import static model.common.Button.*;
import static model.common.Mode.DECRYPT;
import static model.common.Mode.ENCRYPT;

public class VToolPanel extends JPanel {
    private JButton loadKey, genKey, saveKey, loadFile;
    private JComboBox<String> mode;
    private JComboBox<String> select;

    public VToolPanel() {
        init();
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding around the panel
        setLayout(new GridBagLayout());
        layoutComponents();
        loadKeyEvent();
    }

    private void layoutComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(10, 0, 10, 0); // Add spacing between components
        gbc.anchor = GridBagConstraints.CENTER;

        add(genKey, gbc);
        add(loadKey, gbc);
        add(saveKey, gbc);
        add(loadFile, gbc);
        add(mode, gbc);
        add(select, gbc);
    }

    private void init() {
        initMode();
        initSelect();
        loadKey = new JButton(LOAD_KEY);
        genKey = new JButton(GENERATE_KEY);
        saveKey = new JButton(SAVE_KEY);
        loadFile = new JButton(LOAD_FILE);
        Dimension buttonSize = new Dimension(150, 30);
        loadKey.setPreferredSize(buttonSize);
        genKey.setPreferredSize(buttonSize);
        saveKey.setPreferredSize(buttonSize);
        loadFile.setPreferredSize(buttonSize);

        Dimension comboBoxSize = new Dimension(150, 30);
        mode.setPreferredSize(comboBoxSize);
        select.setPreferredSize(comboBoxSize);
    }

    private void initSelect() {
        select = new JComboBox<>();
        select.addItem(ENCRYPT.name());
        select.addItem(DECRYPT.name());
    }

    private void initMode() {
        mode = new JComboBox<>();
        mode.addItem(TRANSPOSITION.name());
        mode.addItem(SUBSTITUTION.name());
        mode.addItem(AFFINE.name());
        mode.addItem(VIGENCE.name());
        mode.addItem(HILL.name());
        mode.addItem(AES.name());
        mode.addItem(DES.name());
    }

    private void loadKeyEvent() {
        loadKey.addActionListener(new LoadKeyEvent());
    }
}
