package view.AlgorithmPanel;

import controller.MainController;
import model.common.*;
import model.key.AsymmetricKeyHelper;
import model.key.SymmetricKeyHelper;
import observer.alphabetObserver.AlphaObserver;
import view.dialog.ProcessDialog;
import view.font.MyFont;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.text.NumberFormat;
import java.util.Map;

import static model.common.Button.FONTSIZE_NORMAL;
import static view.font.MyFont.ROBOTO_REGULAR;

public class DetailAlgorithmPanel extends JPanel implements AlphaObserver {
    private JPanel keyPanel, foreignPanel, alphabetsPanel;
    private JButton back;
    private Font keyFont;
    //    private JComboBox<String> mode, padding, keySize;
    private JTextField alphabetField;
    private JComboBox<Size> keySize, ivSize;
    private JComboBox<Mode> keyMode;
    private JComboBox<Padding> keyPadding;
    private JComboBox<String> hillKeySize;
    private MainController controller;

    public DetailAlgorithmPanel(MainController controller) {
        this.controller = controller;
        controller.register((AlphaObserver) this);
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));
        keyFont = MyFont.loadCustomFont(ROBOTO_REGULAR, FONTSIZE_NORMAL);
        foreignPanel = new JPanel();
        foreignPanel.setLayout(new BorderLayout());
        foreignPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0), "Foreign Characters"));
        JComboBox<String> foreignCharacters = new JComboBox<>();
        foreignCharacters.setFont(keyFont);
        foreignCharacters.addItem("Include");
        foreignCharacters.addItem("Ignore");
        foreignCharacters.setEditable(false);
        foreignCharacters.setSelectedIndex(0);
        foreignPanel.add(foreignCharacters, BorderLayout.CENTER);

        alphabetsPanel = new JPanel();
        alphabetsPanel.setLayout(new BorderLayout());
        alphabetsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0), "Alphabets"));
        alphabetField = new JTextField();
        alphabetField.setFont(keyFont);
        alphabetField.setEditable(false);
        alphabetField.setBackground(Color.WHITE);
        alphabetsPanel.add(alphabetField, BorderLayout.CENTER);
    }

    public void rebuildPanel(Cipher algorithmName) {
        removeAll();
        revalidate();
        repaint();
        switch (algorithmName) {
            case Cipher.AFFINE:
                createAffinePanel();
                break;
            case Cipher.HILL:
                createHillPanel();
                break;
            case Cipher.SUBSTITUTION:
                createSubstitutionPanel();
                break;
            case Cipher.TRANSPOSITION:
                createTransportation();
                break;
            case Cipher.VIGENERE:
                createVergenerePanel();
                break;
            case Cipher.AES, Cipher.BLOWFISH, Cipher.DES, Cipher.DESEDE, Cipher.RC2, Cipher.RC4:
                createSymmetric(algorithmName);
                break;
            case Cipher.RSA:
                createAsymmetric(algorithmName);
                break;
        }
    }


    private void createHillPanel() {
        setLayout(new GridLayout(1, 2));
        //key panel
        hillKeySize = new JComboBox<>();

        JPanel inputKeyPanel = new JPanel();
        hillKeySize.addItem("2x2");
        hillKeySize.addItem("3x3");
        hillKeySize.addItem("4x4");
        hillKeySize.setFont(keyFont);
        hillKeySize.setEditable(false);
        hillKeySize.setSelectedIndex(0);
        JPanel keySizePanel = new JPanel();
        keySizePanel.setLayout(new BorderLayout());
        keySizePanel.setBorder((BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0), "Key Size")));
        keySizePanel.add(hillKeySize, BorderLayout.CENTER);
