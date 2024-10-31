package model.key;

import java.util.Random;

public class NumberKey implements IKey<Integer> {
    private int key;

    public NumberKey(int key) {
        this.key = key;
    }

    public NumberKey() {
        genKey();
    }

    @Override
    public Integer getKey() {
        return key;
    }

    @Override
    public void genKey() {
        key = new Random().nextInt();
    }

}
