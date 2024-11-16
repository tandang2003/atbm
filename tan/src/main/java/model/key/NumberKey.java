package model.key;

public class NumberKey implements IKey<Integer> {
    private int key;

    public NumberKey(int key) {
        this.key = key;
    }

    @Override
    public Integer getKey() {
        return key;
    }

}