//        keySize.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0), BorderFactory.createTitledBorder("Key Size")));
        inputKeyPanel.setLayout(new BorderLayout());
        inputKeyPanel.add(keySizePanel, BorderLayout.NORTH);
        keyPanel = new JPanel();
        hillKeySize.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                rebuildHillPanel((String) hillKeySize.getSelectedItem());
            }
        });
        rebuildHillPanel((String) hillKeySize.getSelectedItem());
        inputKeyPanel.add(keyPanel, BorderLayout.CENTER);
        add(inputKeyPanel);

        //des panel
        JPanel desPanel = new JPanel();
        desPanel.setLayout(new BoxLayout(desPanel, BoxLayout.Y_AXIS));
        desPanel.add(alphabetsPanel);
        desPanel.add(foreignPanel);
        add(desPanel);
    }

    private void createTransportation() {
        setLayout(new GridLayout(3, 1));
        keyPanel = new JPanel();
        JSpinner keyField = new JSpinner();
        keyField.setPreferredSize(new Dimension(200, 100));
        keyField.setBorder(BorderFactory.createTitledBorder("Shift"));
        keyField.setFont(keyFont);
        keyPanel.setLayout(new BorderLayout());
        keyPanel.add(keyField, BorderLayout.CENTER);
        add(keyPanel);
        add(alphabetsPanel);
        add(foreignPanel);
//        add(keyAndDesPanel, BorderLayout.CENTER);
    }

    private void createSubstitutionPanel() {
        setLayout(new GridLayout(3, 1));
        keyPanel = new JPanel();
        JTextField keyField = new JTextField();
        keyField.setPreferredSize(new Dimension(200, 100));
        keyField.setBorder(BorderFactory.createTitledBorder("Cipher alphabet"));
        keyField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                controller.updateKey(keyField.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                controller.updateKey(keyField.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
        //TODO: Add event to keyField
        //TODO: Insert default text
        keyField.setFont(keyFont);
        keyPanel.setLayout(new BorderLayout());
        keyPanel.add(keyField, BorderLayout.CENTER);
        add(keyPanel);
        add(alphabetsPanel);
        add(foreignPanel);
    }

    private void createAffinePanel() {
        setLayout(new GridLayout(3, 1));
        keyPanel = new JPanel();
        keyPanel.setLayout(new GridLayout(1, 2));
        JPanel aPanel = new JPanel();
        JPanel bPanel = new JPanel();
        JSpinner aField = new JSpinner();
        aField.setPreferredSize(new Dimension(200, 100));
        aPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Slope / A"));
        aField.setFont(keyFont);
        aPanel.setLayout(new BorderLayout());
        aPanel.add(aField, BorderLayout.CENTER);
        JSpinner bField = new JSpinner();
        bField.setPreferredSize(new Dimension(200, 100));
        bPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Intercept / B"));
        bField.setFont(keyFont);
        bPanel.setLayout(new BorderLayout());
        bPanel.add(bField, BorderLayout.CENTER);
        aField.addChangeListener(e -> {
            controller.updateKey(aField.getValue(), bField.getValue());
        });
        bField.addChangeListener(e -> {
            controller.updateKey(aField.getValue(), bField.getValue());
        });
        keyPanel.add(aPanel);
        keyPanel.add(bPanel);
        add(keyPanel);
        add(alphabetsPanel);
        add(foreignPanel);
    }

    private void createVergenerePanel() {
        setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));
        Font keyFont = MyFont.loadCustomFont(ROBOTO_REGULAR, FONTSIZE_NORMAL);
        setLayout(new GridLayout(3, 1));
        keyPanel = new JPanel();
        JTextField keyField = new JTextField();
        keyField.setPreferredSize(new Dimension(200, 100));
        keyField.setBorder(BorderFactory.createTitledBorder("Key"));
        keyPanel.setFont(keyFont);
        keyPanel.setLayout(new BorderLayout());
        keyPanel.add(keyField, BorderLayout.CENTER);
        keyField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                controller.updateKey(keyField.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                controller.updateKey(keyField.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });
        add(keyPanel);

        add(alphabetsPanel);

        add(foreignPanel);
    }

    private void createSymmetric(Cipher algorithmName) {
        CipherSpecification cipherSpecification = CipherSpecification.findCipherSpecification(algorithmName);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        JPanel keyInsPanel = new JPanel();
        keyInsPanel.setLayout(new GridLayout(1, 3, 5, 5));
        JPanel keySizePanel = new JPanel();
        JPanel keyModePanel = new JPanel();
        JPanel keyPaddingPanel = new JPanel();
        JPanel IvSizePanel = new JPanel();
        keySize = new JComboBox<>();
        keyMode = new JComboBox<>();
        keyPadding = new JComboBox<>();
        ivSize = new JComboBox<>();
        cipherSpecification.getSupportedKeySizes().forEach((size) -> {
            keySize.addItem(size);
        });
        keySize.setSelectedIndex(0);
        cipherSpecification.getValidModePaddingCombinations().forEach((mode, paddings) -> {
            keyMode.addItem(mode);
        });
        keyMode.setSelectedIndex(0);
        keySizePanel.setLayout(new BorderLayout());
        keySizePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Key Size"));
        keySize.setFont(keyFont);
        keySizePanel.add(keySize, BorderLayout.CENTER);
        keyModePanel.setLayout(new BorderLayout());
        keyModePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Mode"));
        keyMode.setFont(keyFont);
        keyModePanel.add(keyMode, BorderLayout.CENTER);
        keyPaddingPanel.setLayout(new BorderLayout());
        keyPaddingPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Padding"));
        keyPadding.setFont(keyFont);
        keyPaddingPanel.add(keyPadding, BorderLayout.CENTER);
        keyInsPanel.add(keySizePanel);
        keyInsPanel.add(keyModePanel);
        keyInsPanel.add(keyPaddingPanel);
        if (!cipherSpecification.getIvSizes().isEmpty()) {
            keyInsPanel.setLayout(new GridLayout(1, 4, 5, 5));
            IvSizePanel.setLayout(new BorderLayout());
            IvSizePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "IV Size"));
            IvSizePanel.add(ivSize, BorderLayout.CENTER);
            keyInsPanel.add(IvSizePanel);
        }
        keyMode.addActionListener(e -> {
            rebuildPaddingAndIVSize(cipherSpecification, (Mode) keyMode.getSelectedItem());
            controller.updateKey(keySize.getSelectedItem(), ((Mode) keyMode.getSelectedItem()).getName().isBlank() ? "" : ((Mode) keyMode.getSelectedItem()).getName() + "/" + ((Padding) keyPadding.getSelectedItem()).getName(), ivSize.getSelectedItem());
        });
        rebuildPaddingAndIVSize(cipherSpecification, (Mode) keyMode.getSelectedItem());
