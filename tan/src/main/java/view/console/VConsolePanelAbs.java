package view.console;

import controller.MainController;

import javax.swing.*;
import java.awt.*;

public abstract class VConsolePanelAbs extends JPanel {
    protected JButton encryptButton;
    protected JPanel inputPanel, buttonPanel;
    protected MainController controller;

    public VConsolePanelAbs(MainController controller) {
        this.controller = controller;
        init();
    }

    protected void init() {
        initInputPanel();
        initButtonPanel();

        setLayout(new BorderLayout());
        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

    }

    protected abstract void initInputPanel();

    protected void initButtonPanel() {
        buttonPanel = new JPanel();
        encryptButton = new JButton("Encrypt");
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.add(encryptButton);

        encryptButton.addActionListener(e -> {
            try {
                encrypt();
            } catch (ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(controller.getFrame(), "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

            }
        });

    }

    protected abstract void encrypt() throws ClassNotFoundException;

    protected abstract void decrypt() throws ClassNotFoundException;
}
