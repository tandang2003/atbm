package view.algorithmPanel;

import controller.MainController;
import model.common.*;
import model.key.SignKeyHelper;
import view.font.MyFont;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static model.common.Button.FONTSIZE_NORMAL;
import static view.font.MyFont.ROBOTO_REGULAR;

public class DetailAlgorithmPanel extends JPanel {
    private JPanel keyPanel, foreignPanel, alphabetsPanel;
    private Font keyFont;
    private MainController controller;

    //sign
    private JComboBox<Size> signKeySize;
    private JComboBox<Signature> signKeySignature;
    private JComboBox<SecureRandom> signKeySecureRandom;
    private JTextField signPublicKey, signPrivateKey;

    //affine
//    private JSpinner affineAField, affineBField;


    public DetailAlgorithmPanel(MainController controller) {
        this.controller = controller;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));
        keyFont = MyFont.loadCustomFont(ROBOTO_REGULAR, FONTSIZE_NORMAL);
        foreignPanel = new JPanel();
        foreignPanel.setLayout(new BorderLayout());
        foreignPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0), "Foreign Characters"));

        alphabetsPanel = new JPanel();
        alphabetsPanel.setLayout(new BorderLayout());
        alphabetsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0), "Alphabets"));
    }

    public void rebuildPanel(ICipherEnum algorithmName) {
        removeAll();
        revalidate();
        repaint();
        createSignPanel((KeyPairAlgorithm) algorithmName);
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
        signPublicKey = new JTextField();
        signPublicKey.setEnabled(true);
        signPublicKey.setEditable(false);
        signPublicKey.setBackground(Color.WHITE);
        keyFieldPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Public key"));
        signPublicKey.setFont(keyFont);
        keyFieldPanel.add(signPublicKey, BorderLayout.CENTER);
        keyPanel.add(keyFieldPanel);
        JPanel privateKeydPanel = new JPanel();
        privateKeydPanel.setLayout(new BorderLayout());
        signPrivateKey = new JTextField();
        signPrivateKey.setEnabled(true);
        signPrivateKey.setEditable(false);
        signPrivateKey.setBackground(Color.WHITE);
        privateKeydPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Private key"));
        signPrivateKey.setFont(keyFont);
        privateKeydPanel.add(signPrivateKey, BorderLayout.CENTER);
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

        String[] texts = {key.getPrivateKeyString(), key.getPublicKeyString()};
        signPublicKey.setText(texts[1]);
        signPrivateKey.setText(texts[0]);
        signKeySize.setSelectedIndex(siz);
        signKeySignature.setSelectedIndex(sig);
        signKeySecureRandom.setSelectedIndex(ser);

        controller.updateKey(signKeySize.getSelectedItem(), signKeySignature.getSelectedItem(), signKeySecureRandom.getSelectedItem(), "");

        signKeySize.addActionListener(kAl);
        signKeySignature.addActionListener(sAl);
        signKeySecureRandom.addActionListener(seAl);

        signKeySize.repaint();
        signKeySignature.repaint();
        signKeySecureRandom.repaint();
    }
}