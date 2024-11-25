package view.worker.impl;

import com.formdev.flatlaf.ui.FlatProgressBarUI;
import controller.MainController;
import view.VMainPanel;
import view.worker.IWorker;

import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;

public class SaveKeyWorker extends SwingWorker<Void, Void> implements IWorker {
    private MainController controller;
    private VMainPanel vMainPanel;
    private JDialog dialog;
    private JProgressBar progressBar;
    private File key;

    public SaveKeyWorker(MainController controller, JPanel toolPanel, File key) {
        this.vMainPanel = (VMainPanel) toolPanel.getParent();
        this.controller = controller;
        this.key = key;
        init();
    }

    @Override
    protected Void doInBackground() throws Exception {
        dialog.setVisible(true);
        try {
            controller.saveKey(key);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    @Override
    protected void done() {
//        controller.notifyAlgorithmObservers();
        vMainPanel.setEnabled(true);
        dialog.dispose();
    }

    @Override
    public void init() {
        this.vMainPanel.setEnabled(false);
        createDialog();
        createProgressBar();
        dialog.setVisible(true);
    }

    @Override
    public JDialog createDialog() {
        dialog = new JDialog();
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialog.setSize(450, 150);
        dialog.setLocationRelativeTo(null);
//        dialog.setUndecorated(true); // Removes default window decorations

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setBackground(new Color(245, 245, 245)); // Light background color

        JLabel titleLabel = new JLabel(GEN_KEY_LABEL, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        titleLabel.setForeground(new Color(60, 63, 65)); // Darker text color
        panel.add(titleLabel, BorderLayout.NORTH);

        panel.add(createProgressBar(), BorderLayout.CENTER);

        dialog.add(panel);
        dialog.setVisible(true);

        return dialog;
    }

    @Override
    public JProgressBar createProgressBar() {
        progressBar = new JProgressBar();
        progressBar.setUI(new FlatProgressBarUI() {
            @Override
            protected void paintDeterminate(Graphics g, JComponent c) {
                super.paintDeterminate(g, c);
            }

            @Override
            protected void paintIndeterminate(Graphics g, JComponent c) {
                super.paintIndeterminate(g, c);
            }
        });
        progressBar.setIndeterminate(true); // Indeterminate progress
        progressBar.setStringPainted(true); // Enable string display
        progressBar.setString(LOADING_LABEL); // Default string
        progressBar.setForeground(new Color(0, 123, 255)); // Blue progress color
        progressBar.setBackground(new Color(230, 230, 230)); // Light background
        progressBar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding
        return progressBar;
    }
}
