package main.view.AlgorithmPanel;

import main.model.common.Algorithms;
import main.view.font.MyFont;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.text.NumberFormat;

import static main.model.common.Button.FONTSIZE_NORMAL;
import static main.view.font.MyFont.ROBOTO_REGULAR;

public class VClassicPanel extends VAlgorithmAbs {
    private JButton encryptBrowse, decryptBrowse;
    private JTextField encryptPath, decryptPath;
    private JPanel keyAndDesPanel, keyPanel, desPanel, alphabetsPanel, foreignPanel;
    private Font keyFont;

    public VClassicPanel() {
        super();
        name = "Classic";
    }

    @Override
    protected void init() {
        keyFont = MyFont.loadCustomFont(ROBOTO_REGULAR, FONTSIZE_NORMAL);

        algorithms.addItem(Algorithms.AFFINE);
        algorithms.addItem(Algorithms.VIGENCE);
        algorithms.addItem(Algorithms.HILL);
        algorithms.addItem(Algorithms.SUBSTITUTION);
        algorithms.addItem(Algorithms.TRANSPOSITION);

        algorithms.setSelectedIndex(0);
        algorithms.setEditable(false);
        algorithms.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        keyAndDesPanel = new JPanel();
        foreignPanel = new JPanel();
        foreignPanel.setLayout(new BorderLayout());
        foreignPanel.setBorder(BorderFactory.createTitledBorder("Foreign Characters"));
        JComboBox<String> foreignCharacters = new JComboBox<>();
        foreignCharacters.setFont(keyFont);
        foreignCharacters.addItem("Include");
        foreignCharacters.addItem("Ignore");
        foreignCharacters.setEditable(false);
        foreignCharacters.setSelectedIndex(0);
        foreignPanel.add(foreignCharacters, BorderLayout.CENTER);

        alphabetsPanel = new JPanel();
        alphabetsPanel.setLayout(new BorderLayout());
        alphabetsPanel.setBorder(BorderFactory.createTitledBorder("Alphabets"));
        JTextField jTextField = new JTextField();
        jTextField.setFont(keyFont);
        jTextField.setFocusable(false);
        jTextField.setEditable(false);
        alphabetsPanel.add(jTextField, BorderLayout.CENTER);
    }

    @Override
    protected void designLayout() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        ((JLabel) algorithms.getRenderer()).setVerticalAlignment(JLabel.CENTER);
        ;

