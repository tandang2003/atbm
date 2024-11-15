package view;


import controller.MainController;
import view.Event.impl.LoadKeyEvent;
import view.font.MyFont;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import static model.common.Button.*;

public class VToolPanel extends JPanel {
    private JButton loadKey, genKey, saveKey;
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
    }

    private void init() {
        loadKey = new JButton(LOAD_KEY);
        loadKey.setFont(font);
        genKey = new JButton(GENERATE_KEY);
        genKey.setFont(font);
        saveKey = new JButton(SAVE_KEY);
        saveKey.setFont(font);
        Dimension buttonSize = new Dimension(150, 50);
        loadKey.setPreferredSize(buttonSize);
        genKey.setPreferredSize(buttonSize);
        saveKey.setPreferredSize(buttonSize);
        genKeyEvent();
        saveKeyEvent();
        loadKeyEvent();
    }

    private void loadKeyEvent() {
        loadKey.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Specify a folder to save");
            fileChooser.setCurrentDirectory(new File("."));
//            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.setFileFilter(new FileFilter() {
                @Override
                public boolean accept(File f) {
                    // Accept files that end with ".tan.key"
                    return f.getName().toLowerCase().endsWith(".tan.key");
                }

                @Override
                public String getDescription() {
                    return "TAN Key Files (*.tan.key)";
                }
            });
            if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                try {
                    controller.loadKey(fileChooser.getSelectedFile());
                } catch (IOException | ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
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
                try {
                    controller.saveKey(fileChooser.getSelectedFile());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

//    private void loadKeyEvent() {
//        loadKey.addActionListener(new LoadKeyEvent());
//    }
}
