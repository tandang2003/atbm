package view.AlgorithmPanel;


import controller.MainController;
import model.common.Cipher;
import observer.algorithmObserver.ObserverAlgorithm;
import view.dialog.ProcessDialog;
import view.font.MyFont;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static model.common.Button.FONTSIZE_NORMAL;
import static view.font.MyFont.ROBOTO_BOLD;

public abstract class VAlgorithmAbs extends JPanel implements ObserverAlgorithm {
    protected String name;
    protected JComboBox<Cipher> algorithms;
    protected Font font;
    private DetailAlgorithmPanel algorithmPanel;
    private MainController controller;
    private ProcessDialog processDialog;

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
        algorithmPanel.rebuildPanel((Cipher) algorithms.getSelectedItem());
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
            algorithmPanel.rebuildPanel((Cipher) algorithms.getSelectedItem());
            controller.setAlgorithm((Cipher) algorithms.getSelectedItem());
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

    public JComboBox<Cipher> getAlgorithms() {
        return algorithms;
    }

    @Override
    public void update(VAlgorithmAbs algorithmAbs, Cipher cipher) {
        if (algorithmAbs != this) {
            return;
        }
//        processDialog = new ProcessDialog(controller.getFrame(), "Generating Key", "Generating key for " + cipher.toString());
        switch (cipher) {
            case Cipher.AFFINE:
                algorithmPanel.genAffineKey();
                break;
            case Cipher.HILL:
                algorithmPanel.genHillKey();
                break;
            case Cipher.SUBSTITUTION:
                algorithmPanel.genSubstitutionKey();
                break;
            case Cipher.TRANSPOSITION:
                algorithmPanel.genTransportationKey();
                break;
            case Cipher.VIGENERE:
                algorithmPanel.genVergenceKey();
                break;
            case Cipher.AES, Cipher.BLOWFISH, Cipher.DES, Cipher.DESEDE, Cipher.RC2, Cipher.RC4:
                algorithmPanel.genSymmetricKey();
                break;
            case Cipher.RSA:
                algorithmPanel.genAsymmetricKey();
                break;
        }

//        SwingUtilities.invokeLater(() -> {
//            SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
//
//                @Override
//                protected Void doInBackground() throws Exception {
//                    return null;
//                }
//
//                @Override
//                protected void done() {
//                    try {
//                        // Ensure any exceptions from doInBackground are handled
//                        get();
//                        System.out.println("done");
//                    } catch (Exception e) {
//                        JOptionPane.showMessageDialog(controller.getFrame(),
//                                "An error occurred: " + e.getCause().getMessage(),
//                                "Error",
//                                JOptionPane.ERROR_MESSAGE);
//                    } finally {
//                        processDialog.setVisible(false);
//                        processDialog.dispose();
//                    }
////                    System.out.println("done");
////                    processDialog.setVisible(false);
////                    processDialog.dispose();
//                }
//            };
//        });

        ;
    }
}