        add(algorithms, BorderLayout.NORTH);
        rebuildPanel();
        setEvent();
    }

    private void setEvent() {
        algorithms.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                rebuildPanel();
            }
        });
    }


    private void rebuildPanel() {
        keyAndDesPanel.removeAll();
        keyAndDesPanel.repaint();
        switch ((String) this.algorithms.getSelectedItem()) {
            case Algorithms.AFFINE:
                createAffinePanel();
                break;
            case Algorithms.HILL:
                createHillPanel();
                break;
            case Algorithms.SUBSTITUTION:
                createSubstitutionPanel();
                break;
            case Algorithms.TRANSPOSITION:
                createTransportation();
                break;
            case Algorithms.VIGENCE:
                createVergenerePanel();
                break;
        }


    }

    public void rebuildHillPanel(String size, JPanel inputKeyPanel) {
        inputKeyPanel.removeAll();
        inputKeyPanel.repaint();
        System.out.println(size);
        switch (size) {
            case "2x2":
                System.out.println("2x2");
                inputKeyPanel.setLayout(new GridLayout(2, 2));
                for (int i = 0; i < 4; i++) {
                    NumberFormat numberFormat = NumberFormat.getIntegerInstance();
                    JFormattedTextField numberField = new JFormattedTextField(numberFormat);
                    numberField.setFont(keyFont);
                    inputKeyPanel.add(numberField);
                }
                break;
            case "3x3":
                inputKeyPanel.setLayout(new GridLayout(3, 3));
                for (int i = 0; i < 9; i++) {
                    NumberFormat numberFormat = NumberFormat.getIntegerInstance();
                    JFormattedTextField numberField = new JFormattedTextField(numberFormat);
                    numberField.setFont(keyFont);
                    inputKeyPanel.add(numberField);
                }
                break;
            case "4x4":
                inputKeyPanel.setLayout(new GridLayout(4, 4));
                for (int i = 0; i < 16; i++) {
                    NumberFormat numberFormat = NumberFormat.getIntegerInstance();
                    JFormattedTextField numberField = new JFormattedTextField(numberFormat);
                    numberField.setFont(keyFont);
                    inputKeyPanel.add(numberField);
                }
                break;
        }
    }

    private void createHillPanel() {
        keyAndDesPanel.setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));
        keyAndDesPanel.setLayout(new GridLayout(1, 2));
        //key panel
        JComboBox<String> keySize = new JComboBox<>();
        keyPanel = new JPanel();
        keySize.addItem("2x2");
        keySize.addItem("3x3");
        keySize.addItem("4x4");
        keySize.setFont(keyFont);
        keySize.setEditable(false);
        keySize.setSelectedIndex(0);
        keySize.setBorder(BorderFactory.createTitledBorder("Key Size"));
        keyPanel.setLayout(new BorderLayout());
        keyPanel.add(keySize, BorderLayout.NORTH);
        JPanel inputKeyPanel = new JPanel();
        keySize.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                rebuildHillPanel((String) keySize.getSelectedItem(), inputKeyPanel);
            }
        });
        rebuildHillPanel((String) keySize.getSelectedItem(), inputKeyPanel);
        keyPanel.add(inputKeyPanel, BorderLayout.CENTER);
        keyAndDesPanel.add(keyPanel);

        //des panel
        JPanel desPanel = new JPanel();
        desPanel.setLayout(new BoxLayout(desPanel, BoxLayout.Y_AXIS));
        desPanel.add(alphabetsPanel);
        desPanel.add(foreignPanel);
        keyAndDesPanel.add(desPanel);
        add(keyAndDesPanel, BorderLayout.CENTER);
    }

    private void createTransportation() {
        keyAndDesPanel.setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));
        keyAndDesPanel.setLayout(new GridLayout(3, 1));
        keyPanel = new JPanel();
        JSpinner keyField = new JSpinner();
        keyField.setPreferredSize(new Dimension(200, 100));
        keyField.setBorder(BorderFactory.createTitledBorder("Shift"));
        keyField.setFont(keyFont);
        keyPanel.setLayout(new BorderLayout());
        keyPanel.add(keyField, BorderLayout.CENTER);
        keyAndDesPanel.add(keyPanel);
        keyAndDesPanel.add(alphabetsPanel);
        keyAndDesPanel.add(foreignPanel);
        add(keyAndDesPanel, BorderLayout.CENTER);
    }

    private void createSubstitutionPanel() {
        keyAndDesPanel.setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));
        keyAndDesPanel.setLayout(new GridLayout(3, 1));
        keyPanel = new JPanel();
        JTextField keyField = new JTextField();
        keyField.setPreferredSize(new Dimension(200, 100));
        keyField.setBorder(BorderFactory.createTitledBorder("Cipher alphabet"));
        //TODO: Add event to keyField
        //TODO: Insert default text
        keyField.setFont(keyFont);
        keyPanel.setLayout(new BorderLayout());
        keyPanel.add(keyField, BorderLayout.CENTER);
        keyAndDesPanel.add(keyPanel);
        keyAndDesPanel.add(alphabetsPanel);
        keyAndDesPanel.add(foreignPanel);
        add(keyAndDesPanel, BorderLayout.CENTER);
    }

    private void createAffinePanel() {
        keyAndDesPanel.setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));
        keyAndDesPanel.setLayout(new GridLayout(3, 1));
        keyPanel = new JPanel();
        keyPanel.setLayout(new GridLayout(1, 2));
        JPanel aPanel = new JPanel();
        JPanel bPanel = new JPanel();
        JSpinner aField = new JSpinner();
        aField.setPreferredSize(new Dimension(200, 100));
        aField.setBorder(BorderFactory.createTitledBorder("Slope / A"));
        aField.setFont(keyFont);
        aPanel.setLayout(new BorderLayout());
        aPanel.add(aField, BorderLayout.CENTER);
        JSpinner bField = new JSpinner();
        bField.setPreferredSize(new Dimension(200, 100));
        bField.setBorder(BorderFactory.createTitledBorder("Intercept / B"));
        bField.setFont(keyFont);
        bPanel.setLayout(new BorderLayout());
        bPanel.add(bField, BorderLayout.CENTER);
        keyPanel.add(aPanel);
        keyPanel.add(bPanel);
        keyAndDesPanel.add(keyPanel);
        keyAndDesPanel.add(alphabetsPanel);
        keyAndDesPanel.add(foreignPanel);
        add(keyAndDesPanel, BorderLayout.CENTER);
    }

    private void createVergenerePanel() {
        keyAndDesPanel.setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));
        Font keyFont = MyFont.loadCustomFont(ROBOTO_REGULAR, FONTSIZE_NORMAL);
        keyAndDesPanel.setLayout(new GridLayout(4, 1));
        keyPanel = new JPanel();
        JTextField keyField = new JTextField();
        keyField.setPreferredSize(new Dimension(200, 100));
        keyField.setBorder(BorderFactory.createTitledBorder("Key"));
        keyPanel.setFont(keyFont);
        keyPanel.setLayout(new BorderLayout());
        keyPanel.add(keyField, BorderLayout.CENTER);
        keyAndDesPanel.add(keyPanel);

        JPanel keyModelPanel = new JPanel();
        JComboBox<String> keyModel = new JComboBox<>();
        keyModel.addItem("Auto key");
        keyModel.addItem("Repeat");
        keyModel.setFont(keyFont);
        keyModel.setEditable(false);
        keyModel.setSelectedIndex(0);
        keyModel.setBorder(BorderFactory.createTitledBorder("Key Model"));
        keyModelPanel.setLayout(new BorderLayout());
        keyModelPanel.add(keyModel, BorderLayout.CENTER);
        keyAndDesPanel.add(keyModelPanel);
        keyAndDesPanel.add(alphabetsPanel);

        keyAndDesPanel.add(foreignPanel);
        add(keyAndDesPanel, BorderLayout.CENTER);
    }

}
