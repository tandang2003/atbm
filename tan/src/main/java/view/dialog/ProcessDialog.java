package view.dialog;

import com.formdev.flatlaf.ui.FlatProgressBarUI;
import com.formdev.flatlaf.ui.FlatSpinnerUI;
import model.common.Cipher;

import javax.swing.*;
import java.awt.*;

public class ProcessDialog extends JDialog {
    private JProgressBar progressBar;
    private JLabel label;
    private JButton cancelButton;
    private SwingWorker<Void, Void> worker;

    public ProcessDialog(JFrame parent, String title, String message) {
        super(parent, title, ModalityType.APPLICATION_MODAL);
        this.setLocationRelativeTo(parent);
        this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        this.setResizable(false);
//        this.setUndecorated(true);
        this.setLayout(new BorderLayout());
//        this.add(createPanel(message), BorderLayout.CENTER);
//        this.add(createCancelButton(), BorderLayout.SOUTH);
        this.pack();
    }

    public void setWorker(SwingWorker<Void, Void> worker) {
        this.worker = worker;
    }

    private JPanel createPanel(String message) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(Box.createVerticalGlue());
        label = new JLabel(message);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        progressBar = new JProgressBar();
        progressBar.setUI(new FlatProgressBarUI());
        progressBar.setIndeterminate(true);
        progressBar.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(progressBar);
        panel.add(Box.createVerticalGlue());
        return panel;
    }

    private JPanel createCancelButton() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(Box.createHorizontalGlue());
        cancelButton = new JButton("Cancel");
//        cancelButton.setUI(new FlatSpinnerUI());
        panel.add(cancelButton);
        return panel;
    }
    public void start() {
//        this.setVisible(true);
        worker.execute();
    }
}
