package view;


import controller.MainController;
import view.Event.impl.LoadKeyEvent;
import view.font.MyFont;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.io.File;

import static model.common.Button.*;

public class VToolPanel extends JPanel {
    private JButton loadKey, genKey, saveKey, loadFile;
    private Font font;
    private MainController controller;

    public VToolPanel(MainController controller) {
        this.controller = controller;
        font = MyFont.loadCustomFont(MyFont.ROBOTO_REGULAR, FONTSIZE_NORMAL);
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
        genKeyEvent();
        saveKeyEvent();
    }

    private void genKeyEvent() {
        genKey.addActionListener(e -> {
            controller.genKey();
        });
    }

    private void saveKeyEvent() {

        saveKey.addActionListener(e -> {
//            controller.saveKey();
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Specify a folder to save");
            fileChooser.setCurrentDirectory(new File("."));
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fileChooser.setAcceptAllFileFilterUsed(false);
            if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
//                System.out.println("getCurrentDirectory(): " + fileChooser.getCurrentDirectory());
                controller.saveKey(fileChooser.getSelectedFile());
//                System.out.println("getSelectedFile() : " + fileChooser.getSelectedFile());
            } else {
                System.out.println("No Selection ");
            }

        });
    }

    private void loadKeyEvent() {
        loadKey.addActionListener(new LoadKeyEvent());
    }
}
