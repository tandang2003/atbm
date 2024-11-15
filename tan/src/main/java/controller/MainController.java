package controller;

import model.algorithms.IAlgorithms;
import model.algorithms.classicEncryption.SubstitutionAlgorithm;
import model.algorithms.classicEncryption.TranspositionAlgorithm;
import model.algorithms.classicEncryption.AffineAlgorithm;
import model.algorithms.classicEncryption.HillAlgorithm;
import model.algorithms.classicEncryption.VigenereAlgorithm;
import model.common.Alphabet;
import model.common.Cipher;
import observer.algorithmObserver.ObserverAlgorithm;
import observer.algorithmObserver.SubjectAlgorithm;
import observer.alphabetObserver.AlphaSubject;
import view.AlgorithmPanel.VAlgorithmAbs;
import view.VFrame;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    public void genKey() {
        algorithms.genKey();
        this.notifyAlgorithmObservers();
//        algorithms.notifyObservers();
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

    public void setAlgorithm(Cipher selectedItem) {
        System.out.println(selectedItem);
        switch (selectedItem) {
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
        }
    }

    public void setTabbedPane(VAlgorithmAbs selectedIndex) {
        this.algorithmAbs = selectedIndex;
    }

    public void saveKey(File selectedFile) throws IOException {
        algorithms.saveKey(selectedFile);
    }

    public void loadKey(File selectedFile) throws IOException, ClassNotFoundException {
        algorithms.loadKey(selectedFile);
        notifyAlgorithmObservers();
    }
}
