package view.console;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.util.List;

public class VConsoleFilePanel extends JPanel {
    //    private JRadioButton encryptButton, decryptButton;
    private JButton start;
    private JTextArea inputArea, outputArea;

    public VConsoleFilePanel() {
        inputArea = new JTextArea();
        init();
    }

    private void init() {
        inputArea.setDragEnabled(true);
        inputArea.setLineWrap(true);
        inputArea.setWrapStyleWord(true);
        inputArea.setDropMode(DropMode.INSERT);
        inputArea.setDropTarget(new DropTarget() {
            public synchronized void drop(DropTargetDropEvent evt) {
                try {
                    evt.acceptDrop(DnDConstants.ACTION_COPY);
                    List<File> droppedFiles = (List<File>)
                            evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    for (File file : droppedFiles) {
                        // process files
                    }
                    evt.dropComplete(true);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        add(new JScrollPane(inputArea), BorderLayout.CENTER);
    }
}
