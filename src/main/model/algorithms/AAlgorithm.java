package main.model.algorithms;

import main.model.key.IKey;

import java.util.List;


public abstract class AAlgorithm implements IAlgorithms {
    protected IKey key;
    protected List<String> arrChar;

    @Override
    public void loadKey(IKey key) {
        this.key = key;
    }

    public void setArrChar(List<String> chars) {
        this.arrChar = chars;
    }
}
