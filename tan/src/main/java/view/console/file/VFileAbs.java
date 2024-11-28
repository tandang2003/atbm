package view.console.file;

import controller.MainController;
import view.console.VConsolePanelAbs;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.*;
import java.io.File;

public abstract class VFileAbs extends VConsolePanelAbs {
    protected DragHereIcon dragHereIcon;
    protected DropTarget dropTarget;
    protected JTextField inputFile, outputFile;
    protected JButton browseInput, browseOutput;
    protected JPanel dragHerePanel;

    public VFileAbs(MainController controller) {
        super(controller);
    }

    protected void initInput() {
        inputFile = new JTextField(30);
        browseInput = new JButton("Browse");
        browseInput.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Specify a FILE to encrypt/decrypt");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setAcceptAllFileFilterUsed(false);
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                inputFile.setText(file.getAbsolutePath());
            }
        });
    }

    protected void initOutput() {
        outputFile = new JTextField(30);
        browseOutput = new JButton("Browse");
        browseOutput.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Specify a FOLDER to save");
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fileChooser.setAcceptAllFileFilterUsed(false);
            int result = fileChooser.showSaveDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                outputFile.setText(file.getAbsolutePath());
            }
        });
    }

    protected void initDragDrop() {
        dragHereIcon = new DragHereIcon();
        dragHerePanel = new JPanel();
        dragHerePanel.setLayout(new BorderLayout());
        JLabel dragHereLabel = new JLabel(dragHereIcon);
        dragHereLabel.setText("<html>Drag <b>Files</b> Here</html>");
        dragHereLabel.setVerticalTextPosition(SwingConstants.BOTTOM);
        dragHereLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        dragHereLabel.setForeground(Color.GRAY);
        dragHereLabel.setBorder(BorderFactory.createTitledBorder("Drag and Drop"));
        dragHereLabel.setFont(new Font("Monospace", Font.PLAIN, 14));
        dragHereLabel.setHorizontalAlignment(SwingConstants.CENTER);
        dropTarget = new DropTarget(dragHerePanel, new DropTargetAdapter() {
            @Override
            public void drop(DropTargetDropEvent dtde) {
                dtde.acceptDrop(DnDConstants.ACTION_COPY);
                Transferable transferable = dtde.getTransferable();
                try {
                    if (transferable.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                        java.util.List<File> files = (java.util.List<File>) transferable.getTransferData(DataFlavor.javaFileListFlavor);
                        if (files.size() > 0) {
                            File file = files.get(0);
                            inputFile.setText(file.getAbsolutePath());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        dragHerePanel.add(dragHereLabel, BorderLayout.CENTER);
        dragHerePanel.setDropTarget(dropTarget);
    }

    @Override
    protected void initInputPanel() {
        initInput();
        initOutput();
        initDragDrop();

        // Initialize input and output file fields and buttons

//        dragHereIcon = new DragHereIcon();
//
//
//        // Set up input panel with GridBagLayout
//        inputPanel = new JPanel(new GridBagLayout());
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.insets = new Insets(10, 10, 10, 10);
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//
//        // Add dragHereLabel at the top
//        gbc.gridx = 0;
//        gbc.gridy = 0;
//        gbc.gridwidth = 3; // Span across columns
//        gbc.weightx = 1.0;
//        gbc.anchor = GridBagConstraints.CENTER;
//        inputPanel.add(dragHerePanel, gbc);
//
//        // Add input file row
//        gbc.gridx = 0;
//        gbc.gridy = 1;
//        gbc.gridwidth = 1;
//        gbc.weightx = 0.1;
//        gbc.anchor = GridBagConstraints.LINE_START;
//        inputPanel.add(new JLabel("Input File:"), gbc);
//
//        gbc.gridx = 1;
//        gbc.weightx = 1.0;
//        inputPanel.add(inputFile, gbc);
//
//        gbc.gridx = 2;
//        gbc.weightx = 0.1;
//        inputPanel.add(browseInput, gbc);
//
//        // Add output file row
//        gbc.gridx = 0;
//        gbc.gridy = 2;
//        gbc.weightx = 0.1;
//        inputPanel.add(new JLabel("Output File:"), gbc);
//
//        gbc.gridx = 1;
//        gbc.weightx = 1.0;
//        inputPanel.add(outputFile, gbc);
//
//        gbc.gridx = 2;
//        gbc.weightx = 0.1;
//        inputPanel.add(browseOutput, gbc);
//
//        // Add inputPanel to the main container (JFrame or parent panel)
//        add(inputPanel, BorderLayout.CENTER);
    }

    @Override
    protected void encrypt() {
        try {
            controller.getAlgorithms().validation();
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(controller.getFrame(), "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }

    @Override
    protected void decrypt() {
        try {
            controller.getAlgorithms().validation();
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(controller.getFrame(), "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }
}
