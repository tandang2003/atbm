package controller;

import model.algorithms.IAlgorithms;
import model.algorithms.modernEncryption.SignAlgorithm;
import model.common.*;
import observer.algorithm.ObserverAlgorithm;
import observer.algorithm.SubjectAlgorithm;
import view.VFrame;
import view.algorithmPanel.VAlgorithmAbs;

import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.List;

public class MainController  implements SubjectAlgorithm {
    private VFrame frame;
    private IAlgorithms algorithms;
    private List<ObserverAlgorithm> observerAlgorithms;
    private VAlgorithmAbs algorithmAbs;

    public MainController() {
        algorithms = new SignAlgorithm(KeyPairAlgorithm.DSA, Signature.SHA256withDSA, Provider.SUN, SecureRandom.SHA1PRNG, Size.Size_128);
        observerAlgorithms = new ArrayList<>();
        frame = new VFrame(this);
    }

    public void genKey() throws IllegalBlockSizeException, NoSuchPaddingException, InvalidKeyException, NoSuchProviderException {
        algorithms.genKey();
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
        return observerAlgorithms.remove(observer);
    }

    @Override
    public void notifyAlgorithmObservers() {
        for (ObserverAlgorithm observer : observerAlgorithms) {
            observer.update(algorithmAbs, algorithms.getCipher());
        }
    }

    public IAlgorithms getAlgorithms() {
        return algorithms;
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
