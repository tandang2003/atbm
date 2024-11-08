package main.model.key;

public class VigenereKey implements IKey<String[]> {
    private String[] key;

    public VigenereKey(String[] key) {
        this.key = key;
    }

    @Override
    public String[] getKey() {
        return key;
    }
}
