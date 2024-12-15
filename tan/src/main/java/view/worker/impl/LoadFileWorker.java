package view.worker.impl;

import com.formdev.flatlaf.ui.FlatProgressBarUI;
import controller.MainController;
import view.VMainPanel;
import view.worker.IWorker;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class LoadFileWorker extends SwingWorker<Void, Void> implements IWorker {
    private MainController controller;
    private VMainPanel vMainPanel;
    private JDialog dialog;
    private JProgressBar progressBar;
    private File key;
    private boolean isError, isPublicKey;
    private String message;

    public LoadFileWorker(MainController controller, JPanel toolPanel, File key, boolean isPublicKey) {
        this.vMainPanel = (VMainPanel) toolPanel.getParent();
        this.controller = controller;
        this.key = key;
        this.isPublicKey = isPublicKey;
        init();

    }

    @Override
    protected Void doInBackground() throws Exception {
        try {
            controller.loadKey(key, isPublicKey);
            isError = false;
        } catch (IOException ex) {
            isError = true;
            message = ex.getMessage();
        }
        return null;
    }


    @Override
    protected void done() {
        if (isError) {
            dialog.dispose();
            JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
            vMainPanel.setEnabled(true);
            return;
        }
        dialog.dispose();
        controller.notifyAlgorithmObservers();
        vMainPanel.setEnabled(true);
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

