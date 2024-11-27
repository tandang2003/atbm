package view.AlgorithmPanel;

import controller.MainController;
import model.common.*;
import model.key.AsymmetricKeyHelper;
import model.key.HashKeyHelper;
import model.key.SignKeyHelper;
import model.key.SymmetricKeyHelper;
import observer.alphabetObserver.AlphaObserver;
import view.font.MyFont;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.sql.SQLOutput;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

import static model.common.Button.FONTSIZE_NORMAL;
import static view.font.MyFont.ROBOTO_REGULAR;

public class DetailAlgorithmPanel extends JPanel implements AlphaObserver {
    private JPanel keyPanel, foreignPanel, alphabetsPanel;
    private Font keyFont;
    private JTextField alphabetField;
    private JComboBox<String> hillKeySize;
    private MainController controller;


    //symmetric
    private JComboBox<Size> symmetricKeySize, symmetricIvSize;
    private JComboBox<Mode> symmetricKeyMode;
    private JComboBox<Padding> symmetricKeyPadding;
    private JTextField symmetricKeyField, symmetricIvField;

    //asymmetric
    private JComboBox<Size> asymmetricKeySize;
    private JComboBox<Mode> asymmetricKeyMode;
    private JComboBox<Padding> asymmetricKeyPadding;
    private JTextField asymmetricPublic, asymmetricPrivate;

    //hash
    private JComboBox<String> hashOutputFormat;
    private JRadioButton hashYes, hashNo;
    private JTextField hashKeyField;

    //sign
    private JComboBox<Size> signKeySize;
    private JComboBox<Signature> signKeySignature;
    private JComboBox<SecureRandom> signKeySecureRandom;
    private JTextField signPublicKey, signPrivateKey;

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

