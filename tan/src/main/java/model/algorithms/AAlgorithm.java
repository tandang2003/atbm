package model.algorithms;


import model.key.IKey;
import observer.algorithmObserver.ObserverAlgorithm;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class AAlgorithm implements IAlgorithms {
    protected IKey key;
    protected List<String> arrChar;

    protected AAlgorithm() {
    }

    //    protected List<ObserverAlgorithm> observers;
    @Override
    public void loadKey(IKey key) {
        this.key = key;
    }

    public void setArrChar(List<String> chars) {
        this.arrChar = chars;
    }


    @Override
    public IKey getKey() {
        return key;
    }

    @Override
    public void saveKey(File selectedFile) {
        if (!selectedFile.exists())
            selectedFile.mkdirs();


    }

    public static void main(String[] args) {
//        File file = new File("/home/tan/Documents/Hehe/hehe1/");
//        if (!file.exists()) {
//            System.out.println(file.mkdir());
//            ;
//        }
    }
}