//         Layout for panelOne (occupies one row)
        gbc.gridx = 0; // Column 0
        gbc.gridy = 0; // Row 0
        gbc.gridwidth = 1; // Occupy 1 column
        gbc.gridheight = 1; // Occupy 1 row
        gbc.weightx = 1.0; // Horizontal weight
        gbc.weighty = 0.5; // Vertical weight (optional)
        gbc.fill = GridBagConstraints.BOTH; // Fill space
        add(keyInsPanel, gbc);
        keyPanel = new JPanel();
        keyPanel.setLayout(new BoxLayout(keyPanel, BoxLayout.Y_AXIS));
        JPanel keyFieldPanel = new JPanel();
        keyFieldPanel.setLayout(new BorderLayout());
        JTextField keyField = new JTextField();
        keyField.setEnabled(false);
        keyField.setBackground(Color.WHITE);
        keyFieldPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Key"));
        keyField.setFont(keyFont);
        keyFieldPanel.add(keyField, BorderLayout.CENTER);
        keyPanel.add(keyFieldPanel);
        JPanel ivFieldPanel = new JPanel();
        ivFieldPanel.setLayout(new BorderLayout());
        JTextField ivField = new JTextField();
        ivField.setEnabled(false);
        ivField.setBackground(Color.WHITE);
        ivFieldPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Initialization Vector"));
        ivField.setFont(keyFont);
        ivFieldPanel.add(ivField, BorderLayout.CENTER);
        keyPanel.add(ivFieldPanel);
        gbc.gridx = 0; // Column 0
        gbc.gridy = 1; // Row 1
        gbc.gridwidth = 1; // Occupy 1 column
        gbc.gridheight = 2; // Occupy 2 rows
        gbc.weightx = 1.0; // Horizontal weight
        gbc.weighty = 1.0; // Vertical weight
        gbc.fill = GridBagConstraints.BOTH; // Fill space
        add(keyPanel, gbc);
        keySize.addActionListener(e -> {
            controller.updateKey(keySize.getSelectedItem(), ((Mode) keyMode.getSelectedItem()).getName().isBlank() ? "" : ((Mode) keyMode.getSelectedItem()).getName() + "/" + ((Padding) keyPadding.getSelectedItem()).getName(), ivSize.getSelectedItem());
        });
        keyPadding.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (keyPadding.getSelectedItem() != null) {
                    controller.updateKey(keySize.getSelectedItem(), ((Mode) keyMode.getSelectedItem()).getName().isBlank() ? "" : ((Mode) keyMode.getSelectedItem()).getName() + "/" + ((Padding) keyPadding.getSelectedItem()).getName(), ivSize.getSelectedItem());
                }
            }
        });
    }

    private void createAsymmetric(Cipher algorithmName) {
        CipherSpecification cipherSpecification = CipherSpecification.findCipherSpecification(algorithmName);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        JPanel keyInsPanel = new JPanel();
        keyInsPanel.setLayout(new GridLayout(1, 3, 5, 5));
        JPanel keySizePanel = new JPanel();
        JPanel keyModePanel = new JPanel();
        JPanel keyPaddingPanel = new JPanel();
//        JPanel IvSizePanel = new JPanel();
        keySize = new JComboBox<>();
        keyMode = new JComboBox<>();
        keyPadding = new JComboBox<>();
//        ivSize = new JComboBox<>();
        cipherSpecification.getSupportedKeySizes().forEach((size) -> {
            keySize.addItem(size);
        });
        keySize.setSelectedIndex(0);
        cipherSpecification.getValidModePaddingCombinations().forEach((mode, paddings) -> {
            keyMode.addItem(mode);
        });
        keyMode.setSelectedIndex(0);
        keySizePanel.setLayout(new BorderLayout());
        keySizePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Key Size"));
        keySize.setFont(keyFont);
        keySizePanel.add(keySize, BorderLayout.CENTER);
        keyModePanel.setLayout(new BorderLayout());
        keyModePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Mode"));
        keyMode.setFont(keyFont);
        keyModePanel.add(keyMode, BorderLayout.CENTER);
        keyPaddingPanel.setLayout(new BorderLayout());
        keyPaddingPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Padding"));
        keyPadding.setFont(keyFont);
        keyPaddingPanel.add(keyPadding, BorderLayout.CENTER);
        keyInsPanel.add(keySizePanel);
        keyInsPanel.add(keyModePanel);
        keyInsPanel.add(keyPaddingPanel);
//        if (!cipherSpecification.getIvSizes().isEmpty()) {
//            keyInsPanel.setLayout(new GridLayout(1, 4, 5, 5));
//            IvSizePanel.setLayout(new BorderLayout());
//            IvSizePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "IV Size"));
//            IvSizePanel.add(ivSize, BorderLayout.CENTER);
//            keyInsPanel.add(IvSizePanel);
//        }
        keyMode.addActionListener(e -> {
            rebuildPadding(cipherSpecification, (Mode) keyMode.getSelectedItem());
            controller.updateKey(keySize.getSelectedItem(), ((Mode) keyMode.getSelectedItem()).getName().isEmpty() ? "" : ((Mode) keyMode.getSelectedItem()).getName() + "/" + ((Padding) keyPadding.getSelectedItem()).getName());
//            controller.updateKey(keySize.getSelectedItem(), ((Mode) keyMode.getSelectedItem()).getName() + "/" + ((Padding) keyPadding.getSelectedItem()).getName(), ivSize.getSelectedItem());
        });
        rebuildPadding(cipherSpecification, (Mode) keyMode.getSelectedItem());
//         Layout for panelOne (occupies one row)
        gbc.gridx = 0; // Column 0
        gbc.gridy = 0; // Row 0
        gbc.gridwidth = 1; // Occupy 1 column
        gbc.gridheight = 1; // Occupy 1 row
        gbc.weightx = 1.0; // Horizontal weight
        gbc.weighty = 0.5; // Vertical weight (optional)
        gbc.fill = GridBagConstraints.BOTH; // Fill space
        add(keyInsPanel, gbc);
        keyPanel = new JPanel();
        keyPanel.setLayout(new BoxLayout(keyPanel, BoxLayout.Y_AXIS));
        JPanel keyFieldPanel = new JPanel();
        keyFieldPanel.setLayout(new BorderLayout());
        JTextField keyField = new JTextField();
        keyField.setEnabled(true);
        keyField.setEditable(false);
        keyField.setBackground(Color.WHITE);
        keyFieldPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Public key"));
        keyField.setFont(keyFont);
        keyFieldPanel.add(keyField, BorderLayout.CENTER);
        keyPanel.add(keyFieldPanel);
        JPanel privateKeydPanel = new JPanel();
        privateKeydPanel.setLayout(new BorderLayout());
        JTextField privateKeyField = new JTextField();
        privateKeyField.setEnabled(true);
        privateKeyField.setEditable(false);
        privateKeyField.setBackground(Color.WHITE);
        privateKeydPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Private key"));
        privateKeyField.setFont(keyFont);
        privateKeydPanel.add(privateKeyField, BorderLayout.CENTER);
        keyPanel.add(privateKeydPanel);
        gbc.gridx = 0; // Column 0
        gbc.gridy = 1; // Row 1
        gbc.gridwidth = 1; // Occupy 1 column
        gbc.gridheight = 2; // Occupy 2 rows
        gbc.weightx = 1.0; // Horizontal weight
        gbc.weighty = 1.0; // Vertical weight
        gbc.fill = GridBagConstraints.BOTH; // Fill space
        add(keyPanel, gbc);
        keySize.addActionListener(e -> {
            controller.updateKey(keySize.getSelectedItem(), ((Mode) keyMode.getSelectedItem()).getName().isEmpty() ? "" : ((Mode) keyMode.getSelectedItem()).getName() + "/" + ((Padding) keyPadding.getSelectedItem()).getName());
        });
        keyPadding.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (keyPadding.getSelectedItem() != null) {
                    controller.updateKey(keySize.getSelectedItem(), ((Mode) keyMode.getSelectedItem()).getName().isEmpty() ? "" : ((Mode) keyMode.getSelectedItem()).getName() + "/" + ((Padding) keyPadding.getSelectedItem()).getName());
                }
            }
        });
    }

    public void rebuildPadding(CipherSpecification specification, Mode mode) {
        keyPadding.removeAllItems();
        specification.getValidModePaddingCombinations().get(mode).forEach(padding -> {
            keyPadding.addItem(padding);
        });
    }

    public synchronized void rebuildPaddingAndIVSize(CipherSpecification specification, Mode mode) {
        keyPadding.removeAllItems();
        specification.getValidModePaddingCombinations().get(mode).forEach(padding -> {
            keyPadding.addItem(padding);
        });
//        keyPadding.setSelectedIndex(0);
        ivSize.removeAllItems();
        ivSize.addItem(specification.getIvSizes().get(mode));
    }

    public void rebuildHillPanel(String size) {
        keyPanel.removeAll();
        keyPanel.revalidate();
        keyPanel.repaint();
        NumberFormat numberFormat = NumberFormat.getIntegerInstance();
        NumberFormatter numberFormatter = new NumberFormatter(numberFormat);
        numberFormatter.setAllowsInvalid(false);
        numberFormatter.setValueClass(Integer.class);
        switch (size) {
            case "2x2":
                keyPanel.setLayout(new GridLayout(2, 2, 5, 5));
                for (int i = 0; i < 4; i++) {
                    JFormattedTextField numberField = new JFormattedTextField(numberFormatter);
                    numberField.addActionListener(hillListener);
                    numberField.setFont(keyFont);
                    keyPanel.add(numberField);
                }
                break;
            case "3x3":
                keyPanel.setLayout(new GridLayout(3, 3, 5, 5));
                for (int i = 0; i < 9; i++) {
                    JFormattedTextField numberField = new JFormattedTextField(numberFormatter);
                    numberField.addActionListener(hillListener);
                    numberField.setFont(keyFont);
                    keyPanel.add(numberField);
                }
                break;
            case "4x4":
                keyPanel.setLayout(new GridLayout(4, 4, 5, 5));
                for (int i = 0; i < 16; i++) {
                    JFormattedTextField numberField = new JFormattedTextField(numberFormatter);
                    numberField.addActionListener(hillListener);
                    numberField.setFont(keyFont);
                    keyPanel.add(numberField);
                }
                break;
        }
    }

    private ActionListener hillListener = e -> {
        double[][] key = new double[0][];
        switch ((String) hillKeySize.getSelectedItem()) {
            case "2x2":
                key = new double[2][2];
                break;
            case "3x3":
                key = new double[3][3];
                break;
            case "4x4":
                key = new double[4][4];
                break;
        }
        int index = 0;
        for (Component c : keyPanel.getComponents()) {
            if (c instanceof JFormattedTextField textField) {
                key[index / key.length][index % key.length] = Double.parseDouble(textField.getText());
                index++;
            }
        }
        controller.updateKey(key);
    };

    public void genSubstitutionKey() {
        Map<String, String> key = (Map<String, String>) controller.getAlgorithms().getKey().getKey();
        for (Component c : keyPanel.getComponents()) {
            if (c instanceof JTextField textField) {
                textField.setText(String.join("", key.values()));
            }
        }
    }

    public void genTransportationKey() {
        int key = (int) controller.getAlgorithms().getKey().getKey();
        for (Component c : keyPanel.getComponents()) {
            if (c instanceof JSpinner spinner) {
                spinner.setValue(key);
            }
        }
    }

    public void genHillKey() {
        this.hillKeySize.setSelectedIndex(0);
        rebuildHillPanel((String) hillKeySize.getSelectedItem());
        double[][] key = (double[][]) controller.getAlgorithms().getKey().getKey();
        int index = 0;
        for (double[] doubles : key) {
            for (double aDouble : doubles) {
                Component c = keyPanel.getComponent(index++);
                if (c instanceof JFormattedTextField textField) {
                    textField.setValue(aDouble);
                }
            }
        }
    }

    public void genVergenceKey() {
        String[] key = (String[]) controller.getAlgorithms().getKey().getKey();
        for (Component c : keyPanel.getComponents()) {
            if (c instanceof JTextField textField) {
                textField.setText(String.join("", key));
            }
        }
    }

    public void genAffineKey() {
        int[] key = (int[]) controller.getAlgorithms().getKey().getKey();
        int index = 0;
        for (Component c : keyPanel.getComponents()) {
            if (c instanceof JPanel panel) {
                for (Component component : panel.getComponents()) {
                    if (component instanceof JSpinner spinner) {
                        spinner.setValue(key[index++]);
                    }
                }
            }
        }
    }

    public void genSymmetricKey() {
        SymmetricKeyHelper key = (SymmetricKeyHelper) controller.getAlgorithms().getKey().getKey();
        String[] texts = key.getStringKeyAndIv();
        int index = 0;
        for (Component c : keyPanel.getComponents()) {
            if (c instanceof JPanel panel) {
                if (panel.getComponent(0) instanceof JTextField textField)
                    textField.setText(texts[index++]);
            }
        }
    }

    public void genAsymmetricKey() {
        AsymmetricKeyHelper key = (AsymmetricKeyHelper) controller.getAlgorithms().getKey().getKey();
        String[] texts = key.getKeys();
        int index = 0;
        for (Component c : keyPanel.getComponents()) {
            if (c instanceof JPanel panel) {
                if (panel.getComponent(0) instanceof JTextField textField)
                    textField.setText(texts[index++]);
            }
        }

    }

    @Override
    public void update(String alphabet) {
        alphabetField.setText(alphabet);
    }


}
