package controller;

import model.algorithms.IAlgorithms;
import model.algorithms.ModernEncryption.HashAlgorithm;
import model.algorithms.ModernEncryption.SignAlgorithm;
import model.algorithms.classicEncryption.SubstitutionAlgorithm;
import model.algorithms.classicEncryption.TranspositionAlgorithm;
import model.algorithms.classicEncryption.AffineAlgorithm;
import model.algorithms.classicEncryption.HillAlgorithm;
import model.algorithms.classicEncryption.VigenereAlgorithm;
import model.algorithms.ModernEncryption.AsymmetricAlgorithm;
import model.algorithms.ModernEncryption.SymmetricAlgorithm;
import model.common.*;
import observer.algorithmObserver.ObserverAlgorithm;
import observer.algorithmObserver.SubjectAlgorithm;
import observer.alphabetObserver.AlphaSubject;
import view.AlgorithmPanel.VAlgorithmAbs;
import view.VFrame;

import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MainController extends AlphaSubject implements SubjectAlgorithm {
    private boolean isEnglish;
    private VFrame frame;
    private IAlgorithms algorithms;
    private List<ObserverAlgorithm> observerAlgorithms;
    private VAlgorithmAbs algorithmAbs;

    public MainController() {
        setLanguage(true);
        observerAlgorithms = new ArrayList<>();
        algorithms = new AffineAlgorithm(isEnglish ? Alphabet.ENGLISH_CHAR_SET : Alphabet.VIETNAMESE_CHAR_SET);
        frame = new VFrame(this);
    }


    public boolean isEnglish() {
        return isEnglish;
    }

    public void setLanguage(boolean isEnglish) {
        this.isEnglish = isEnglish;
        System.out.println("Language set to " + (isEnglish ? "English" : "Vietnamese"));
    }

    public void genKey() throws IllegalBlockSizeException, NoSuchPaddingException, InvalidKeyException {
        algorithms.genKey();
//        this.notifyAlgorithmObservers();
    }


    public String getAlphabet() {
        return isEnglish ? String.join("", Alphabet.ENGLISH_CHAR_SET) : String.join("", Alphabet.VIETNAMESE_CHAR_SET);
    }

    @Override
    public boolean register(ObserverAlgorithm observer) {
        if (!observerAlgorithms.contains(observer)) {
            return observerAlgorithms.add(observer);
        }
        return false;
    }

    @Override
    public boolean remove(ObserverAlgorithm observer) {
        return observers.remove(observer);
    }

    @Override
    public void notifyAlgorithmObservers() {
        for (ObserverAlgorithm observer : observerAlgorithms) {
            observer.update(algorithmAbs, algorithms.getCipher());
        }
    }

    @Override
    public void notifyObservers() {
        algorithms.setArrChar(isEnglish ? Alphabet.ENGLISH_CHAR_SET : Alphabet.VIETNAMESE_CHAR_SET);
        for (observer.alphabetObserver.AlphaObserver observer : observers) {
            observer.update(getAlphabet());
        }
    }

    public IAlgorithms getAlgorithms() {
        return algorithms;
    }

    public void setAlgorithm(ICipherEnum selectedItem) {
        if (selectedItem instanceof Cipher) {
            Cipher cipher = (Cipher) selectedItem;
            switch (cipher) {
                case AFFINE:
                    algorithms = new AffineAlgorithm(isEnglish ? Alphabet.ENGLISH_CHAR_SET : Alphabet.VIETNAMESE_CHAR_SET);
                    break;
                case HILL:
                    algorithms = new HillAlgorithm(isEnglish ? Alphabet.ENGLISH_CHAR_SET : Alphabet.VIETNAMESE_CHAR_SET);
                    break;
                case VIGENERE:
                    algorithms = new VigenereAlgorithm(isEnglish ? Alphabet.ENGLISH_CHAR_SET : Alphabet.VIETNAMESE_CHAR_SET);
                    break;
                case SUBSTITUTION:
                    algorithms = new SubstitutionAlgorithm(isEnglish ? Alphabet.ENGLISH_CHAR_SET : Alphabet.VIETNAMESE_CHAR_SET);
                    break;
                case TRANSPOSITION:
                    algorithms = new TranspositionAlgorithm(isEnglish ? Alphabet.ENGLISH_CHAR_SET : Alphabet.VIETNAMESE_CHAR_SET);
                    break;
                case AES, BLOWFISH, DES, DESEDE, RC2, RC4:
                    CipherSpecification specification = CipherSpecification.findCipherSpecification((Cipher) selectedItem);
                    Size keySize = specification.getSupportedKeySizes().stream().findFirst().get();
                    Mode mode = specification.getValidModePaddingCombinations().entrySet().stream().findFirst().get().getKey();
                    Padding padding = specification.getValidModePaddingCombinations().get(mode).getFirst();
                    Size ivSize = specification.getIvSizes().get(mode);
                    algorithms = new SymmetricAlgorithm((Cipher) selectedItem, mode, padding, keySize, ivSize);
                    break;
                case RSA:
                    CipherSpecification s = CipherSpecification.findCipherSpecification((Cipher) selectedItem);
                    Size kz = s.getSupportedKeySizes().stream().findFirst().get();
                    Mode m = s.getValidModePaddingCombinations().entrySet().stream().findFirst().get().getKey();
                    Padding p = s.getValidModePaddingCombinations().get(m).getFirst();
                    algorithms = new AsymmetricAlgorithm((Cipher) selectedItem, m, p, kz);
                    break;
            }
        } else if (selectedItem instanceof Hash) {
            Hash hash = (Hash) selectedItem;
            algorithms = new HashAlgorithm(hash, true, true);
        } else if (selectedItem instanceof KeyPairAlgorithm keyPairAlgorithm) {
            SignatureSpecification specification = SignatureSpecification.findByKeyPairAlgorithm(keyPairAlgorithm);
            Provider provider = specification.getProvider();
            Signature signatures = specification.getSignatures().getFirst();
            SecureRandom algRandoms = specification.getAlgRandoms().getFirst();
            Size sizes = specification.getSizes().stream().toList().getFirst();
            algorithms = new SignAlgorithm(keyPairAlgorithm, signatures, provider, algRandoms, sizes);
        }
    }

    public void setTabbedPane(VAlgorithmAbs selectedIndex) {
        this.algorithmAbs = selectedIndex;
        setAlgorithm((ICipherEnum) selectedIndex.getAlgorithms().getSelectedItem());
    }

    public void saveKey(File selectedFile) throws IOException {
        algorithms.saveKey(selectedFile);
    }

    public void loadKey(File selectedFile) throws IOException {
        algorithms.loadKey(selectedFile);
    }

    public void updateKey(Object... objects) {
        algorithms.updateKey(objects);
    }

    public VFrame getFrame() {
        return frame;
    }
}