    public void rebuildPanel(ICipherEnum algorithmName) {
        removeAll();
        revalidate();
        repaint();
        if (algorithmName instanceof Hash) {
            createHash((Hash) algorithmName);
        } else if (algorithmName instanceof KeyPairAlgorithm) {
            createSignPanel((KeyPairAlgorithm) algorithmName);
        } else if (algorithmName instanceof Cipher) {
            Cipher algorithm = (Cipher) algorithmName;
            switch (algorithm) {
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
                    createSymmetric((Cipher) algorithmName);
                    break;
                case Cipher.RSA:
                    createAsymmetric((Cipher) algorithmName);
                    break;
            }
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
                controller.updateKey(key);
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
        keyField.addChangeListener(e -> {
            controller.updateKey(keyField.getValue());
        });
        keyField.setFont(keyFont);
        keyPanel.setLayout(new BorderLayout());
        keyPanel.add(keyField, BorderLayout.CENTER);
        add(keyPanel);
        add(alphabetsPanel);
        add(foreignPanel);
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
        symmetricIvSize = new JComboBox<>();
        symmetricKeyMode = new JComboBox<>();
        symmetricKeyPadding = new JComboBox<>();
        symmetricKeySize = new JComboBox<>();
        cipherSpecification.getSupportedKeySizes().forEach((size) -> {
            symmetricKeySize.addItem(size);
        });
        symmetricKeySize.setSelectedIndex(0);
        cipherSpecification.getValidModePaddingCombinations().forEach((mode, paddings) -> {
            symmetricKeyMode.addItem(mode);
        });
        symmetricKeyMode.setSelectedIndex(0);
        keySizePanel.setLayout(new BorderLayout());
        keySizePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Key Size"));
        symmetricKeySize.setFont(keyFont);
        keySizePanel.add(symmetricKeySize, BorderLayout.CENTER);
        keyModePanel.setLayout(new BorderLayout());
        keyModePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Mode"));
        symmetricKeyMode.setFont(keyFont);
        keyModePanel.add(symmetricKeyMode, BorderLayout.CENTER);
        keyPaddingPanel.setLayout(new BorderLayout());
        keyPaddingPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Padding"));
        symmetricKeyPadding.setFont(keyFont);
        keyPaddingPanel.add(symmetricKeyPadding, BorderLayout.CENTER);
        keyInsPanel.add(keySizePanel);
        keyInsPanel.add(keyModePanel);
        keyInsPanel.add(keyPaddingPanel);
        if (!cipherSpecification.getIvSizes().isEmpty()) {
            keyInsPanel.setLayout(new GridLayout(1, 4, 5, 5));
            IvSizePanel.setLayout(new BorderLayout());
            IvSizePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "IV Size"));
            IvSizePanel.add(symmetricIvSize, BorderLayout.CENTER);
            keyInsPanel.add(IvSizePanel);
        }
        symmetricKeyMode.addActionListener(e -> {
            rebuildPaddingAndIVSize(cipherSpecification, (Mode) symmetricKeyMode.getSelectedItem());
            controller.updateKey(symmetricKeySize.getSelectedItem(), ((Mode) symmetricKeyMode.getSelectedItem()), symmetricKeyPadding.getSelectedItem(), symmetricIvSize.getSelectedItem());
//            controller.updateKey(keySize.getSelectedItem(), ((Mode) keyMode.getSelectedItem()).getName().isBlank() ? "" : ((Mode) keyMode.getSelectedItem()).getName() + "/" + ((Padding) keyPadding.getSelectedItem()).getName(), ivSize.getSelectedItem());
        });
        rebuildPaddingAndIVSize(cipherSpecification, (Mode) symmetricKeyMode.getSelectedItem());
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
        symmetricKeySize.addActionListener(e -> {
            controller.updateKey(symmetricKeySize.getSelectedItem(), ((Mode) symmetricKeyMode.getSelectedItem()), symmetricKeyPadding.getSelectedItem(), symmetricIvSize.getSelectedItem());
        });
        symmetricKeyPadding.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (symmetricKeyPadding.getSelectedItem() != null) {
                    controller.updateKey(symmetricKeySize.getSelectedItem(), ((Mode) symmetricKeyMode.getSelectedItem()), symmetricKeyPadding.getSelectedItem(), symmetricIvSize.getSelectedItem());
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
        asymmetricKeySize = new JComboBox<>();
        asymmetricKeyMode = new JComboBox<>();
        asymmetricKeyPadding = new JComboBox<>();
        cipherSpecification.getSupportedKeySizes().forEach((size) -> {
            asymmetricKeySize.addItem(size);
        });
        asymmetricKeySize.setSelectedIndex(0);
        cipherSpecification.getValidModePaddingCombinations().forEach((mode, paddings) -> {
            asymmetricKeyMode.addItem(mode);
        });
        asymmetricKeyMode.setSelectedIndex(0);
        keySizePanel.setLayout(new BorderLayout());
        keySizePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Key Size"));
        asymmetricKeySize.setFont(keyFont);
        keySizePanel.add(asymmetricKeySize, BorderLayout.CENTER);
        keyModePanel.setLayout(new BorderLayout());
        keyModePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Mode"));
        asymmetricKeyMode.setFont(keyFont);
        keyModePanel.add(asymmetricKeyMode, BorderLayout.CENTER);
        keyPaddingPanel.setLayout(new BorderLayout());
        keyPaddingPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Padding"));
        asymmetricKeyPadding.setFont(keyFont);
        keyPaddingPanel.add(asymmetricKeyPadding, BorderLayout.CENTER);
        keyInsPanel.add(keySizePanel);
        keyInsPanel.add(keyModePanel);
        keyInsPanel.add(keyPaddingPanel);
        asymmetricKeyMode.addActionListener(e -> {
            rebuildPadding(cipherSpecification, (Mode) asymmetricKeyMode.getSelectedItem());
//            controller.updateKey(asymmetricKeySize.getSelectedItem(), ((Mode) asymmetricKeyMode.getSelectedItem()).getName().isEmpty() ? "" : ((Mode) asymmetricKeyMode.getSelectedItem()).getName() + "/" + ((Padding) asymmetricKeySize.getSelectedItem()).getName());
            controller.updateKey(asymmetricKeySize.getSelectedItem(), ((Mode) asymmetricKeyMode.getSelectedItem()), ((Padding) asymmetricKeyPadding.getSelectedItem()));
        });
        rebuildPadding(cipherSpecification, (Mode) asymmetricKeyMode.getSelectedItem());
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
        asymmetricKeySize.addActionListener(e -> {
            controller.updateKey(asymmetricKeySize.getSelectedItem(), ((Mode) asymmetricKeyMode.getSelectedItem()), ((Padding) asymmetricKeyPadding.getSelectedItem()));
        });
        asymmetricKeyPadding.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (asymmetricKeyPadding.getSelectedItem() != null) {
                    controller.updateKey(asymmetricKeySize.getSelectedItem(), ((Mode) asymmetricKeyMode.getSelectedItem()), ((Padding) asymmetricKeyPadding.getSelectedItem()));
                }
            }
        });
    }

    private void createHash(Hash hash) {
        keyPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH; // Fill both horizontal and vertical space
        gbc.insets = new Insets(5, 5, 5, 5); // Padding between components

        hashKeyField = new JTextField(20);
        // ComboBox Section
        hashOutputFormat = new JComboBox<>();
        hashOutputFormat.addItem("Hex");
        hashOutputFormat.addItem("Base64");
        hashOutputFormat.setFont(keyFont);
        JPanel isHexPanel = new JPanel();
        isHexPanel.setLayout(new BorderLayout());
        isHexPanel.add(hashOutputFormat, BorderLayout.CENTER);
        isHexPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Output Format"));

        gbc.gridx = 0; // Column 0
        gbc.gridy = 0; // Row 0
        gbc.gridwidth = 1; // Span 1 column
        gbc.weightx = 1.0; // Expand horizontally
        gbc.weighty = 0.1; // Take small vertical space
        keyPanel.add(isHexPanel, gbc);

        // HMAC Radio Buttons Section
        hashYes = new JRadioButton("Yes");
        hashYes.setFont(keyFont);

        hashNo = new JRadioButton("No");
        hashNo.setFont(keyFont);

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(hashYes);
        buttonGroup.add(hashNo);
        buttonGroup.setSelected(hashYes.getModel(), true); // Set default selection

        // Add spacing between radio buttons
        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5)); // Horizontal gap: 20, Vertical gap: 5
        radioPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Use HMAC:"));
        radioPanel.add(hashYes);
        radioPanel.add(hashNo);

        gbc.gridx = 0; // Column 0
        gbc.gridy = 1; // Row 1
        gbc.gridwidth = 1; // Span 1 column
        gbc.weightx = 1.0; // Expand horizontally
        gbc.weighty = 0.1; // Take moderate vertical space
        keyPanel.add(radioPanel, gbc);

        // Key Field Section
        gbc.gridx = 0; // Column 0
        gbc.gridy = 2; // Row 2
        gbc.gridwidth = 1; // Span 1 column
        gbc.weightx = 1.0; // Expand horizontally
        gbc.weighty = 0.9; // Take most of the vertical space
        keyPanel.add(hashKeyField, gbc);

        add(keyPanel);
        if (hash.equals(Hash.MD2)) {
            hashYes.setEnabled(false);
            hashNo.setSelected(true);
            hashKeyField.setEnabled(false);
            buttonGroup.setSelected(hashNo.getModel(), true); // Set default selection
        }
        // event
        hashOutputFormat.addActionListener(e -> {
            controller.updateKey(Objects.requireNonNull(hashOutputFormat.getSelectedItem()).equals("Hex"), hashYes.isSelected(), hashKeyField.getText());
        });
        hashKeyField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
