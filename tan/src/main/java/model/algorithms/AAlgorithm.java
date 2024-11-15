package model.algorithms;


import model.common.Cipher;
import model.key.AffineKey;
import model.key.IKey;
import observer.algorithmObserver.ObserverAlgorithm;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AAlgorithm implements IAlgorithms {
    protected IKey key;
    protected List<String> arrChar;

    protected AAlgorithm() {
    }

    //    protected List<ObserverAlgorithm> observers;
    @Override
    public void loadKey(File selectedFile) throws IOException, ClassNotFoundException {

        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(selectedFile));
        Cipher cipher = null;
        try {
            cipher = (Cipher) inputStream.readObject();
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException("Invalid key file");
        }
        if (cipher != getCipher()) {
            throw new IOException("Invalid key file");
        }
        IKey key = null;
        try {
            key = (IKey) inputStream.readObject();
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException("Invalid key file");
        }
        this.key = key;
        inputStream.close();

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
        try {
            File destination = new File(path + File.separator + getCipher().getName() + ".tan.key");
            if (!destination.exists())
                destination.createNewFile();
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(destination));
            outputStream.writeObject(getCipher());
            outputStream.writeObject(this.key);
            outputStream.close();
        } catch (IOException e) {
            throw new IOException("Error while saving key. Please try again");
        }
    }

    public static void main(String[] args) {
//        File file = new File("/home/tan/Documents/Hehe/hehe1/");
//        if (!file.exists()) {
//            System.out.println(file.mkdir());
//            ;
//        }
    }
}
