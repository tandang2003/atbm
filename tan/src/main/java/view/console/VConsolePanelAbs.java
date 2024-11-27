package view.console;

import controller.MainController;

import javax.swing.*;
import java.awt.*;

public abstract class VConsolePanelAbs extends JPanel {
    protected JButton encryptButton, decryptButton;
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
        decryptButton = new JButton("Decrypt");
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.add(encryptButton);
        buttonPanel.add(decryptButton);

        encryptButton.addActionListener(e -> {
            encrypt();
        });
        decryptButton.addActionListener(e -> {
            decrypt();
        });

    }

    protected abstract void encrypt();

    protected abstract void decrypt();
}
