package view.algorithmPanel;


import controller.MainController;
import model.common.Cipher;
import model.common.Hash;
import model.common.ICipherEnum;
import model.common.KeyPairAlgorithm;
import observer.algorithm.ObserverAlgorithm;
import view.font.MyFont;

import javax.swing.*;
import java.awt.*;

import static model.common.Button.FONTSIZE_NORMAL;
import static view.font.MyFont.ROBOTO_BOLD;

public abstract class VAlgorithmAbs extends JPanel implements ObserverAlgorithm {
    protected String name;
    protected JComboBox<ICipherEnum> algorithms;
    protected Font font;
    private DetailAlgorithmPanel algorithmPanel;
    private MainController controller;

    public VAlgorithmAbs(MainController controller) {
        this.controller = controller;
        controller.register(this);
        algorithms = new JComboBox<>();
        font = MyFont.loadCustomFont(ROBOTO_BOLD, FONTSIZE_NORMAL);
        algorithms.setFont(font);
        algorithms.setEditable(false);
//        algorithms.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        algorithmPanel = new DetailAlgorithmPanel(controller);
        init();
        designLayout();
        algorithms.setSelectedIndex(0);
        algorithmPanel.rebuildPanel((ICipherEnum) algorithms.getSelectedItem());
    }

    protected abstract void init();

    protected void designLayout() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JPanel tempAlgoPanel = new JPanel();
        tempAlgoPanel.setLayout(new BorderLayout());
        tempAlgoPanel.add(algorithms, BorderLayout.CENTER);
        tempAlgoPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0), "Algorithms"));
        add(tempAlgoPanel, BorderLayout.NORTH);
        add(algorithmPanel, BorderLayout.CENTER);

        algorithms.addActionListener(e -> {
            controller.setAlgorithm((ICipherEnum) algorithms.getSelectedItem());
            algorithmPanel.rebuildPanel((ICipherEnum) algorithms.getSelectedItem());
        });
    }

    ;

    @Override
    public Dimension getMaximumSize() {
        return new Dimension(Integer.MAX_VALUE, 300);
    }

    public String getName() {
        return name;
    }

    public JComboBox<ICipherEnum> getAlgorithms() {
        return algorithms;
    }

    @Override
    public void update(VAlgorithmAbs algorithmAbs, ICipherEnum cipher) {
        if (algorithmAbs != this) {
            return;
        }
        if (cipher instanceof Hash) {
            algorithmPanel.genHashKey();
            return;
        } else if (cipher instanceof KeyPairAlgorithm) {
            algorithmPanel.genKeyPair();
            return;
        } else
            switch ((Cipher) cipher) {
                case AFFINE:
                    algorithmPanel.genAffineKey();
                    break;
                case HILL:
                    algorithmPanel.genHillKey();
                    break;
                case SUBSTITUTION:
                    algorithmPanel.genSubstitutionKey();
                    break;
                case TRANSPOSITION:
                    algorithmPanel.genTransportationKey();
                    break;
                case VIGENERE:
                    algorithmPanel.genVergenceKey();
                    break;
                case AES, BLOWFISH, DES, DESEDE, RC2, RC4, Camellia, Twofish, Serpent:
                    algorithmPanel.genSymmetricKey();
                    break;
                case RSA:
                    algorithmPanel.genAsymmetricKey();
                    break;
            }
    }
}
