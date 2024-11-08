package main.view;


import main.view.Event.impl.LoadKeyEvent;
import main.view.font.MyFont;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;

import static main.model.common.Algorithms.*;
import static main.model.common.Button.*;

public class VToolPanel extends JPanel {
    private JButton loadKey, genKey, saveKey, loadFile;
    private Font font;
//    private JComboBox<String> mode;
//    private JComboBox<String> select;

    public VToolPanel() {
        font= MyFont.loadCustomFont(MyFont.ROBOTO_REGULAR, FONTSIZE_NORMAL);
        init();
        // Create an empty border for padding (top, left, bottom with 20 pixels)
        Border paddingBorder = new EmptyBorder(20, 20, 20, 18);

        // Matte border for the right line (2 pixels on the right, black color)
        Border rightBorder = new MatteBorder(0, 0, 0, 2, Color.BLACK);

        // Combine both borders
        setBorder(BorderFactory.createCompoundBorder(rightBorder, paddingBorder));

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
    }

    private void init() {
        loadKey = new JButton(LOAD_KEY);
        loadKey.setFont(font);
        genKey = new JButton(GENERATE_KEY);
        genKey.setFont(font);
        saveKey = new JButton(SAVE_KEY);
        saveKey.setFont(font);
        loadFile = new JButton(LOAD_FILE);
        loadFile.setFont(font);
        Dimension buttonSize = new Dimension(150, 50);
        loadKey.setPreferredSize(buttonSize);
        genKey.setPreferredSize(buttonSize);
        saveKey.setPreferredSize(buttonSize);
        loadFile.setPreferredSize(buttonSize);

//        Dimension comboBoxSize = new Dimension(150, 30);
//        mode.setPreferredSize(comboBoxSize);
//        select.setPreferredSize(comboBoxSize);
    }

//    private void initSelect() {
//        select = new JComboBox<>();
////        select.addItem(ENCRYPT.name());
////        select.addItem(DECRYPT.name());
//    }

//    private void initMode() {
//        mode = new JComboBox<>();
//        mode.addItem(TRANSPOSITION);
//        mode.addItem(SUBSTITUTION);
//        mode.addItem(AFFINE);
//        mode.addItem(VIGENCE);
//        mode.addItem(HILL);
//        mode.addItem(AES);
//        mode.addItem(DES);
//    }

    private void loadKeyEvent() {
        loadKey.addActionListener(new LoadKeyEvent());
    }
}
