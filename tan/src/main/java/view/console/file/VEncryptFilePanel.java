package view.console.file;

import controller.MainController;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class VEncryptFilePanel extends VFileAbs {
    public VEncryptFilePanel(MainController controller) {
        super(controller);
    }

    @Override
    protected void initInputPanel() {
        super.initInputPanel();
// Set up input panel with GridBagLayout
        inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

// Increase field height
        Dimension largerSize = new Dimension(0, 40); // Set a preferred height of 40 pixels

// Add dragHereLabel at the top
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3; // Span across columns
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        inputPanel.add(dragHerePanel, gbc);

// Add input file row
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0.1;
        gbc.anchor = GridBagConstraints.LINE_START;
        inputPanel.add(new JLabel("Input File:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        inputFile.setPreferredSize(largerSize);
        inputPanel.add(inputFile, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0.1;
        inputPanel.add(browseInput, gbc);

// Add output file row
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.1;
        gbc.gridwidth = 1;
        inputPanel.add(new JLabel("Output File:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        outputFile.setPreferredSize(largerSize);
        inputPanel.add(outputFile, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0.1;
        inputPanel.add(browseOutput, gbc);

// Add inputPanel to the main container (JFrame or parent panel)
        add(inputPanel, BorderLayout.CENTER);

    }

    @Override
    protected void encrypt() {
        super.encrypt();
        String input = inputFile.getText();
        String output = outputFile.getText();

        File inputFile = new File(input);
        File outputFile = new File(output);
        try {
            if (!inputFile.exists() && inputFile.isFile()) {
                throw new FileNotFoundException("Input file not found");
            }
            if (!outputFile.exists() && outputFile.isDirectory()) {
                throw new FileNotFoundException("Output folder not found");
            }
            controller.getAlgorithms().encryptFile(input, output);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(controller.getFrame(), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }


    }

    @Override
    protected void decrypt() {
        super.decrypt();
        String input = inputFile.getText();
        String output = outputFile.getText();
        File inputFile = new File(input);
        File outputFile = new File(output);
        try {
            if (!inputFile.exists() && inputFile.isFile()) {
                throw new FileNotFoundException("Input file not found");
            }
            if (!outputFile.exists() && outputFile.isDirectory()) {
                throw new FileNotFoundException("Output folder not found");
            }
            controller.getAlgorithms().decryptFile(input, output);
            System.out.println(123);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(controller.getFrame(), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(controller.getFrame(), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