//                controller.updateKey(Objects.requireNonNull(hashOutputFormat.getSelectedItem()).equals("Hex"), hashYes.isSelected(), keyField.getText());
                controller.updateKey(Objects.requireNonNull(hashOutputFormat.getSelectedItem()).equals("Hex"), hashYes.isSelected(), hashKeyField.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                controller.updateKey(Objects.requireNonNull(hashOutputFormat.getSelectedItem()).equals("Hex"), hashYes.isSelected(), hashKeyField.getText());
//                controller.updateKey(Objects.requireNonNull(isHex.getSelectedItem()).equals("Hex"), yes.isSelected(), keyField.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
        hashYes.addActionListener(e -> {
            hashKeyField.setEnabled(true);
            controller.updateKey(Objects.requireNonNull(hashOutputFormat.getSelectedItem()).equals("Hex"), hashYes.isSelected(), hashKeyField.getText());
        });
        hashNo.addActionListener(e -> {
            hashKeyField.setEnabled(false);
            controller.updateKey(Objects.requireNonNull(hashOutputFormat.getSelectedItem()).equals("Hex"), hashYes.isSelected(), hashKeyField.getText());
        });

    }

    private void createSignPanel(KeyPairAlgorithm algorithm) {
        SignatureSpecification cipherSpecification = SignatureSpecification.findByKeyPairAlgorithm(algorithm);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        JPanel keyInsPanel = new JPanel();
        keyInsPanel.setLayout(new GridLayout(1, 3, 5, 5));
        JPanel keySizePanel = new JPanel();
        JPanel keyModePanel = new JPanel();
        JPanel keyPaddingPanel = new JPanel();
        signKeySize = new JComboBox<>();
        signKeySignature = new JComboBox<>();
        signKeySecureRandom = new JComboBox<>();
        cipherSpecification.getSizes().forEach(signKeySize::addItem);
        signKeySize.setSelectedIndex(0);
        cipherSpecification.getSignatures().forEach(signKeySignature::addItem);
        cipherSpecification.getAlgRandoms().forEach(signKeySecureRandom::addItem);
        signKeySignature.setSelectedIndex(0);
        keySizePanel.setLayout(new BorderLayout());
        keySizePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Key Size"));
        signKeySize.setFont(keyFont);
        keySizePanel.add(signKeySize, BorderLayout.CENTER);
        keyModePanel.setLayout(new BorderLayout());
        keyModePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Signature"));
        signKeySignature.setFont(keyFont);
        keyModePanel.add(signKeySignature, BorderLayout.CENTER);
        keyPaddingPanel.setLayout(new BorderLayout());
        keyPaddingPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Secure Random Algorithm"));
        signKeySecureRandom.setFont(keyFont);
        keyPaddingPanel.add(signKeySecureRandom, BorderLayout.CENTER);
        keyInsPanel.add(keySizePanel);
        keyInsPanel.add(keyModePanel);
        keyInsPanel.add(keyPaddingPanel);
        signKeySignature.addActionListener(signListener);
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
        signKeySize.addActionListener(signListener);
        signKeySecureRandom.addActionListener(signListener);

    }

    private ActionListener signListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            controller.updateKey(signKeySize.getSelectedItem(), signKeySignature.getSelectedItem(), signKeySecureRandom.getSelectedItem());
        }
    };


    private void rebuildPadding(CipherSpecification specification, Mode mode) {
        asymmetricKeyPadding.removeAllItems();
        specification.getValidModePaddingCombinations().get(mode).forEach(padding -> {
            asymmetricKeyPadding.addItem(padding);
        });
    }

    public void rebuildPaddingAndIVSize(CipherSpecification specification, Mode mode) {
        symmetricKeyPadding.removeAllItems();
        specification.getValidModePaddingCombinations().get(mode).forEach(padding -> {
            symmetricKeyPadding.addItem(padding);
        });
        symmetricIvSize.removeAllItems();
        symmetricIvSize.addItem(specification.getIvSizes().get(mode));
        symmetricKeyPadding.repaint();
        symmetricIvSize.repaint();
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
                    numberField.getDocument().addDocumentListener(hillListener);
                    numberField.setValue(0);
                    numberField.setFont(keyFont);
                    keyPanel.add(numberField);
                }
                break;
            case "3x3":
                keyPanel.setLayout(new GridLayout(3, 3, 5, 5));
                for (int i = 0; i < 9; i++) {
                    JFormattedTextField numberField = new JFormattedTextField(numberFormatter);
                    numberField.getDocument().addDocumentListener(hillListener);
                    numberField.setValue(0);
                    numberField.setFont(keyFont);
                    keyPanel.add(numberField);
                }
                break;
            case "4x4":
                keyPanel.setLayout(new GridLayout(4, 4, 5, 5));
                for (int i = 0; i < 16; i++) {
                    JFormattedTextField numberField = new JFormattedTextField(numberFormatter);
                    numberField.getDocument().addDocumentListener(hillListener);
                    numberField.setValue(0);
                    numberField.setFont(keyFont);
                    keyPanel.add(numberField);
                }
                break;
        }
    }

    private DocumentListener hillListener = new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
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
                    key[index / key.length][index % key.length] = Double.parseDouble(textField.getText().isEmpty() ? "0" : textField.getText());
                    index++;
                }
            }
            controller.updateKey(key);
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
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
                    key[index / key.length][index % key.length] = Double.parseDouble(textField.getText().isEmpty() ? "0" : textField.getText());
                    index++;
                }
            }
            controller.updateKey(key);
        }

        @Override
        public void changedUpdate(DocumentEvent e) {

        }
    };

    public void genSubstitutionKey() {
        Map<String, String> key = (Map<String, String>) controller.getAlgorithms().getKey().getKey();
        Collection<String> values = key.values();
        StringBuilder sb = new StringBuilder();
        for (String value : values) {
            sb.append(value);
        }
        for (Component c : keyPanel.getComponents()) {
            if (c instanceof JTextField) {
                JTextField textField = (JTextField) c;
                textField.setText(sb.toString());
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
        double[][] key = (double[][]) controller.getAlgorithms().getKey().getKey();
        hillKeySize.setSelectedIndex(key.length - 2);
        rebuildHillPanel((String) hillKeySize.getSelectedItem());
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
        System.out.println(key);
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

        ActionListener kSizeAL = symmetricKeySize.getActionListeners()[0];
        ActionListener mAL = symmetricKeyMode.getActionListeners()[0];
        ActionListener pAl = symmetricKeyPadding.getActionListeners()[0];

        for (ActionListener actionListener : symmetricKeySize.getActionListeners()) {
            symmetricKeySize.removeActionListener(actionListener);
        }
        for (ActionListener actionListener : symmetricKeyMode.getActionListeners()) {
            symmetricKeyMode.removeActionListener(actionListener);
        }
        for (ActionListener actionListener : symmetricKeyPadding.getActionListeners()) {
            symmetricKeyPadding.removeActionListener(actionListener);
        }
//        if (!cipherSpecification.getIvSizes().isEmpty()) {
        CipherSpecification specification = CipherSpecification.findCipherSpecification(key.getCipher());

        int sizIV = 0, m = 0, ksiz = 0, p = 0;
        ComboBoxModel<Size> modelSize = symmetricKeySize.getModel();
        for (int i = 0; i < modelSize.getSize(); i++) {
            Size size = (Size) modelSize.getElementAt(i);
            if (size.getBit() == key.getKeySize()) {
                ksiz = i;
                break;
            }
        }

        ComboBoxModel<Mode> modelMode = symmetricKeyMode.getModel();
        for (int i = 0; i < modelMode.getSize(); i++) {
            Mode mode = (Mode) modelMode.getElementAt(i);
            if (mode.getName().equals(key.getMode().getName())) {
                m = i;
                break;
            }
        }
        ComboBoxModel<Padding> modelPadding = symmetricKeyPadding.getModel();
        for (int i = 0; i < modelPadding.getSize(); i++) {
            Padding padding = (Padding) modelPadding.getElementAt(i);
            if (padding.getName().equals(key.getPadding().getName())) {
                p = i;
                break;
            }
        }
        if (!specification.getIvSizes().isEmpty()) {
            ComboBoxModel<Size> modeKeySize = symmetricIvSize.getModel();
            for (int i = 0; i < modeKeySize.getSize(); i++) {
                Size size = (Size) modeKeySize.getElementAt(i);
                if (size.getByteFormat() == key.getIvSize()) {
                    sizIV = i;
                    break;
                }
            }
        }

        String[] texts = key.getStringKeyAndIv();
        int index = 0;
        for (Component c : keyPanel.getComponents()) {
            if (c instanceof JPanel panel) {
                for (Component component : panel.getComponents()) {
                    if (component instanceof JTextField textField)
                        textField.setText(texts[index++]);
                }
            }
        }

        key.setSecretKey(texts[0]);
        key.setIvParameterSpec(texts[1]);

        symmetricKeySize.setSelectedIndex(ksiz);
        symmetricKeyMode.setSelectedIndex(m);
        symmetricKeyPadding.addActionListener(pAl);
        symmetricIvSize.setSelectedIndex(sizIV);
        if (specification.getIvSizes().isEmpty())
            controller.updateKey(symmetricKeySize.getSelectedItem(), ((Mode) symmetricKeyMode.getSelectedItem()), ((Padding) symmetricKeyPadding.getSelectedItem()), Size.Size_0, "");
        else
            controller.updateKey(symmetricKeySize.getSelectedItem(), ((Mode) symmetricKeyMode.getSelectedItem()), ((Padding) symmetricKeyPadding.getSelectedItem()), ((Size) symmetricIvSize.getSelectedItem()), "");

        symmetricKeySize.addActionListener(kSizeAL);
        symmetricKeyMode.addActionListener(mAL);
        symmetricKeyPadding.setSelectedIndex(p);


        symmetricIvSize.repaint();
        symmetricKeySize.repaint();
        symmetricKeyPadding.repaint();
        symmetricKeyMode.repaint();
    }

    public void genAsymmetricKey() {
        AsymmetricKeyHelper key = (AsymmetricKeyHelper) controller.getAlgorithms().getKey().getKey();

        ActionListener sAl = asymmetricKeySize.getActionListeners()[0];
        ActionListener mAl = asymmetricKeyMode.getActionListeners()[0];
        ActionListener pAl = asymmetricKeyPadding.getActionListeners()[0];

        for (ActionListener actionListener : asymmetricKeySize.getActionListeners())
            asymmetricKeySize.removeActionListener(actionListener);

        for (ActionListener actionListener : asymmetricKeyMode.getActionListeners())
            asymmetricKeyMode.removeActionListener(actionListener);

        for (ActionListener actionListener : asymmetricKeyPadding.getActionListeners())
            asymmetricKeyPadding.removeActionListener(actionListener);


        int m = 0, ksiz = 0, p = 0;
        ComboBoxModel<Size> modelSize = asymmetricKeySize.getModel();
        for (int i = 0; i < modelSize.getSize(); i++) {
            Size size = (Size) modelSize.getElementAt(i);
            if (size.getBit() == key.getKeySize().getBit()) {
                ksiz = i;
                break;
            }
        }

        ComboBoxModel<Mode> modelMode = asymmetricKeyMode.getModel();
        for (int i = 0; i < modelMode.getSize(); i++) {
            Mode mode = (Mode) modelMode.getElementAt(i);
            if (mode.getName().equals(key.getMode().getName())) {
                m = i;
                break;
            }
        }
        ComboBoxModel<Padding> modelPadding = asymmetricKeyPadding.getModel();
        for (int i = 0; i < modelPadding.getSize(); i++) {
            Padding padding = (Padding) modelPadding.getElementAt(i);
            if (padding.getName().equals(key.getPadding().getName())) {
                p = i;
                break;
            }
        }

        String[] texts = key.getKeys();
        int index = 0;
        for (Component c : keyPanel.getComponents()) {
            if (c instanceof JPanel panel) {
                if (panel.getComponent(0) instanceof JTextField textField)
                    textField.setText(texts[index++]);
            }
        }

        key.setPublicKey(texts[0]);
        key.setPrivateKey(texts[1]);

        controller.updateKey(asymmetricKeySize.getSelectedItem(), ((Mode) asymmetricKeyMode.getSelectedItem()), ((Padding) asymmetricKeyPadding.getSelectedItem()), "");

        asymmetricKeySize.setSelectedIndex(ksiz);
        asymmetricKeyMode.setSelectedIndex(m);
        asymmetricKeyPadding.setSelectedIndex(p);

        asymmetricKeySize.addActionListener(sAl);
        asymmetricKeyMode.addActionListener(mAl);
        asymmetricKeyPadding.addActionListener(pAl);

        asymmetricKeySize.repaint();
        asymmetricKeyPadding.repaint();
        asymmetricKeyMode.repaint();


    }


    @Override
    public void update(String alphabet) {
        alphabetField.setText(alphabet);
    }

    public void genHashKey() {
        HashKeyHelper key = (HashKeyHelper) controller.getAlgorithms().getKey().getKey();
        hashYes.setSelected(key.isHMAC());
        hashNo.setSelected(!key.isHMAC());
        hashKeyField.setEnabled(key.isHMAC());
        hashKeyField.setText(key.getKeyHmac());
        hashOutputFormat.setSelectedItem(key.isHex() ? "Hex" : "Base64");

    }

    public void genKeyPair() {
        SignKeyHelper key = (SignKeyHelper) controller.getAlgorithms().getKey().getKey();

        ActionListener kAl = signKeySize.getActionListeners()[0];
        ActionListener sAl = signKeySignature.getActionListeners()[0];
        ActionListener seAl = signKeySecureRandom.getActionListeners()[0];

        for (ActionListener actionListener : signKeySize.getActionListeners())
            signKeySize.removeActionListener(actionListener);

        for (ActionListener actionListener : signKeySignature.getActionListeners())
            signKeySignature.removeActionListener(actionListener);

        for (ActionListener actionListener : signKeySecureRandom.getActionListeners())
            signKeySecureRandom.removeActionListener(actionListener);

        SignatureSpecification specification = SignatureSpecification.findByKeyPairAlgorithm(key.getKeyPairAlgorithm());
        int siz = 0, sig = 0, ser = 0;
        ComboBoxModel<Size> modelSize = signKeySize.getModel();
        for (int i = 0; i < modelSize.getSize(); i++) {
            Size size = (Size) modelSize.getElementAt(i);
            if (size.getBit() == key.getKeySize()) {
                siz = i;
                break;
            }
        }

        ComboBoxModel<Signature> modelSignature = signKeySignature.getModel();
        for (int i = 0; i < modelSignature.getSize(); i++) {
            Signature sign = (Signature) modelSignature.getElementAt(i);
            if (sign.getName().equals(key.getSignature())) {
                sig = i;
                break;
            }
        }

        ComboBoxModel<SecureRandom> modelSecureRandom = signKeySecureRandom.getModel();
        for (int i = 0; i < modelSecureRandom.getSize(); i++) {
            SecureRandom secureRandom = (SecureRandom) modelSecureRandom.getElementAt(i);
            if (secureRandom.getDisplayName().equals(key.getSecureRandom())) {
                ser = i;
                break;
            }
        }

        String[] texts = key.getKeys();
        int index = 0;
        for (Component c : keyPanel.getComponents()) {
            if (c instanceof JPanel panel) {
                if (panel.getComponent(0) instanceof JTextField textField)
                    textField.setText(texts[index++]);
            }
        }

        signKeySize.setSelectedIndex(siz);
        signKeySignature.setSelectedIndex(sig);
        signKeySecureRandom.setSelectedIndex(ser);

        controller.updateKey(signKeySize.getSelectedItem(), signKeySignature.getSelectedItem(), signKeySecureRandom.getSelectedItem(),"");

        signKeySize.addActionListener(kAl);
        signKeySignature.addActionListener(sAl);
        signKeySecureRandom.addActionListener(seAl);

        signKeySize.repaint();
        signKeySignature.repaint();
        signKeySecureRandom.repaint();



    }
}