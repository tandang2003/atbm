package model.key;

public class VigenereKey implements IKey<String[]> {
    private String[] key;

    public VigenereKey(String[] key) {
        this.key = key;
    }

    @Override
    public String[] getKey() {
        System.out.println("VigenereKey getKey");
        System.out.println(key);
        return key;
    }

}
