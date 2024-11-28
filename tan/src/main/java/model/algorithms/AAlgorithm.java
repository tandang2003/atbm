package model.algorithms;


import model.common.Cipher;
import model.common.Hash;
import model.common.ICipherEnum;
import model.common.KeyPairAlgorithm;
import model.key.AffineKey;
import model.key.IKey;
import observer.algorithmObserver.ObserverAlgorithm;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AAlgorithm implements IAlgorithms {
    protected IKey key;
    protected List<String> arrChar;

    protected AAlgorithm() {
    }



    @Override
    public void loadKey(File selectedFile) throws IOException {
        if (!selectedFile.exists()) {
            throw new IOException("File not found");
        }
        DataInputStream inputStream = new DataInputStream(new FileInputStream(selectedFile));
        key.loadFromFile(inputStream);
        inputStream.close();
//        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(selectedFile));
//        Cipher cipher = null;
//        try {
//            cipher = (Cipher) inputStream.readObject();
//        } catch (ClassNotFoundException e) {
//            throw new ClassNotFoundException("Invalid key file");
//        }
//        if (cipher != getCipher()) {
//            throw new IOException("Invalid key file");
//        }
//        IKey key = null;
//        try {
//            key = (IKey) inputStream.readObject();
//        } catch (ClassNotFoundException e) {
//            throw new ClassNotFoundException("Invalid key file");
//        }
//        this.key = key;
//        inputStream.close();
    }

    public void setArrChar(List<String> chars) {
        this.arrChar = chars;
    }

    @Override
    public IKey getKey() {
        return key;
    }

    @Override
    public void saveKey(File selectedFile) throws IOException {
        boolean isFile = selectedFile.getAbsolutePath().contains(".");
        String path = selectedFile.getAbsolutePath();
        if (isFile) {
            File parent = selectedFile.getParentFile();
            path = parent.getAbsolutePath();
            if (!parent.exists()) {
                parent.mkdirs();
            }
        } else {
            if (!selectedFile.exists())
                selectedFile.mkdirs();
        }
        if (key == null) {
            throw new IOException("Key is not created");
        }
        ICipherEnum cipher = getCipher();
        String nameFile = getCipher().getName() + ".tan.key";
        if (cipher instanceof KeyPairAlgorithm) {
            nameFile = "Sign_" + getCipher().getName() + ".tan.key";
        } else if (cipher instanceof Hash) {
            nameFile = "hash_" + getCipher().getName() + ".tan.key";
        }
        nameFile = Arrays.stream(nameFile.split(""))
                .map(ch -> ch.matches("[a-zA-Z0-9._-]") ? ch : "_")
                .reduce("", String::concat);
        try {
            File destination = new File(path + File.separator + nameFile);
            if (!destination.exists())
                destination.createNewFile();
            DataOutputStream outputStream = new DataOutputStream(new FileOutputStream(destination));
            key.saveToFile(outputStream);
            outputStream.close();
        } catch (IOException e) {
            throw new IOException("Error while saving key. Please try again");
        }
    }


}
