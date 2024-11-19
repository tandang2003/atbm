package view;


import com.formdev.flatlaf.ui.FlatProgressBarUI;
import controller.MainController;
import view.font.MyFont;

import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;

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
            controller.getFrame().getvMainPanel().setEnabled(false);
//                    controller.genKey();
            JDialog dialog = createDialog("Generating key...");
            new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() {
                    dialog.setVisible(true);
                    try {
                        controller.genKey();
                    } catch (IllegalBlockSizeException | NoSuchPaddingException |
                             InvalidKeyException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    for (int i = 0; i < 999999999; i++) {
                        System.out.println("Progress Bar: " + i);
                    }
                    return null;
                }

                @Override
                protected void done() {
                    controller.getFrame().getvMainPanel().setEnabled(true);
                    dialog.dispose();
                }
            }.execute();


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

    private JDialog createDialog(String label) {
        // Create a JDialog
        JDialog dialog = new JDialog();
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialog.setSize(450, 150);
        dialog.setLocationRelativeTo(null);
        dialog.setUndecorated(true); // Removes default window decorations

        // Create a panel with a border for aesthetics
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setBackground(new Color(245, 245, 245)); // Light background color

        // Add label above the progress bar
        JLabel titleLabel = new JLabel(label, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        titleLabel.setForeground(new Color(60, 63, 65)); // Darker text color
        panel.add(titleLabel, BorderLayout.NORTH);

        // Add a beautiful progress bar
        panel.add(createProgressBar(), BorderLayout.CENTER);

        // Add panel to the dialog
        dialog.add(panel);
        dialog.setVisible(true);

        return dialog;
    }

    private JProgressBar createProgressBar() {
        // Create a JProgressBar
        JProgressBar progressBar = new JProgressBar();
        progressBar.setUI(new FlatProgressBarUI() {
            @Override
            protected void paintDeterminate(Graphics g, JComponent c) {
                super.paintDeterminate(g, c);
                // Add custom rounded edges or gradient styling here if desired
            }

            @Override
            protected void paintIndeterminate(Graphics g, JComponent c) {
                super.paintIndeterminate(g, c);
                // Add custom rounded edges or gradient styling here if desired
            }
        });
        progressBar.setIndeterminate(true); // Indeterminate progress
        progressBar.setStringPainted(true); // Enable string display
        progressBar.setString("Loading..."); // Default string
        progressBar.setForeground(new Color(0, 123, 255)); // Blue progress color
        progressBar.setBackground(new Color(230, 230, 230)); // Light background
        progressBar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding
        return progressBar;
    }
//    private void loadKeyEvent() {
//        loadKey.addActionListener(new LoadKeyEvent());
//    }
}
