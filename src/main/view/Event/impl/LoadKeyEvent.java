package main.view.Event.impl;

import main.view.Event.IFileEvent;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class LoadKeyEvent implements IFileEvent {
    //    private JFileChooser fileChooser;
//    private JCheckBox isSelectFile;
//    private JTextField input;
//    private JOptionPane dialog;

    public LoadKeyEvent() {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int selection = JOptionPane.showConfirmDialog(null, "Do you want load key form file");
        if (selection == JOptionPane.YES_OPTION) {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                JOptionPane.showMessageDialog(null, "File selected: " + fileChooser.getSelectedFile().getAbsolutePath());
            }
        } else if (selection == JOptionPane.NO_OPTION) {
            String key=JOptionPane.showInputDialog(null,"Fill key");

        } else {
            JOptionPane.showMessageDialog(null, "You selected Cancel");
        }

    }

    @Override
    public void onFileAccept() {

    }

    @Override
    public void onFileCancel() {

    }

    @Override
    public void onFileError() {

    }
    //
}
