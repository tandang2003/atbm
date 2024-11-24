package model.common;

public enum KeyPairAlgorithm implements ICipherEnum {
    RSA("RSA", "RSA"),
    DSA("DSA", "DSA"),
    ;
    private String name;
    private String displayName;

    KeyPairAlgorithm(String name, String displayName) {
        this.name = name;
        this.displayName = displayName;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
