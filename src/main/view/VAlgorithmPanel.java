package main.view;

import main.model.common.Algorithms;
import main.model.common.Button;
import main.view.AlgorithmPanel.*;
import main.view.font.MyFont;

import javax.swing.*;
import java.awt.*;

import static main.model.common.Button.FONTSIZE_NORMAL;
import static main.view.font.MyFont.ROBOTO_REGULAR;

public class VAlgorithmPanel extends JTabbedPane {

    //    private JComboBox<String> typeAlgorithm;
//    private JComboBox<String> algorithm;
//    private JComboBox<String> mode;
//    private JComboBox<String> padding;
    private Font font;

    private VSymmetricPanel symmetricPanel;
    private VAsymmetricPanel asymmetricPanel;
    private VHashPanel hashPanel;
    private VClassicPanel classicPanel;
    private VBlockPanel blockPanel;

    public VAlgorithmPanel() {
        font = MyFont.loadCustomFont(ROBOTO_REGULAR, FONTSIZE_NORMAL);
        init();
    }

    private void init() {

        symmetricPanel = new VSymmetricPanel();
        asymmetricPanel = new VAsymmetricPanel();
        hashPanel = new VHashPanel();
        classicPanel = new VClassicPanel();
        blockPanel = new VBlockPanel();
        this.setFont(font);
        addTab(symmetricPanel.getName(), symmetricPanel);
        addTab(asymmetricPanel.getName(), asymmetricPanel);
        addTab(hashPanel.getName(), hashPanel);
        addTab(classicPanel.getName(), classicPanel);
        addTab(blockPanel.getName(), blockPanel);
    }

//        typeAlgorithm = new JComboBox<>();
//        typeAlgorithm.addItem(Algorithms.SYMMETRIC);
//        typeAlgorithm.addItem(Algorithms.ASYMMETRIC);
//        typeAlgorithm.addItem(Algorithms.BLOCK);
//        typeAlgorithm.addItem(Algorithms.HASH);
//        typeAlgorithm.addItem(Algorithms.CLASSICAL);
//        typeAlgorithm.setFont(font);
//        algorithm = new JComboBox<>();
//        algorithm.setFont(font);
//        algorithm.setEditable(false);
//        algorithm.setEnabled(false);
//        mode = new JComboBox<>();
//        mode.setFont(font);
//        mode.setEnabled(false);
//        mode.setEditable(false);
//        padding = new JComboBox<>();
//        padding.setFont(font);
//        padding.setEditable(false);
//        padding.setEnabled(false);
//        // Set preferred height for each combo box
//        Dimension comboBoxSize = new Dimension(150, 40); // Width, Height
//        typeAlgorithm.setPreferredSize(comboBoxSize);
//        algorithm.setPreferredSize(comboBoxSize);
//        mode.setPreferredSize(comboBoxSize);
//        padding.setPreferredSize(comboBoxSize);
//
//        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
//        setLayout(new GridLayout(1, 4, 10, 10));
//        eventType();
//
//        add(typeAlgorithm);
//        add(algorithm);
//        add(mode);
//        add(padding);
//
//    public void eventType() {
//        typeAlgorithm.addActionListener(e -> {
//            String type = (String) typeAlgorithm.getSelectedItem();
//            resetAlgorithm(type);
//            resetModel(null);
//            resetPadding(null);
//            algorithm.setEnabled(true);
//            algorithm.setSelectedIndex(-1);
//        });
//
//        algorithm.addActionListener(e -> {
//            String algorithm = (String) this.algorithm.getSelectedItem();
//            resetModel(algorithm);
//            resetPadding(algorithm);
//            mode.setEnabled(true);
//            padding.setEnabled(true);
//            mode.setSelectedIndex(-1);
//            padding.setSelectedIndex(-1);
//        });
//    }
//
//    private void resetModel(String algorithm) {
//        mode.removeAllItems();
//        if (algorithm == null) {
//            return;
//        }
//        switch (algorithm) {
//            case Algorithms.AES:
//                // Các mode phổ biến cho AES
//                mode.addItem(Algorithms.MODEL_CBC); // Cipher Block Chaining
//                mode.addItem(Algorithms.MODEL_ECB); // Electronic Codebook
//                mode.addItem(Algorithms.MODEL_GCM); // Galois/Counter Mode (thường dùng cho xác thực)
//                break;
//            case Algorithms.DES:
//                // Các mode phổ biến cho DES
//                mode.addItem(Algorithms.MODEL_CBC); // Cipher Block Chaining
//                mode.addItem(Algorithms.MODEL_ECB); // Electronic Codebook
//                break;
//            case Algorithms.RSA:
//                // RSA thường không sử dụng các mode giống như AES hay DES, mà kết hợp với padding
//                mode.addItem(Algorithms.MODEL_ECB); // Electronic Codebook (mặc định cho RSA)
////                mode.addItem(Algorithms.PADDING_PKCS1); // RSA với PKCS#1 padding
////                mode.addItem(Algorithms.PADDING_OAEP); // RSA với OAEP (Optimal Asymmetric Encryption Padding)
//                break;
//            case Algorithms.DSA:
//                // DSA không phải là thuật toán mã hóa mà là thuật toán ký số, thường không cần mode
//                mode.addItem(Algorithms.MODEL_NONE); // Không có mode cụ thể cho DSA
//                break;
//            case Algorithms.TRANSPOSITION, Algorithms.SUBSTITUTION, Algorithms.AFFINE, Algorithms.VIGENCE,
//                 Algorithms.HILL:
//                break;
//            default:
//                padding.addItem(Algorithms.PADDING_NO);
//                break;
//        }
//    }
//
//    public void resetPadding(String algorithm) {
//        padding.removeAllItems();
//        if (algorithm == null) {
//            return;
//        }
//        switch (algorithm) {
//            case Algorithms.AES:
//                padding.addItem(Algorithms.PADDING_NO);
//                padding.addItem(Algorithms.PADDING_PKCS5);
//                padding.addItem(Algorithms.PADDING_PKCS7);
//                padding.addItem(Algorithms.PADDING_ISO10126);
//                padding.addItem(Algorithms.PADDING_ISO7816);
//                padding.addItem(Algorithms.PADDING_X923);
//                break;
//            case Algorithms.DES:
//                padding.addItem(Algorithms.PADDING_NO);
//                padding.addItem(Algorithms.PADDING_PKCS5);
//                padding.addItem(Algorithms.PADDING_PKCS7);
//                padding.addItem(Algorithms.PADDING_ISO10126);
//                padding.addItem(Algorithms.PADDING_ISO7816);
//                padding.addItem(Algorithms.PADDING_X923);
//                break;
//            case Algorithms.RSA:
//                padding.addItem(Algorithms.PADDING_NO);
//                padding.addItem(Algorithms.PADDING_PKCS1);
//                padding.addItem(Algorithms.PADDING_OAEP);
//                break;
//            case Algorithms.DSA:
//                padding.addItem(Algorithms.PADDING_NO);
//                break;
//            case Algorithms.TRANSPOSITION, Algorithms.SUBSTITUTION, Algorithms.AFFINE, Algorithms.VIGENCE,
//                 Algorithms.HILL:
//                break;
//            default:
//                padding.addItem(Algorithms.PADDING_NO);
//                break;
//        }
//    }
//
//    private void resetAlgorithm(String type) {
//        algorithm.removeAllItems();
//        switch (type) {
//            case Algorithms.SYMMETRIC:
//                algorithm.addItem(Algorithms.AES);
//                algorithm.addItem(Algorithms.DES);
//                break;
//            case Algorithms.ASYMMETRIC:
//                algorithm.addItem(Algorithms.RSA);
//                algorithm.addItem(Algorithms.DSA);
//                break;
//            case Algorithms.BLOCK:
//                algorithm.addItem(Algorithms.AES);
//                algorithm.addItem(Algorithms.DES);
//                break;
//            case Algorithms.HASH:
//                algorithm.addItem(Algorithms.MD5);
//                break;
//            case Algorithms.CLASSICAL:
//                algorithm.addItem(Algorithms.TRANSPOSITION);
//                algorithm.addItem(Algorithms.SUBSTITUTION);
//                algorithm.addItem(Algorithms.AFFINE);
//                algorithm.addItem(Algorithms.VIGENCE);
//                algorithm.addItem(Algorithms.HILL);
//                break;
//            default:
//                padding.addItem(Algorithms.PADDING_NO);
//                break;
//        }
//    }
}