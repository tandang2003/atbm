package view.console.file;

import controller.MainController;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class VHashFilePanel extends VFileAbs {


    public VHashFilePanel(MainController controller) {
        super(controller);
    }
    private JTextField checkedText;
    @Override
    protected void initInput() {
        super.initInput();
    }

    @Override
    protected void initOutput() {
        outputFile = new JTextField(30);
//        browseOutput = new JButton("Browse");
//        browseOutput.addActionListener(e -> {
//            JFileChooser fileChooser = new JFileChooser();
//            fileChooser.setDialogTitle("Specify a FOLDER to save");
//            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//            fileChooser.setAcceptAllFileFilterUsed(false);
//            int result = fileChooser.showSaveDialog(null);
//            if (result == JFileChooser.APPROVE_OPTION) {
//                File file = fileChooser.getSelectedFile();
//                outputFile.setText(file.getAbsolutePath());
//            }
//        });
    }

    @Override
    protected void initButtonPanel() {
        super.initButtonPanel();
        encryptButton.setText("Hash");
        decryptButton.setText("Check");
    }

    @Override
    protected void initDragDrop() {
        super.initDragDrop();
    }

    @Override
    protected void initInputPanel() {
        super.initInputPanel();
        // Set up input panel with GridBagLayout
        inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

// Increase the height of the text fields
        Dimension largerSize = new Dimension(0, 40); // Set height to 40 pixels

// Add dragHereLabel at the top
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3; // Span across columns
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        inputPanel.add(dragHerePanel, gbc);

// Add input file row with browse button
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0.1; // Minimal space for the label
        gbc.anchor = GridBagConstraints.LINE_START;
        inputPanel.add(new JLabel("Input File:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0; // Text field takes up more space
        gbc.gridwidth = 1;
        inputFile.setPreferredSize(largerSize);
        inputPanel.add(inputFile, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0.0; // Button has minimal width
        gbc.gridwidth = 1;
        inputPanel.add(browseInput, gbc);

// Add output file row without browse button
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.1; // Minimal space for the label
        gbc.gridwidth = 1;
        inputPanel.add(new JLabel("Output File:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0; // Text field takes remaining space
        gbc.gridwidth = 2; // Span across remaining columns
        outputFile.setPreferredSize(largerSize);
        inputPanel.add(outputFile, gbc);

// Add destination file row without browse button
        JTextField destinationFile = new JTextField();

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.1; // Minimal space for the label
        gbc.gridwidth = 1;
        inputPanel.add(new JLabel("Check hashed text:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0; // Text field takes remaining space
        gbc.gridwidth = 2; // Span across remaining columns
        destinationFile.setPreferredSize(largerSize);
        inputPanel.add(destinationFile, gbc);

// Add inputPanel to the main container (JFrame or parent panel)
        add(inputPanel, BorderLayout.CENTER);

    }

    @Override
    protected void encrypt() {
        super.encrypt();
        String input = inputFile.getText();

        File inputFile = new File(input);
        if (!inputFile.exists())
            JOptionPane.showMessageDialog(controller.getFrame(), "Input file does not exist", "Error", JOptionPane.ERROR_MESSAGE);

        try {
            String op=controller.getAlgorithms().signOrHashFile(input);
            outputFile.setText(op);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(controller.getFrame(), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

        }
    }

    @Override
    protected void decrypt() {
        super.decrypt();
        String input = inputFile.getText();
        String checked = checkedText.getText();

        File inputFile = new File(input);
        if (!inputFile.exists())
            JOptionPane.showMessageDialog(controller.getFrame(), "Input file does not exist", "Error", JOptionPane.ERROR_MESSAGE);

        try {
            String op=controller.getAlgorithms().signOrHashFile(input);
            outputFile.setText(op);
            if (op.equals(checked))
                JOptionPane.showMessageDialog(controller.getFrame(), "Hashes are the same", "Success", JOptionPane.INFORMATION_MESSAGE);
            else
                JOptionPane.showMessageDialog(controller.getFrame(), "Hashes are different", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(controller.getFrame(), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }
}
